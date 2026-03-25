package com.flightbooking.config;

import com.flightbooking.model.Airport;
import com.flightbooking.model.Flight;
import com.flightbooking.model.Passenger;
import com.flightbooking.repository.AirportRepository;
import com.flightbooking.repository.FlightRepository;
import com.flightbooking.repository.PassengerRepository;
import com.flightbooking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;


@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataSeeder.class);

    @Autowired
    private AirportRepository airportRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public void run(String... args) {
        // Only seed if database is empty
        if (airportRepository.count() > 0) {
            logger.info("Database already contains data. Skipping seeding.");
            return;
        }

        logger.info("Seeding database with sample data...");

        // === AIRPORTS ===
        Airport lhr = airportRepository.save(new Airport("LHR", "Heathrow Airport", "London", "United Kingdom"));
        Airport cdg = airportRepository.save(new Airport("CDG", "Charles de Gaulle Airport", "Paris", "France"));
        Airport jfk = airportRepository.save(new Airport("JFK", "John F. Kennedy Airport", "New York", "United States"));
        Airport dxb = airportRepository.save(new Airport("DXB", "Dubai International Airport", "Dubai", "UAE"));
        Airport ika = airportRepository.save(new Airport("IKA", "Imam Khomeini Airport", "Tehran", "Iran"));
        Airport ist = airportRepository.save(new Airport("IST", "Istanbul Airport", "Istanbul", "Turkey"));
        Airport fra = airportRepository.save(new Airport("FRA", "Frankfurt Airport", "Frankfurt", "Germany"));
        Airport nrt = airportRepository.save(new Airport("NRT", "Narita International Airport", "Tokyo", "Japan"));
        Airport sin = airportRepository.save(new Airport("SIN", "Changi Airport", "Singapore", "Singapore"));
        Airport syd = airportRepository.save(new Airport("SYD", "Sydney Airport", "Sydney", "Australia"));

        logger.info("Seeded {} airports", airportRepository.count());


        LocalDateTime now = LocalDateTime.now();

        flightRepository.save(new Flight("BA-2490", lhr, jfk,
                now.plusDays(1).withHour(8).withMinute(30),
                now.plusDays(1).withHour(11).withMinute(45),
                450.00, 120));

        flightRepository.save(new Flight("AF-1821", cdg, dxb,
                now.plusDays(1).withHour(14).withMinute(0),
                now.plusDays(1).withHour(22).withMinute(30),
                380.00, 85));

        flightRepository.save(new Flight("EK-0204", dxb, lhr,
                now.plusDays(2).withHour(3).withMinute(15),
                now.plusDays(2).withHour(7).withMinute(45),
                520.00, 200));

        flightRepository.save(new Flight("IR-0721", ika, ist,
                now.plusDays(2).withHour(10).withMinute(0),
                now.plusDays(2).withHour(13).withMinute(30),
                280.00, 150));

        flightRepository.save(new Flight("TK-1990", ist, fra,
                now.plusDays(3).withHour(6).withMinute(45),
                now.plusDays(3).withHour(9).withMinute(15),
                310.00, 90));

        flightRepository.save(new Flight("LH-7340", fra, nrt,
                now.plusDays(3).withHour(12).withMinute(0),
                now.plusDays(4).withHour(6).withMinute(30),
                780.00, 60));

        flightRepository.save(new Flight("SQ-0025", sin, syd,
                now.plusDays(4).withHour(9).withMinute(0),
                now.plusDays(4).withHour(18).withMinute(30),
                620.00, 110));

        flightRepository.save(new Flight("JL-0044", nrt, jfk,
                now.plusDays(5).withHour(17).withMinute(0),
                now.plusDays(5).withHour(17).withMinute(30),
                890.00, 75));

        flightRepository.save(new Flight("QF-0002", syd, sin,
                now.plusDays(5).withHour(11).withMinute(15),
                now.plusDays(5).withHour(17).withMinute(0),
                550.00, 95));

        flightRepository.save(new Flight("EK-0978", dxb, ika,
                now.plusDays(6).withHour(20).withMinute(0),
                now.plusDays(6).withHour(22).withMinute(30),
                240.00, 180));

        logger.info("Seeded {} flights", flightRepository.count());


        passengerRepository.save(new Passenger("John", "Smith", "john.smith@email.com", "+44-7700-123456", "GB123456789"));
        passengerRepository.save(new Passenger("Sarah", "Johnson", "sarah.j@email.com", "+1-555-0123", "US987654321"));
        passengerRepository.save(new Passenger("Ali", "Hosseini", "ali.hosseini@email.com", "+98-912-1234567", "IR112233445"));
        passengerRepository.save(new Passenger("Marie", "Dupont", "marie.dupont@email.com", "+33-6-12345678", "FR556677889"));
        passengerRepository.save(new Passenger("Yuki", "Tanaka", "yuki.tanaka@email.com", "+81-90-1234-5678", "JP998877665"));
        passengerRepository.save(new Passenger("Emma", "Wilson", "emma.w@email.com", "+61-4-1234-5678", "AU445566778"));
        passengerRepository.save(new Passenger("Mehmet", "Yilmaz", "mehmet.y@email.com", "+90-532-1234567", "TR334455667"));
        passengerRepository.save(new Passenger("Fatima", "Al-Rashid", "fatima.ar@email.com", "+971-50-1234567", "AE223344556"));

        logger.info("Seeded {} passengers", passengerRepository.count());
        logger.info("Database seeding completed successfully!");
    }
}
