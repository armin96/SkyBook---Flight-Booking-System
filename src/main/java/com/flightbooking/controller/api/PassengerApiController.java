package com.flightbooking.controller.api;

import com.flightbooking.model.Passenger;
import com.flightbooking.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/passengers")
public class PassengerApiController {

    private final PassengerService passengerService;

    @Autowired
    public PassengerApiController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping
    public List < Passenger > getAllPassengers() {
        return passengerService.getAllPassengers();
    }

    @GetMapping("/{id}")
    public Passenger getPassengerById(@PathVariable String id) {
        return passengerService.getPassengerById(id);
    }

    @PostMapping
    public Passenger createPassenger(@RequestBody Passenger passenger) {
        return passengerService.createPassenger(passenger);
    }

    @PutMapping("/{id}")
    public Passenger updatePassenger(@PathVariable String id, @RequestBody Passenger passenger) {
        return passengerService.updatePassenger(id, passenger);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity < Void > deletePassenger(@PathVariable String id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }
}