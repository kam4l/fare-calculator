package com.car.rent.farecalculator.service;

import com.car.rent.farecalculator.beans.CarRentalEnquiryRequest;
import com.car.rent.farecalculator.beans.CarRentalExpense;
import com.car.rent.farecalculator.beans.CarRentalResponseHolder;
import com.car.rent.farecalculator.beans.TravelRoute;
import com.car.rent.farecalculator.constant.CarRentalConstants.VEHICLE_FUEL;
import com.car.rent.farecalculator.repository.CarRentalRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static com.car.rent.farecalculator.constant.CarRentalConstants.VEHICLE_TYPE_BUS;

@Service
public class CalculateFareServiceImpl implements CalculateFareService {

    private final CarRentalRepository carRentalRepository;

    @Autowired
    public CalculateFareServiceImpl(CarRentalRepository carRentalRepository) {
        this.carRentalRepository = carRentalRepository;
    }


    @Override
    public CarRentalExpense calculateCarRentalExpense(final CarRentalEnquiryRequest carRentalEnquiryRequest) {

        double totalDistance = calculateDistance(carRentalEnquiryRequest.getTravelRoute());

        //Preparing for parallel calls
        Observable<Double> ratePerHour = carRentalRepository.getRatePerHour()
                .subscribeOn(Schedulers.io());

        Observable<String> vehicleType = carRentalRepository.getVehicleType(carRentalEnquiryRequest.getVehicleName())
                .subscribeOn(Schedulers.io());

        //parallel calls to the downstream service
        final CarRentalResponseHolder carRentalResponseHolder = Observable
                .zip(ratePerHour, vehicleType, CarRentalResponseHolder::new)
                .toBlocking()
                .single();

        final Integer maxPassenger = carRentalRepository.getMaxPassengerCount(carRentalResponseHolder.getVehicleType());

        final double standardRate = calculateStandardRate(carRentalEnquiryRequest, carRentalResponseHolder.getRatePerHour(), carRentalResponseHolder.getVehicleType());

        //find extra charge
        int extraPassengers = carRentalEnquiryRequest.getPassengerCount() - maxPassenger;
        double extraCharge = extraPassengers > 0 ? extraPassengers * totalDistance : 0;

        final double estimatedExpense = (standardRate * totalDistance) + extraCharge;
        return new CarRentalExpense(estimatedExpense);
    }

    private double calculateDistance(List<String> travelCities) {
        List<TravelRoute> travelRoutes = new ArrayList<>();
        Iterator<String> routeIterator = travelCities.iterator();
        String origin = routeIterator.hasNext() ? routeIterator.next() : null;
        while (routeIterator.hasNext()) {
            final String destination = routeIterator.next();
            TravelRoute route = new TravelRoute().setOrigin(origin).setDestination(destination);
            travelRoutes.add(route);
            origin = destination;
        }
        List<Double> routeDistance = new LinkedList<>();
        Observable.from(travelRoutes)
                .flatMap(route -> carRentalRepository.getDistance(route.getOrigin(), route.getDestination())
                        .subscribeOn(Schedulers.io()))
                .toBlocking()
                .forEach(routeDistance::add);

        return routeDistance.stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    private double calculateStandardRate(final CarRentalEnquiryRequest carRentalEnquiryRequest,
                                         Double rate, final String vehicleType) {

        if (StringUtils.equalsIgnoreCase(carRentalEnquiryRequest.getVehicleFuel(), VEHICLE_FUEL.DIESEL.name())) {
            rate = rate - 1;
        }

        if (carRentalEnquiryRequest.isAirConditioned()) {
            rate = rate + 2;
        }

        if (StringUtils.equalsIgnoreCase(vehicleType, VEHICLE_TYPE_BUS)) {
            rate = rate - (rate * 2 / 100);
        }
        return rate;
    }
}
