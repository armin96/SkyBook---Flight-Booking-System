package com.flightbooking.repository;

import com.flightbooking.model.Airport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;


@Repository
public interface AirportRepository extends MongoRepository<Airport, String> {



    Optional<Airport> findByCode(String code);


    List<Airport> findByCountry(String country);


    List<Airport> findByCity(String city);
}
