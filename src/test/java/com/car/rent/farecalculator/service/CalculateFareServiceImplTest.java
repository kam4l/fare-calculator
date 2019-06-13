package com.car.rent.farecalculator.service;

import com.car.rent.farecalculator.beans.CarRentalEnquiryRequest;
import com.car.rent.farecalculator.beans.CarRentalExpense;
import com.car.rent.farecalculator.repository.CarRentalRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import rx.Observable;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CalculateFareServiceImplTest {

    private CalculateFareServiceImpl service;

    @Mock
    CarRentalRepository repository;

    @Before
    public void setUp(){
        initMocks(this);
    }

    @Test
    public void calculateFareForSwiftType(){
        //given
        //vehicle name = swift
        CarRentalEnquiryRequest request = new CarRentalEnquiryRequest("swift", "diesel", Arrays.asList("Pune", "Mumbai", "Bangalore", "Pune"), 3, false);
        mockRepoResponse();
        when(repository.getRatePerHour()).thenReturn(Observable.just(15d));

        service = new CalculateFareServiceImpl(repository);
        //when
        CarRentalExpense expense = service.calculateCarRentalExpense(request);

        //then
        assertThat(expense.getEstimatedExpense(), is(28000d));
    }
   @Test
    public void calculateFareForPetrolType(){
        //given
       //fuel type = petrol
        CarRentalEnquiryRequest request = new CarRentalEnquiryRequest("swift", "petrol", Arrays.asList("Pune", "Mumbai", "Bangalore", "Pune"), 3, false);
       mockRepoResponse();
       when(repository.getRatePerHour()).thenReturn(Observable.just(15d));

        service = new CalculateFareServiceImpl(repository);
        //when
        CarRentalExpense expense = service.calculateCarRentalExpense(request);

        //then
        assertThat(expense.getEstimatedExpense(), is(30000d));
    }

    @Test
    public void calculateFareForAC(){
        //given
        //airConditioned = true
        CarRentalEnquiryRequest request = new CarRentalEnquiryRequest("swift", "petrol", Arrays.asList("Pune", "Mumbai", "Bangalore", "Pune"), 3, true);
        mockRepoResponse();
        when(repository.getRatePerHour()).thenReturn(Observable.just(15d));

        service = new CalculateFareServiceImpl(repository);
        //when
        CarRentalExpense expense = service.calculateCarRentalExpense(request);

        //then
        assertThat(expense.getEstimatedExpense(), is(34000d));
    }


    @Test
    public void calculateFareForExtraPassengers(){
        //given
        // number of passengers = 5 where maximum passenger is only 4
        CarRentalEnquiryRequest request = new CarRentalEnquiryRequest("swift", "petrol", Arrays.asList("Pune", "Mumbai", "Bangalore", "Pune"), 5, true);
        mockRepoResponse();
        when(repository.getRatePerHour()).thenReturn(Observable.just(15d));

        service = new CalculateFareServiceImpl(repository);
        //when
        CarRentalExpense expense = service.calculateCarRentalExpense(request);

        //then
        assertThat(expense.getEstimatedExpense(), is(36000d));
    }

    @Test
    public void calculateFareForBus(){
        //given
        // 2 % discount applicable for bus
        CarRentalEnquiryRequest request = new CarRentalEnquiryRequest("volvo", "petrol", Arrays.asList("Pune", "Mumbai", "Bangalore", "Pune"), 20, true);
        when(repository.getDistance("Pune", "Mumbai")).thenReturn(Observable.just(200d));
        when(repository.getDistance("Mumbai", "Bangalore")).thenReturn(Observable.just(800d));
        when(repository.getDistance("Bangalore", "Pune")).thenReturn(Observable.just(1000d));
        when(repository.getMaxPassengerCount("BUS")).thenReturn(21);
        when(repository.getVehicleType("volvo")).thenReturn(Observable.just("BUS"));
        when(repository.getRatePerHour()).thenReturn(Observable.just(15d));

        service = new CalculateFareServiceImpl(repository);
        //when
        CarRentalExpense expense = service.calculateCarRentalExpense(request);

        //then
        assertThat(expense.getEstimatedExpense(), is(33320d));
    }

    private void mockRepoResponse() {
        when(repository.getDistance("Pune", "Mumbai")).thenReturn(Observable.just(200d));
        when(repository.getDistance("Mumbai", "Bangalore")).thenReturn(Observable.just(800d));
        when(repository.getDistance("Bangalore", "Pune")).thenReturn(Observable.just(1000d));
        when(repository.getMaxPassengerCount("SEDAN")).thenReturn(4);
        when(repository.getVehicleType("swift")).thenReturn(Observable.just("SEDAN"));
    }

}