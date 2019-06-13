package com.car.rent.farecalculator.validator;

import com.car.rent.farecalculator.beans.CarRentalEnquiryRequest;
import org.junit.Test;

import javax.ws.rs.BadRequestException;
import java.util.Arrays;
import java.util.Collections;

import static com.car.rent.farecalculator.validator.RequestValidator.validateRequest;

public class RequestValidatorTest {


    @Test(expected = BadRequestException.class)
    public void validateRouteCitiesForMinimumNumber() {

        //given
        CarRentalEnquiryRequest request = new CarRentalEnquiryRequest(null, null, Collections.singletonList("Pune"), 3, false);

        //when
        validateRequest(request);
    }



    @Test(expected = BadRequestException.class)
    public void validateFuelTypeForInvalidValue() {

        //given
        CarRentalEnquiryRequest request = new CarRentalEnquiryRequest(null, "Electric", Arrays.asList("Pune", "Chennai"), 3, false);

        //when
        validateRequest(request);
    }
}