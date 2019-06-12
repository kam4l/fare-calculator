package com.car.rent.farecalculator.repository;

import org.springframework.stereotype.Repository;

import javax.ws.rs.BadRequestException;
import java.util.*;

@Repository
public class DefaultCarRentalRepositoryImpl implements CarRentalRepository {

    private static final Map<Set<String>, Double> DISTANCE_VALUES = createDistanceMap();

    private static final Map<String, Integer> MAX_PASSENGER = createMaxPassengerMap();

    private static Map<String, Integer> createMaxPassengerMap() {
        final Map<String, Integer> maxPassenger = new HashMap<>();
        maxPassenger.put("BUS", 51);
        maxPassenger.put("VAN", 21);
        maxPassenger.put("SUV", 7);
        maxPassenger.put("SEDAN", 4);
        maxPassenger.put("HATCHBACK", 3);
        return maxPassenger;
    }

    private static Map<Set<String>, Double> createDistanceMap() {
        final Map<Set<String>,Double> distanceMap = new HashMap<>();
        final Set<String> puneToMumbai = new HashSet<>(Arrays.asList("PUNE", "MUMBAI"));
        final Set<String> puneToBangalore = new HashSet<>(Arrays.asList("PUNE", "BANGALORE"));
        final Set<String> mumbaiToBangalore = new HashSet<>(Arrays.asList("MUMBAI", "BANGALORE"));
        final Set<String> mumbaiToDelhi = new HashSet<>(Arrays.asList("MUMBAI", "DELHI"));
        final Set<String> mumbaiToChennai = new HashSet<>(Arrays.asList("MUMBAI", "CHENNAI"));
        final Set<String> bangaloreToChennai = new HashSet<>(Arrays.asList("BANGALORE", "CHENNAI"));
        distanceMap.put(puneToMumbai, 200d);
        distanceMap.put(puneToBangalore, 1000d);
        distanceMap.put(mumbaiToBangalore, 800d);
        distanceMap.put(mumbaiToDelhi, 2050d);
        distanceMap.put(mumbaiToChennai, 1150d);
        distanceMap.put(bangaloreToChennai, 350d);
        return distanceMap;
    }

    @Override
    public double getDistance(final String origin,
                              final String destination) {
        return Optional.ofNullable(DISTANCE_VALUES.get(new HashSet<>(Arrays.asList(origin.toUpperCase(), destination.toUpperCase())))).orElseThrow(() -> new BadRequestException("Unexpected combination of cities"));

    }

    @Override
    public int getMaxPassengerCount(final String vehicleType) {
        return Optional.ofNullable(MAX_PASSENGER.get(vehicleType.toUpperCase())).orElseThrow(() -> new BadRequestException("Invalid vehicle type"));
    }
}
