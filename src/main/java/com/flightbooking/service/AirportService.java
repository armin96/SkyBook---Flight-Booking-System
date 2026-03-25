package com.flightbooking.service;

import com.flightbooking.model.Airport;
import com.flightbooking.repository.AirportRepository;
import com.flightbooking.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportService {

    private final AirportRepository airportRepository;

    @Autowired
    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public List < Airport > getAllAirports() {
        return airportRepository.findAll();
    }

    public Airport getAirportById(String id) {
        return airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport", "id", id));
    }

    public Airport getAirportByCode(String code) {
        return airportRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Airport", "code", code));
    }

    public Airport createAirport(Airport airport) {
        return airportRepository.save(airport);
    }

    public Airport updateAirport(String id, Airport airportDetails) {
        Airport airport = getAirportById(id);
        airport.setCode(airportDetails.getCode());
        airport.setName(airportDetails.getName());
        airport.setCity(airportDetails.getCity());
        airport.setCountry(airportDetails.getCountry());
        return airportRepository.save(airport);
    }

    public void deleteAirport(String id) {
        Airport airport = getAirportById(id);
        airportRepository.delete(airport);
    }

    public long count() {
        return airportRepository.count();
    }
}