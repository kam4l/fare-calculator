package com.car.rent.farecalculator.controller;

import com.car.rent.farecalculator.beans.CarRentalEnquiryRequest;
import com.car.rent.farecalculator.beans.CarRentalExpense;
import com.car.rent.farecalculator.service.CalculateFareService;
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
    public CarRentalExpense calculateFare(@Valid @RequestBody CarRentalEnquiryRequest rentalEnquiryRequest) {
        validateRequest(rentalEnquiryRequest);
        return calculateFareService.calculateCarRentalExpense(rentalEnquiryRequest);
    }

}
