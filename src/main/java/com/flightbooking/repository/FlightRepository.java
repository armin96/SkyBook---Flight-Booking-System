package com.flightbooking.repository;

import com.flightbooking.model.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface FlightRepository extends MongoRepository<Flight, String> {


    Optional<Flight> findByFlightNumber(String flightNumber);


    List<Flight> findByOriginCode(String code);


    List<Flight> findByDestinationCode(String code);


    List<Flight> findByOriginCodeAndDestinationCode(String originCode, String destinationCode);


    List<Flight> findByStatus(String status);


    List<Flight> findByDepartureTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("{ 'origin.code': ?0, 'destination.code': ?1, 'availableSeats': { $gt: 0 }, 'status': 'SCHEDULED' }")
    List<Flight> findAvailableFlights(String originCode, String destinationCode);
}
