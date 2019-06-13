package com.car.rent.farecalculator.beans;

public class TravelRoute {

    private String origin;
    private String destination;

    public String getOrigin() {
        return origin;
    }

    public TravelRoute setOrigin(String origin) {
        this.origin = origin;
        return this;
    }

    public String getDestination() {
        return destination;
    }

    public TravelRoute setDestination(String destination) {
        this.destination = destination;
        return this;
    }
}
