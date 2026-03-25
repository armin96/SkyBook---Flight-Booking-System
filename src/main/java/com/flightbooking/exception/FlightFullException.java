package com.flightbooking.exception;


public class FlightFullException extends RuntimeException {

    public FlightFullException(String flightNumber) {
        super("Flight " + flightNumber + " is fully booked. No available seats.");
    }
}
