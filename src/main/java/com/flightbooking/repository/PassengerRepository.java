package com.flightbooking.repository;

import com.flightbooking.model.Passenger;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;


@Repository
public interface PassengerRepository extends MongoRepository<Passenger, String> {


    Optional<Passenger> findByEmail(String email);


    Optional<Passenger> findByPassportNumber(String passportNumber);


    List<Passenger> findByLastNameContainingIgnoreCase(String lastName);
}
