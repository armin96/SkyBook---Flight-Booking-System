package com.flightbooking.service;

import com.flightbooking.model.Passenger;
import com.flightbooking.repository.PassengerRepository;
import com.flightbooking.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public List < Passenger > getAllPassengers() {
        return passengerRepository.findAll();
    }

    public Passenger getPassengerById(String id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger", "id", id));
    }

    public Passenger getPassengerByEmail(String email) {
        return passengerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger", "email", email));
    }

    public List < Passenger > searchPassengers(String lastName) {
        if (lastName != null && !lastName.isEmpty()) {
            return passengerRepository.findByLastNameContainingIgnoreCase(lastName);
        }
        return passengerRepository.findAll();
    }

    public Passenger createPassenger(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    public Passenger updatePassenger(String id, Passenger passengerDetails) {
        Passenger passenger = getPassengerById(id);
        passenger.setFirstName(passengerDetails.getFirstName());
        passenger.setLastName(passengerDetails.getLastName());
        passenger.setEmail(passengerDetails.getEmail());
        passenger.setPhone(passengerDetails.getPhone());
        passenger.setPassportNumber(passengerDetails.getPassportNumber());
        return passengerRepository.save(passenger);
    }

    public void deletePassenger(String id) {
        Passenger passenger = getPassengerById(id);
        passengerRepository.delete(passenger);
    }

    public long count() {
        return passengerRepository.count();
    }
}