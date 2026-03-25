package com.flightbooking.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

@Document(collection = "bookings")
public class Booking {

    @Id
    private String id;

    @NotNull(message = "Passenger information is required")
    private Passenger passenger;

    @NotNull(message = "Flight information is required")
    private Flight flight;

    private LocalDateTime bookingDate;

    @NotBlank(message = "Seat class is required")
    private String seatClass; // ECONOMY, BUSINESS, FIRST_CLASS

    @NotBlank(message = "Booking status is required")
    private String status; // CONFIRMED, CANCELLED, COMPLETED

    @Positive(message = "Total price must be positive")
    private double totalPrice;

    public Booking() {
        this.bookingDate = LocalDateTime.now();
        this.status = "CONFIRMED";
    }

    public Booking(Passenger passenger, Flight flight, String seatClass, double totalPrice) {
        this.passenger = passenger;
        this.flight = flight;
        this.seatClass = seatClass;
        this.totalPrice = totalPrice;
        this.bookingDate = LocalDateTime.now();
        this.status = "CONFIRMED";
    }

    // Getters and Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Passenger getPassenger() {
        return passenger;
    }
    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Flight getFlight() {
        return flight;
    }
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getSeatClass() {
        return seatClass;
    }
    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public static double calculatePrice(double basePrice, String seatClass) {
        return switch (seatClass) {
            case "BUSINESS" -> basePrice * 2.5;
            case "FIRST_CLASS" -> basePrice * 4.0;
            default -> basePrice; // ECONOMY
        };
    }

    @Override
    public String toString() {
        return "Booking #" + id + " - " + passenger.getFullName() +
                " on " + flight.getFlightNumber() + " (" + status + ")";
    }
}