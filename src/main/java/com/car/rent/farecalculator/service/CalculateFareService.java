package com.car.rent.farecalculator.service;

import com.car.rent.farecalculator.beans.CarRentalEnquiryRequest;
import com.car.rent.farecalculator.beans.CarRentalExpense;

public interface CalculateFareService {
    CarRentalExpense calculateCarRentalExpense(CarRentalEnquiryRequest carRentalEnquiryRequest);
}
