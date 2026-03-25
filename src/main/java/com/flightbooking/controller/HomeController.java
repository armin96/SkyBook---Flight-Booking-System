package com.flightbooking.controller;

import com.flightbooking.service.FlightService;
import com.flightbooking.service.BookingService;
import com.flightbooking.service.PassengerService;
import com.flightbooking.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final FlightService flightService;
    private final BookingService bookingService;
    private final PassengerService passengerService;
    private final AirportService airportService;

    @Autowired
    public HomeController(FlightService flightService, BookingService bookingService,
                          PassengerService passengerService, AirportService airportService) {
        this.flightService = flightService;
        this.bookingService = bookingService;
        this.passengerService = passengerService;
        this.airportService = airportService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalFlights", flightService.count());
        model.addAttribute("totalBookings", bookingService.count());
        model.addAttribute("confirmedBookings", bookingService.countConfirmed());
        model.addAttribute("totalPassengers", passengerService.count());
        model.addAttribute("totalAirports", airportService.count());
        model.addAttribute("recentFlights", flightService.getAllFlights().stream().limit(5).toList());
        return "index";
    }
}