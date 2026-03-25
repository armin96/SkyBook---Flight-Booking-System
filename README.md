# SkyBook-Flight-Booking-System
Name : Seyedarmin Hosseinilargani<br>
Student ID: GH1042143<br><br>
 Flight booking management system built with Java Spring Boot and MongoDB.


## Features

- **CRUD Operations** — Full Create, Read, Update, Delete for Flights, Passengers, Bookings, and Airports
- **Flight Search** — Search flights by origin and destination
- **Booking System** — Book flights with Economy, Business, or First Class
- **Seat Management** — Automatic seat tracking (decrement on book, restore on cancel)
- **REST API** — JSON endpoints under /api/ for all entities
- **Web UI** — Beautiful responsive dashboard with Bootstrap 5
- **Error Handling** — Global @ControllerAdvice with custom exceptions and error pages
- **Sample Data** — Pre-loaded airports, flights, and passengers

##  Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+
- MongoDB (running on localhost:27017)

### Run the Application

```bash
# Clone the repository
git clone <repository-url>

# Navigate to project directory
cd project

# Build and run
mvn spring-boot:run
```

The application will start on http://localhost:8080

### MongoDB Setup
Make sure MongoDB is running locally. The application will automatically create the flight_booking_db database.



## 🌐 API Endpoints

### Flights
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/api/flights` | Get all flights |
| GET | `/api/flights/{id}` | Get flight by ID |
| GET | `/api/flights/search?origin=LHR&destination=JFK` | Search flights |
| POST | `/api/flights` | Create a flight |
| PUT | `/api/flights/{id}` | Update a flight |
| DELETE | `/api/flights/{id}` | Delete a flight |

### Bookings
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/api/bookings` | Get all bookings |
| GET | `/api/bookings/{id}` | Get booking by ID |
| POST | `/api/bookings` | Create a booking |
| PUT | `/api/bookings/{id}/cancel` | Cancel a booking |
| DELETE | `/api/bookings/{id}` | Delete a booking |

### Passengers
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/api/passengers` | Get all passengers |
| GET | `/api/passengers/{id}` | Get passenger by ID |
| POST | `/api/passengers` | Create a passenger |
| PUT | `/api/passengers/{id}` | Update a passenger |
| DELETE | `/api/passengers/{id}` | Delete a passenger |

##  Database Schema (MongoDB Documents)

### Airport
```json
{
  "code": "LHR",
  "name": "Heathrow Airport",
  "city": "London",
  "country": "United Kingdom"
}
```

### Flight
```json
{
  "flightNumber": "BA-2490",
  "origin": { "code": "LHR", "name": "Heathrow Airport", "city": "London", "country": "UK" },
  "destination": { "code": "JFK", "name": "JFK Airport", "city": "New York", "country": "US" },
  "departureTime": "2026-03-26T08:30:00",
  "arrivalTime": "2026-03-26T11:45:00",
  "price": 450.00,
  "availableSeats": 120,
  "status": "SCHEDULED"
}
```

=
