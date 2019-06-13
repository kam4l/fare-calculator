package com.car.rent.farecalculator.beans;

public class CarRentalResponseHolder {

    private final Double ratePerHour;

    private final String vehicleType;

    public CarRentalResponseHolder(Double ratePerHour, String vehicleType) {
        this.ratePerHour = ratePerHour;
        this.vehicleType = vehicleType;
    }


    public Double getRatePerHour() {
        return ratePerHour;
    }

    public String getVehicleType() {
        return vehicleType;
    }
}
