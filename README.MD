# Eventify - Event Management System

A RESTful API for managing events and participants built with Spring Boot and Spring Data JPA.

## Overview
Eventify is an event management system designed to:
- Create and manage events (conferences, weddings, training sessions, etc.)
- Add participants via bulk file upload or manually
- Search and filter events efficiently

## Features

### Event Management
- **Create Events**: Add new events with title, description, date, and location
- **Update Events**: Full update (PUT) or partial update (PATCH)
- **Delete Events**: Cascade delete removes all participants
- **List Events**: Retrieve all events or a single event by ID
- **Search Events**: Search by title, description, or location

### Participant Management
- **Bulk Upload**: Import participants via CSV file
- **Manual Upload**: Create a participant for an event
- **List Participants**: Get all participants for a specific event

### API Features
- **RESTful Design**: Proper HTTP verbs and status codes
- **Swagger/OpenAPI**: Interactive API documentation
- **Error Handling**: Meaningful error messages
- **Data Validation**: Input validation with clear feedback

## Technologies Used
- **Spring Boot**: Application framework
- **Spring Data JPA**: Data persistence layer
- **Postgre SQL**: Database
- **Hibernate**: ORM implementation
- **SpringDoc OpenAPI**: API documentation (Swagger)
- **Java JDK 25**: Primary Language
- **Maven 3.9.11**: Build tool

## Installation

1. **Clone the repository**
   ```sh
   git clone https://github.com/uthmaniv/eventify.git
   cd eventify
   ```
2. **Build the project**
   ```sh
   mvn clean install
   ```
3. **Run the application**
   ```sh
   mvn spring-boot:run
   ```
   The application will start on [http://localhost:8080](http://localhost:8080)

## Quick Test
Once running, access:
- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **JDBC URL**: `jdbc:postgresql://localhost:5432/event_db`
- **Username**: `postgres`
- **Password**: `uthmaniv12`

## API Documentation

### Swagger UI
The API documentation is available at:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## API Endpoints

### Events
| Method | Endpoint                | Description              | Status Codes     |
|--------|-------------------------|--------------------------|------------------|
| POST   | /api/events/add         | Create new event         | 201, 400         |
| GET    | /api/events             | Get all events           | 200              |
| GET    | /api/events/title       | Get event by title       | 200, 404         |
| PUT    | /api/events/update      | Update event (full)      | 200, 404, 400    |
| PATCH  | /api/events/title       | Update event title       | 200, 404         |
| PATCH  | /api/events/description | Update event description | 200, 404         |
| PATCH  | /api/events/location    | Update event location    | 200, 404         |
| PATCH  | /api/events/date-time   | Update event date-time   | 200, 404         |
| DELETE | /api/events             | Delete event             | 204, 404         |

### Participants
| Method | Endpoint                   | Description              | Status Codes     |
|--------|----------------------------|--------------------------|------------------|
| GET    | /api/participants          | Get all participants     | 200, 404         |
| POST   | /api/participants          | Create a participant     | 201, 400         |
| POST   | /api/participants/upload   | Upload participants file | 201, 400, 404    |

## Author
**Usman Yahaya**

- GitHub: [@uthmaniv](https://github.com/uthmaniv)
- Email: uthmanyahayababa@gmail.com