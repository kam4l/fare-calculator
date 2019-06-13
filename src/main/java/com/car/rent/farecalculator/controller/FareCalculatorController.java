package com.car.rent.farecalculator.controller;

import com.car.rent.farecalculator.beans.CarRentalEnquiryRequest;
import com.car.rent.farecalculator.beans.CarRentalExpense;
import com.car.rent.farecalculator.service.CalculateFareService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.car.rent.farecalculator.validator.RequestValidator.validateRequest;

@RestController
public class FareCalculatorController {

    private final CalculateFareService calculateFareService;

    @Autowired
    public FareCalculatorController(CalculateFareService calculateFareService) {
        this.calculateFareService = calculateFareService;
    }

    @PostMapping("/calculateFare")
    @ApiOperation(value = "calculate the fare for the given trip params", response = CarRentalExpense.class)
    @ApiResponses(value = {
            @ApiResponse(code= 200, message = "successfully calculated fare"),
            @ApiResponse(code= 400, message = "invalid input parameter(s) supplied")
    })
    public CarRentalExpense calculateFare(@Valid @RequestBody CarRentalEnquiryRequest rentalEnquiryRequest) {
        validateRequest(rentalEnquiryRequest);
        return calculateFareService.calculateCarRentalExpense(rentalEnquiryRequest);
    }

}
