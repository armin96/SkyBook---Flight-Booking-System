package com.flightbooking.controller.api;

import com.flightbooking.model.Booking;
import com.flightbooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingApiController {

    private final BookingService bookingService;

    @Autowired
    public BookingApiController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List < Booking > getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable String id) {
        return bookingService.getBookingById(id);
    }

    @PostMapping
    public Booking createBooking(@RequestBody Map < String, String > request) {
        return bookingService.createBooking(
                request.get("passengerId"),
                request.get("flightId"),
                request.get("seatClass")
        );
    }

    @PutMapping("/{id}/cancel")
    public Booking cancelBooking(@PathVariable String id) {
        return bookingService.cancelBooking(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity < Void > deleteBooking(@PathVariable String id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}