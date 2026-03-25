package com.flightbooking.controller;

import com.flightbooking.model.Booking;
import com.flightbooking.service.BookingService;
import com.flightbooking.service.FlightService;
import com.flightbooking.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final FlightService flightService;
    private final PassengerService passengerService;

    @Autowired
    public BookingController(BookingService bookingService,
                             FlightService flightService,
                             PassengerService passengerService) {
        this.bookingService = bookingService;
        this.flightService = flightService;
        this.passengerService = passengerService;
    }

    @GetMapping
    public String listBookings(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "bookings/list";
    }

    @GetMapping("/{id}")
    public String bookingDetail(@PathVariable String id, Model model) {
        model.addAttribute("booking", bookingService.getBookingById(id));
        return "bookings/detail";
    }

    @GetMapping("/new")
    public String newBookingForm(@RequestParam String flightId, Model model) {
        model.addAttribute("flight", flightService.getFlightById(flightId));
        model.addAttribute("passengers", passengerService.getAllPassengers());
        return "bookings/form";
    }

    /** Handles booking form submission. */
    @PostMapping("/save")
    public String createBooking(@RequestParam String passengerId,
                                @RequestParam String flightId,
                                @RequestParam String seatClass,
                                RedirectAttributes redirectAttributes) {
        try {
            Booking booking = bookingService.createBooking(passengerId, flightId, seatClass);
            redirectAttributes.addFlashAttribute("success",
                    "Booking confirmed! Booking ID: " + booking.getId());
            return "redirect:/bookings/" + booking.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Booking failed: " + e.getMessage());
            return "redirect:/flights/" + flightId;
        }
    }

    @GetMapping("/{id}/cancel")
    public String cancelBooking(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            bookingService.cancelBooking(id);
            redirectAttributes.addFlashAttribute("success", "Booking cancelled successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Cancellation failed: " + e.getMessage());
        }
        return "redirect:/bookings/" + id;
    }

    @GetMapping("/{id}/delete")
    public String deleteBooking(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            bookingService.deleteBooking(id);
            redirectAttributes.addFlashAttribute("success", "Booking deleted.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/bookings";
    }
}