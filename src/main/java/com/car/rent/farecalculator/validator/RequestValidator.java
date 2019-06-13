package com.car.rent.farecalculator.validator;

import com.car.rent.farecalculator.beans.CarRentalEnquiryRequest;
import com.car.rent.farecalculator.constant.CarRentalConstants;

import javax.ws.rs.BadRequestException;
import java.util.List;

public class RequestValidator {

    public static void validateRequest(final CarRentalEnquiryRequest request){
        validateRoute(request.getTravelRoute());
        validateVehicleFuel(request.getVehicleFuel());
    }

    private static void validateRoute(List<String> travelRoute) {
        if (travelRoute.size() < 2){
            throw new BadRequestException("TravelRoute route must contain at least two cities");
        }
    }

    private static void validateVehicleFuel(final String vehicleFuel) {

        boolean isValidFuel = false;

        for (CarRentalConstants.VEHICLE_FUEL c : CarRentalConstants.VEHICLE_FUEL.values()) {
            if (c.name().equalsIgnoreCase(vehicleFuel)) {
                isValidFuel = true;
            }
        }

        if(!isValidFuel){
            throw new BadRequestException("Invalid vehicle fuel type");
        }
    }
}
