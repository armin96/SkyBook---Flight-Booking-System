package com.flightbooking.repository;

import com.flightbooking.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {


    List<Booking> findByPassengerId(String passengerId);


    List<Booking> findByFlightId(String flightId);


    List<Booking> findByStatus(String status);


    List<Booking> findByPassengerEmail(String email);
}
