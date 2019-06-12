package com.car.rent.farecalculator.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarRentalEnquiryRequest {
    private String vehicleType;

    private String vehicleFuel;

    private List<String> travelRoute;

    private Integer passengerCount;

    private Boolean airConditioned;

    public CarRentalEnquiryRequest(final String vehicleType,
                                   final String vehicleFuel,
                                   final List<String> travelRoute,
                                   final Integer passengerCount,
                                   final Boolean airConditioned) {
        this.vehicleType = vehicleType;
        this.vehicleFuel = vehicleFuel;
        this.travelRoute = travelRoute;
        this.passengerCount = passengerCount;
        this.airConditioned = airConditioned;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleFuel() {
        return vehicleFuel;
    }

    public void setVehicleFuel(String vehicleFuel) {
        this.vehicleFuel = vehicleFuel;
    }

    public List<String> getTravelRoute() {
        return travelRoute;
    }

    public void setTravelRoute(List<String> travelRoute) {
        this.travelRoute = travelRoute;
    }

    public Integer getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(Integer passengerCount) {
        this.passengerCount = passengerCount;
    }

    public Boolean isAirConditioned() {
        return airConditioned;
    }

    public void setAirConditioned(Boolean airConditioned) {
        this.airConditioned = airConditioned;
    }
}
