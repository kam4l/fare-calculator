package com.car.rent.farecalculator.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarRentalEnquiryRequest {

    @NotBlank(message = "vehicle name is mandatory")
    private String vehicleName;

    @NotBlank(message = "vehicle fuel type is mandatory")
    private String vehicleFuel;

    @NotNull(message = "travel route is mandatory")
    private List<String> travelRoute;

    @NotNull(message = "number of passengers is mandatory")
    private Integer passengerCount;

    @NotNull(message = "airCondition info is mandatory")
    private Boolean airConditioned;

    public CarRentalEnquiryRequest(){

    }

    public CarRentalEnquiryRequest(final String vehicleName,
                                   final String vehicleFuel,
                                   final List<String> travelRoute,
                                   final Integer passengerCount,
                                   final Boolean airConditioned) {
        this.vehicleName = vehicleName;
        this.vehicleFuel = vehicleFuel;
        this.travelRoute = travelRoute;
        this.passengerCount = passengerCount;
        this.airConditioned = airConditioned;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getVehicleFuel() {
        return vehicleFuel;
    }

    public List<String> getTravelRoute() {
        return travelRoute;
    }

    public Integer getPassengerCount() {
        return passengerCount;
    }

    public Boolean isAirConditioned() {
        return airConditioned;
    }

}
