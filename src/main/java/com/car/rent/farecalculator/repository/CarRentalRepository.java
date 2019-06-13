package com.car.rent.farecalculator.repository;

import rx.Observable;

public interface CarRentalRepository {

    Observable<Double> getDistance(String origin, String destination);

    Integer getMaxPassengerCount(String vehicleType);

    Observable<Double> getRatePerHour();

    Observable<String> getVehicleType(String vehicleName);
}
