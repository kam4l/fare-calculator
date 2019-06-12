package com.car.rent.farecalculator.controller;

import com.car.rent.farecalculator.beans.CarRentalEnquiryRequest;
import com.car.rent.farecalculator.beans.CarRentalExpense;
import com.car.rent.farecalculator.service.CalculateFareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.BadRequestException;

import static com.car.rent.farecalculator.validator.RequestValidator.validateRequest;

@RestController
public class FareCalculatorController {

    private final CalculateFareService calculateFareService;

    @Autowired
    public FareCalculatorController(CalculateFareService calculateFareService) {
        this.calculateFareService = calculateFareService;
    }

    @PostMapping("/calculateFare")
    public CarRentalExpense calculateFare(@RequestBody CarRentalEnquiryRequest rentalEnquiryRequest) {
        validateRequest(rentalEnquiryRequest);
        return calculateFareService.calculateCarRentalExpense(rentalEnquiryRequest);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity badRequest(final BadRequestException ex){
        return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
