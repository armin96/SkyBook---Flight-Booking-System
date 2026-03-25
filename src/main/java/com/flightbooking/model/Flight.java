package com.flightbooking.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

@Document(collection = "flights")
public class Flight {

    @Id
    private String id;

    @NotBlank(message = "Flight number is required")
    @Indexed(unique = true)
    private String flightNumber;

    @NotNull(message = "Origin airport is required")
    private Airport origin;

    @NotNull(message = "Destination airport is required")
    private Airport destination;

    @NotNull(message = "Departure time is required")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;

    @Positive(message = "Price must be positive")
    private double price;

    @Min(value = 0, message = "Available seats cannot be negative")
    private int availableSeats;

    @NotBlank(message = "Status is required")
    private String status; // SCHEDULED, DEPARTED, ARRIVED, CANCELLED

    // Default constructor
    public Flight() {
        this.status = "SCHEDULED";
    }

    public Flight(String flightNumber, Airport origin, Airport destination,
                  LocalDateTime departureTime, LocalDateTime arrivalTime,
                  double price, int availableSeats) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.availableSeats = availableSeats;
        this.status = "SCHEDULED";
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Airport getOrigin() {
        return origin;
    }

    public void setOrigin(Airport origin) {
        this.origin = origin;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDuration() {
        if (departureTime == null || arrivalTime == null) return "N/A";
        long minutes = java.time.Duration.between(departureTime, arrivalTime).toMinutes();
        return (minutes / 60) + "h " + (minutes % 60) + "m";
    }

    @Override
    public String toString() {
        return flightNumber + ": " + origin.getCode() + " → " + destination.getCode();
    }
}