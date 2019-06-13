package com.car.rent.farecalculator.controller;

import com.car.rent.farecalculator.beans.CarRentalEnquiryRequest;
import com.car.rent.farecalculator.beans.CarRentalExpense;
import com.car.rent.farecalculator.service.CalculateFareService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.BadRequestException;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class FareCalculatorControllerTest {

    @Mock
    private CalculateFareService service;

    private FareCalculatorController controller;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        controller = new FareCalculatorController(service);
    }

    @Test
    public void testSuccessfulFareEstimation(){
        // given
        // Service responds with success response
        final CarRentalExpense expectedResponse = new CarRentalExpense(3000d);
        final CarRentalEnquiryRequest request = new CarRentalEnquiryRequest("suv",
                "petrol",
                Arrays.asList("pune", "mumbai"),
                5,
                false);
        when(service.calculateCarRentalExpense(request)).thenReturn(expectedResponse);
        // when
        final CarRentalExpense actualResponse = controller.calculateFare(request);
        // then
        assertNotNull(actualResponse);
        assertEquals(3000d, actualResponse.getEstimatedExpense(), 0);
    }

    @Test (expected = BadRequestException.class)
    public void testBadRequest(){
        // given
        // Service responds with bad request
        final CarRentalEnquiryRequest request = new CarRentalEnquiryRequest("bike",
                "petrol",
                Arrays.asList("pune", "mumbai"),
                5,
                false);
        when(service.calculateCarRentalExpense(request)).thenThrow(new BadRequestException("Invalid vehicle type."));

        // when
        controller.calculateFare(request);
    }
}