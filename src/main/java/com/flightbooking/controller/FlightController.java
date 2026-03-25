package com.flightbooking.controller;

import com.flightbooking.model.Flight;
import com.flightbooking.model.Airport;
import com.flightbooking.service.FlightService;
import com.flightbooking.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import java.util.List;

@Controller
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;
    private final AirportService airportService;

    @Autowired
    public FlightController(FlightService flightService, AirportService airportService) {
        this.flightService = flightService;
        this.airportService = airportService;
    }

    @GetMapping
    public String listFlights(@RequestParam(required = false) String origin,
                              @RequestParam(required = false) String destination,
                              Model model) {
        List < Flight > flights;
        if ((origin != null && !origin.isEmpty()) || (destination != null && !destination.isEmpty())) {
            flights = flightService.searchFlights(origin, destination);
            model.addAttribute("searchOrigin", origin);
            model.addAttribute("searchDestination", destination);
        } else {
            flights = flightService.getAllFlights();
        }
        model.addAttribute("flights", flights);
        model.addAttribute("airports", airportService.getAllAirports());
        return "flights/list";
    }

    @GetMapping("/{id}")
    public String flightDetail(@PathVariable String id, Model model) {
        model.addAttribute("flight", flightService.getFlightById(id));
        return "flights/detail";
    }

    @GetMapping("/new")
    public String newFlightForm(Model model) {
        model.addAttribute("flight", new Flight());
        model.addAttribute("airports", airportService.getAllAirports());
        return "flights/form";
    }

    @GetMapping("/{id}/edit")
    public String editFlightForm(@PathVariable String id, Model model) {
        model.addAttribute("flight", flightService.getFlightById(id));
        model.addAttribute("airports", airportService.getAllAirports());
        return "flights/form";
    }

    @PostMapping("/save")
    public String saveFlight(@ModelAttribute Flight flight,
                             @RequestParam String originId,
                             @RequestParam String destinationId,
                             RedirectAttributes redirectAttributes) {
        try {
            Airport origin = airportService.getAirportById(originId);
            Airport destination = airportService.getAirportById(destinationId);
            flight.setOrigin(origin);
            flight.setDestination(destination);

            if (flight.getId() != null && !flight.getId().isEmpty()) {
                flightService.updateFlight(flight.getId(), flight);
                redirectAttributes.addFlashAttribute("success", "Flight updated successfully!");
            } else {
                flight.setId(null);
                flightService.createFlight(flight);
                redirectAttributes.addFlashAttribute("success", "Flight created successfully!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error saving flight: " + e.getMessage());
        }
        return "redirect:/flights";
    }

    @GetMapping("/{id}/delete")
    public String deleteFlight(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            flightService.deleteFlight(id);
            redirectAttributes.addFlashAttribute("success", "Flight deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting flight: " + e.getMessage());
        }
        return "redirect:/flights";
    }
}