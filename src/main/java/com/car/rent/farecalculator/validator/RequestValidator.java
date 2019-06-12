package com.car.rent.farecalculator.validator;

import com.car.rent.farecalculator.beans.CarRentalEnquiryRequest;
import com.car.rent.farecalculator.constant.CarRentalConstants;
import com.car.rent.farecalculator.constant.CarRentalConstants.VEHICLE_TYPE;

import javax.ws.rs.BadRequestException;
import java.util.List;

public class RequestValidator {

    public static void validateRequest(final CarRentalEnquiryRequest request){
        validateRoute(request.getTravelRoute());

        validateVehicleType(request.getVehicleType());

        validateVehicleFuel(request.getVehicleFuel());
    }

    private static void validateRoute(List<String> travelRoute) {
        if (travelRoute.size() < 2){
            throw new BadRequestException("Travel route must contain at least two cities");
        }
    }

    private static void validateVehicleType(final String vehicleType) {

        boolean isValidVehicle = false;

        for (VEHICLE_TYPE c : VEHICLE_TYPE.values()) {
            if (c.name().equalsIgnoreCase(vehicleType)) {
                isValidVehicle = true;
            }
        }

        if(!isValidVehicle){
            throw new BadRequestException("Invalid vehicle type");
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
