package com.flightbooking.controller.api;

import com.flightbooking.model.Flight;
import com.flightbooking.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightApiController {

    private final FlightService flightService;

    @Autowired
    public FlightApiController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public List < Flight > getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/{id}")
    public Flight getFlightById(@PathVariable String id) {
        return flightService.getFlightById(id);
    }

    @GetMapping("/search")
    public List < Flight > searchFlights(@RequestParam(required = false) String origin,
                                         @RequestParam(required = false) String destination) {
        return flightService.searchFlights(origin, destination);
    }

    @PostMapping
    public Flight createFlight(@RequestBody Flight flight) {
        return flightService.createFlight(flight);
    }

    @PutMapping("/{id}")
    public Flight updateFlight(@PathVariable String id, @RequestBody Flight flight) {
        return flightService.updateFlight(id, flight);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity < Void > deleteFlight(@PathVariable String id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }
}