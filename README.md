# Realtime Event Ticketing System

## Overview
A multi-threaded event ticketing system built with Spring Boot and MongoDB, designed to handle concurrent ticket sales with advanced configuration and management capabilities.

## Features
- Real-time ticket sales
- Concurrent ticket purchasing
- Vendor and customer management
- Dynamic ticket pool configuration
- Thread-based ticket release and retrieval

## Tech Stack
- Backend: Spring Boot
- Frontend: React
- Database: MongoDB
- Concurrency: Java Threads


## System Architecture

### Key Components
1. **Vendor Management**
   - Create and manage event vendors
   - Associate events with vendors

2. **Event Management**
   - Create, update, and delete events
   - Configure ticket pools
   - Start ticketing processes

3. **Ticket Management**
   - Dynamic ticket generation
   - Concurrent ticket sales
   - Ticket availability tracking

4. **Customer Management**
   - Customer registration
   - Ticket purchasing

## Configuration

### System Configuration Parameters
- Total Tickets
- Ticket Release Rate
- Customer Retrieval Rate
- Maximum Ticket Capacity

## Endpoints

### Configuration
- `POST /api/configure/setup`: Configure system parameters
- `GET /api/configure`: Get current system configuration

### Customers
- `POST /api/customers`: Create customer
- `GET /api/customers`: List all customers
- `GET /api/customers/id/{id}`: Get customer by ID
- `GET /api/customers/email/{email}`: Get customer by email

### Vendors
- `POST /api/vendors`: Create vendor
- `GET /api/vendors`: List all vendors
- `GET /api/vendors/id/{id}`: Get vendor by ID
- `GET /api/vendors/email/{email}`: Get vendor by email

### Events
- `POST /api/events/create`: Create event
- `GET /api/events/all`: List all events
- `GET /api/events/{eventId}`: Get event details
- `POST /api/events/{eventId}/start-ticketing`: Start ticket sales
- `POST /api/events/{eventId}/buy-ticket`: Purchase tickets

## Concurrency Model

### Vendor Thread
- Responsible for ticket generation
- Releases tickets at configured rate
- Stops when ticket pool reaches maximum capacity

### Customer Thread
- Purchases tickets concurrently
- Retrieves tickets based on configuration
- Handles multiple ticket purchases

## Database Schema
- Utilizes MongoDB for flexible, document-based storage
- Supports complex relationships between entities

## Setup and Installation

### Prerequisites
- Java 21
- MongoDB
- React


### Steps
1. Clone the repository
2. Configure MongoDB connection
3. Build the project
4. Run the application

```bash
# Clone repository
git clone https://github.com/VismalDeSilva/Real-Time-Event-Ticketing-System.git

