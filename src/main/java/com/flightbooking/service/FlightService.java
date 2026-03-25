package com.flightbooking.service;

import com.flightbooking.model.Flight;
import com.flightbooking.repository.FlightRepository;
import com.flightbooking.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List < Flight > getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight getFlightById(String id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", id));
    }

    public Flight getFlightByNumber(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "flightNumber", flightNumber));
    }

    public List < Flight > searchFlights(String originCode, String destinationCode) {
        if (originCode != null && !originCode.isEmpty() && destinationCode != null && !destinationCode.isEmpty()) {
            return flightRepository.findByOriginCodeAndDestinationCode(originCode, destinationCode);
        } else if (originCode != null && !originCode.isEmpty()) {
            return flightRepository.findByOriginCode(originCode);
        } else if (destinationCode != null && !destinationCode.isEmpty()) {
            return flightRepository.findByDestinationCode(destinationCode);
        }
        return flightRepository.findAll();
    }

    public List < Flight > getFlightsByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        return flightRepository.findByDepartureTimeBetween(startOfDay, endOfDay);
    }

    /**
     * Finds available flights (scheduled with seats) from origin to destination.
     */
    public List < Flight > getAvailableFlights(String originCode, String destinationCode) {
        return flightRepository.findAvailableFlights(originCode, destinationCode);
    }

    public Flight createFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public Flight updateFlight(String id, Flight flightDetails) {
        Flight flight = getFlightById(id);
        flight.setFlightNumber(flightDetails.getFlightNumber());
        flight.setOrigin(flightDetails.getOrigin());
        flight.setDestination(flightDetails.getDestination());
        flight.setDepartureTime(flightDetails.getDepartureTime());
        flight.setArrivalTime(flightDetails.getArrivalTime());
        flight.setPrice(flightDetails.getPrice());
        flight.setAvailableSeats(flightDetails.getAvailableSeats());
        flight.setStatus(flightDetails.getStatus());
        return flightRepository.save(flight);
    }

    public void deleteFlight(String id) {
        Flight flight = getFlightById(id);
        flightRepository.delete(flight);
    }

    public long count() {
        return flightRepository.count();
    }
}