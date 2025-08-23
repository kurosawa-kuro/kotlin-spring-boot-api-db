# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Kotlin Spring Boot CRUD Web API project that provides RESTful endpoints for managing customer data with PostgreSQL database integration.

## Common Commands

### Build and Run
- **Build the project**: `./gradlew build`
- **Run the application**: `./gradlew bootRun` or `make dev`
- **Run tests**: `./gradlew test`
- **Run a single test class**: `./gradlew test --tests "com.example.kotlinspringcrudwebapi.KotlinSpringCrudWebApiApplicationTests"`
- **Clean build**: `./gradlew clean build`

### Database Setup
- **Initialize database schema**: `PGPASSWORD=postgres psql -h localhost -U postgres -d local_dev -f sql/001-customer.sql`
- **Check database structure**: `PGPASSWORD=postgres psql -h localhost -U postgres -d local_dev -c "\d customer"`

Database connection details (configured in `application.properties`):
- Host: localhost
- Port: 5432
- Database: local_dev
- Username: postgres
- Password: postgres

## Architecture Overview

### Layered Architecture Pattern
The application follows a standard Spring Boot layered architecture:

1. **Controller Layer** (`CustomerController.kt`)
   - REST API endpoints at `/customers`
   - Handles HTTP requests/responses
   - Uses DTOs (`CustomerRequest`, `CustomerResponse`) for API communication
   - Returns JSON responses with snake_case field naming

2. **Service Layer** (`CustomerService.kt`)
   - Business logic layer with interface and implementation pattern
   - `CustomerService` interface defines contracts
   - `CustomerServiceImpl` implements business operations
   - Orchestrates data flow between controller and repository

3. **Repository Layer** (`CustomerRepository.kt`)
   - Data access layer using Spring JDBC with `NamedParameterJdbcTemplate`
   - Interface-based design with `CustomerRepositoryImpl`
   - Raw SQL queries for database operations
   - Manual mapping between database records and domain objects

4. **Domain Model** (`Customer.kt`)
   - Simple data class representing the customer entity
   - Used across all layers for data transfer

### Key Technologies
- **Framework**: Spring Boot 3.5.5 with Kotlin 1.9.25
- **Database Access**: Spring JDBC (not JPA/Hibernate)
- **Database**: PostgreSQL
- **Build Tool**: Gradle with Kotlin DSL
- **Java Version**: 17

### API Endpoints
- `POST /customers` - Create a new customer
- `GET /customers` - Retrieve all customers
- `PUT /customers/{id}` - Update a customer by ID
- `DELETE /customers/{id}` - Delete a customer by ID

### Testing
- Test files located in `src/test/kotlin/`
- HTTP request examples in `src/test/kotlin/com/example/kotlinspringcrudwebapi/api.http`
- Spring Boot test context available for integration testing