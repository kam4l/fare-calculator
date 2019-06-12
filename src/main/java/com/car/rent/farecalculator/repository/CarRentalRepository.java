package com.car.rent.farecalculator.repository;

public interface CarRentalRepository {
    double getDistance(String origin, String destination);

    int getMaxPassengerCount(String vehicleType);
}
