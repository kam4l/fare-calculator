package com.car.rent.farecalculator.service;

import com.car.rent.farecalculator.beans.CarRentalEnquiryRequest;
import com.car.rent.farecalculator.beans.CarRentalExpense;
import com.car.rent.farecalculator.repository.CarRentalRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class CalculateFareServiceImpl implements CalculateFareService {

    private final CarRentalRepository carRentalRepository;

    @Autowired
    public CalculateFareServiceImpl(CarRentalRepository carRentalRepository) {
        this.carRentalRepository = carRentalRepository;
    }


    @Override
    public CarRentalExpense calculateCarRentalExpense(final CarRentalEnquiryRequest carRentalEnquiryRequest) {

        Iterator<String> routeIterator = carRentalEnquiryRequest.getTravelRoute().iterator();

        double routeDistance = 0;
        if (routeIterator.hasNext()){
            String origin = routeIterator.next();
            while (routeIterator.hasNext()){
                final String destination = routeIterator.next();
                routeDistance = routeDistance + carRentalRepository.getDistance(origin, destination);
                origin = destination;
            }
        }
        final int maxPassenger = carRentalRepository.getMaxPassengerCount(carRentalEnquiryRequest.getVehicleType());

        final double standardRate = calculateStandardRate(carRentalEnquiryRequest);
        double extraCharge = 0;
        if (carRentalEnquiryRequest.getPassengerCount().compareTo(maxPassenger) > 0){
            int extraPassengers = carRentalEnquiryRequest.getPassengerCount() - maxPassenger;
            extraCharge = extraPassengers * routeDistance;
        }

        final double estimatedExpense = (standardRate * routeDistance) + extraCharge ;
        final CarRentalExpense expense = new CarRentalExpense();
        expense.setEstimatedExpense(estimatedExpense);
        return expense;
    }

    private double calculateStandardRate(final CarRentalEnquiryRequest carRentalEnquiryRequest) {
        double rate = 15;

        if(StringUtils.equalsIgnoreCase(carRentalEnquiryRequest.getVehicleFuel(), "DIESEL")){
            rate = rate - 1;
        }

        if (carRentalEnquiryRequest.isAirConditioned()){
            rate = rate + 2;
        }

        if (StringUtils.equalsIgnoreCase(carRentalEnquiryRequest.getVehicleType(), "BUS")){
            rate = rate - (rate * 2 / 100);
        }
        return rate;
    }
}
