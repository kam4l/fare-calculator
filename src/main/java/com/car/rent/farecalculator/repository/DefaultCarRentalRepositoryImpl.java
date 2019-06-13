package com.car.rent.farecalculator.repository;

import org.springframework.stereotype.Repository;
import rx.Observable;

import javax.ws.rs.BadRequestException;
import java.util.*;

@Repository
public class DefaultCarRentalRepositoryImpl implements CarRentalRepository {

    private static final Map<Set<String>, Double> DISTANCE_VALUES = createDistanceMap();

    private static final Map<String, Integer> MAX_PASSENGER = createMaxPassengerMap();

    private static final Map<String, String> VEHICLE_TYPE = createVehicleType();

    private static final Double RATE_PER_HOUR = 15D;

    private static Map<String, Integer> createMaxPassengerMap() {
        final Map<String, Integer> maxPassenger = new LinkedHashMap<>();
        maxPassenger.put("BUS", 51);
        maxPassenger.put("VAN", 21);
        maxPassenger.put("SUV", 7);
        maxPassenger.put("SEDAN", 4);
        maxPassenger.put("HATCHBACK", 3);
        return maxPassenger;
    }

    private static Map<String, String> createVehicleType() {
        final Map<String, String> vehicleTypeList = new LinkedHashMap<>();
        vehicleTypeList.put("SWIFT", "SEDAN");
        vehicleTypeList.put("AUDI", "HATCHBACK");
        vehicleTypeList.put("BENZ", "SUV");
        vehicleTypeList.put("VOLVO", "BUS");
        vehicleTypeList.put("TOYOTA COASTER", "VAN");
        return vehicleTypeList;
    }

    private static Map<Set<String>, Double> createDistanceMap() {
        final Map<Set<String>, Double> distanceMap = new LinkedHashMap<>();
        final Set<String> puneToMumbai = new HashSet<>(Arrays.asList("PUNE", "MUMBAI"));
        final Set<String> puneToBangalore = new HashSet<>(Arrays.asList("PUNE", "BANGALORE"));
        final Set<String> mumbaiToBangalore = new HashSet<>(Arrays.asList("MUMBAI", "BANGALORE"));
        final Set<String> mumbaiToDelhi = new HashSet<>(Arrays.asList("MUMBAI", "DELHI"));
        final Set<String> mumbaiToChennai = new HashSet<>(Arrays.asList("MUMBAI", "CHENNAI"));
        final Set<String> bangaloreToChennai = new HashSet<>(Arrays.asList("BANGALORE", "CHENNAI"));
        final Set<String> puneToChennai = new HashSet<>(Arrays.asList("PUNE", "CHENNAI"));
        distanceMap.put(puneToMumbai, 200d);
        distanceMap.put(puneToBangalore, 1000d);
        distanceMap.put(mumbaiToBangalore, 800d);
        distanceMap.put(mumbaiToDelhi, 2050d);
        distanceMap.put(mumbaiToChennai, 1234.5);
        distanceMap.put(bangaloreToChennai, 350d);
        distanceMap.put(puneToChennai, 956.6);
        return distanceMap;
    }

    @Override
    public Observable<Double> getDistance(final String origin, final String destination) {
        return Observable.fromCallable(() -> findDistance(origin, destination));
    }

    @Override
    public Integer getMaxPassengerCount(final String vehicleType) {
        return Optional.ofNullable(MAX_PASSENGER.get(vehicleType.toUpperCase()))
                .orElseThrow(() -> new BadRequestException("Invalid vehicle type"));
    }

    @Override
    public Observable<Double> getRatePerHour() {
        return Observable.fromCallable(this::findRatePerHour);
    }

    @Override
    public Observable<String> getVehicleType(final String vehicleName) {
        return Observable.fromCallable(() -> findVehicleType(vehicleName));
    }

    private String findVehicleType(String vehicleName) {
        return Optional.ofNullable(VEHICLE_TYPE.get(vehicleName.toUpperCase()))
                .orElseThrow(() -> new BadRequestException("Vehicle name is not found"));
    }

    private Double findRatePerHour() {
        return RATE_PER_HOUR;
    }

    private Double findDistance(final String origin, final String destination) {
        return Optional.ofNullable(DISTANCE_VALUES.get(new HashSet<>(Arrays.asList(origin.toUpperCase(), destination.toUpperCase()))))
                .orElseThrow(() -> new BadRequestException("This route is not found in our record: " + origin + " -> " + destination));
    }
}
