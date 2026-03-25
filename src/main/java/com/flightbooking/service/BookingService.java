package com.flightbooking.service;

import com.flightbooking.model.Booking;
import com.flightbooking.model.Flight;
import com.flightbooking.model.Passenger;
import com.flightbooking.repository.BookingRepository;
import com.flightbooking.exception.FlightFullException;
import com.flightbooking.exception.InvalidBookingException;
import com.flightbooking.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final FlightService flightService;
    private final PassengerService passengerService;

    @Autowired
    public BookingService(BookingRepository bookingRepository,
                          FlightService flightService,
                          PassengerService passengerService) {
        this.bookingRepository = bookingRepository;
        this.flightService = flightService;
        this.passengerService = passengerService;
    }

    public List < Booking > getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(String id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", id));
    }

    public List < Booking > getBookingsByPassenger(String passengerId) {
        return bookingRepository.findByPassengerId(passengerId);
    }

    public List < Booking > getBookingsByFlight(String flightId) {
        return bookingRepository.findByFlightId(flightId);
    }

    public Booking createBooking(String passengerId, String flightId, String seatClass) {
        // Fetch and validate passenger and flight
        Passenger passenger = passengerService.getPassengerById(passengerId);
        Flight flight = flightService.getFlightById(flightId);

        // Check seat availability
        if (flight.getAvailableSeats() <= 0) {
            throw new FlightFullException(flight.getFlightNumber());
        }

        double totalPrice = Booking.calculatePrice(flight.getPrice(), seatClass);

        Booking booking = new Booking(passenger, flight, seatClass, totalPrice);
        booking.setBookingDate(LocalDateTime.now());

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightService.updateFlight(flight.getId(), flight);

        logger.info("Booking created: {} on flight {} ({})",
                passenger.getFullName(), flight.getFlightNumber(), seatClass);

        return bookingRepository.save(booking);
    }

    public Booking cancelBooking(String bookingId) {
        Booking booking = getBookingById(bookingId);

        if ("CANCELLED".equals(booking.getStatus())) {
            throw new InvalidBookingException("Booking #" + bookingId + " is already cancelled.");
        }

        booking.setStatus("CANCELLED");

        Flight flight = flightService.getFlightById(booking.getFlight().getId());
        flight.setAvailableSeats(flight.getAvailableSeats() + 1);
        flightService.updateFlight(flight.getId(), flight);

        logger.info("Booking #{} cancelled. Seat restored on flight {}.",
                bookingId, flight.getFlightNumber());

        return bookingRepository.save(booking);
    }

    public void deleteBooking(String id) {
        Booking booking = getBookingById(id);
        bookingRepository.delete(booking);
    }

    public long count() {
        return bookingRepository.count();
    }

    public long countConfirmed() {
        return bookingRepository.findByStatus("CONFIRMED").size();
    }
}