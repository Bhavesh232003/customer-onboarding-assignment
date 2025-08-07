# Customer Onboarding REST API

A Spring Boot REST API for managing customer onboarding with role-based access control and appointment scheduling functionality.

## Overview

This project provides a backend service for managing customer onboarding processes. It includes secure APIs for creating and viewing customer information with role-based access control to ensure only authorized users can perform specific actions. The application also features a bonus appointment scheduling endpoint for enhanced functionality.

## Features

- **Add new customers** - Create customer records with business information and document metadata
- **View customer details** - Retrieve customer information by ID
- **Role-Based Access Control (RBAC)** - Admin and User roles with different permission levels
- **Appointment scheduling stub** - Basic appointment booking functionality
- **Input validation** - Comprehensive request validation with detailed error messages
- **Thread-safe storage** - Concurrent data handling using in-memory storage
- **Token-based authentication** - Simple Bearer token security implementation

## Tech Stack

- **Java 17+** - Latest LTS version with modern language features
- **Spring Boot 3.x** - Latest Spring Boot framework
- **Maven** - Dependency management and build tool
- **Spring Security** - Authentication and authorization framework
- **Jakarta Validation** - Bean validation for request data
- **Spring Web** - RESTful web services framework

## Prerequisites

Before running this application, ensure you have:

- **JDK 17 or higher** installed
- **Maven 3.6+** installed
- **Internet connection** for downloading dependencies

## Setup and Installation

### Method 1: Using Spring Initializer (Recommended)

1. **Generate Project Structure**:
   - Visit [Spring Initializer](https://start.spring.io/)
   - Configure:
     - Project: Maven
     - Language: Java
     - Spring Boot: 3.x.x
     - Java: 21 (in this project java 21 is used)
   - Add Dependencies:
     - Spring Web
     - Spring Security
     - Validation
   - Generate and download the project

2. **Clone the Repository**:
   ```bash
   git clone <your-repository-url>
   cd customer-onboarding
   ```

3. **Build the Project**:
   ```bash
   mvn clean compile
   ```

4. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

## API Endpoints and Usage

### Base URLs
- **Local Development**: `http://localhost:8080`
- **Production (Deployed)**: `https://your-service-name.onrender.com`

*Replace `your-service-name` with your actual Render service name in this case `customer-onboarding-api` *
- **Deployed on **: `https://customer-onboarding-api.onrender.com`

---

### 1. Create Customer

**POST** `/customers`

Creates a new customer record with business information and document metadata.

**Access:** Admin role only (`ADMIN123` token)

**Postman Configuration:**
- **Method**: POST
- **URL**: `https://customer-onboarding-api.onrender.com/customers`
- **Headers**:
  - `Content-Type: application/json`
  - `Authorization: Bearer ADMIN123`
- **Body** (raw JSON):
```json
{
  "businessName": "Tech Solutions Inc",
  "phoneNumber": "+1-555-0123",
  "website": "https://techsolutions.com",
  "documents": ["business_license.pdf", "tax_certificate.pdf", "insurance_policy.pdf"]
}
```

**Successful Response (201 Created):**
```json
{
  "id": 1,
  "businessName": "Tech Solutions Inc",
  "phoneNumber": "+1-555-0123",
  "website": "https://techsolutions.com",
  "documents": ["business_license.pdf", "tax_certificate.pdf", "insurance_policy.pdf"]
}
```

**Error Response - Insufficient Permissions (403 Forbidden):**
When using USER123 token or invalid token.

**Error Response - Validation Errors (400 Bad Request):**
```json
{
  "businessName": "BusinessName is Required Field",
  "phoneNumber": "PhoneNumber is Required Field"
}
```

---

### 2. Get Customer Details

**GET** `/customers/{id}`

Retrieves detailed information about a specific customer by their ID.

**Access:** Both Admin and User roles (`ADMIN123` or `USER123` tokens)

**Postman Configuration:**
- **Method**: GET
- **URL**: `https://customer-onboarding-api.onrender.com/customers/1`
- **Headers**:
  - `Authorization: Bearer USER123` (or `ADMIN123`)

**Successful Response (200 OK):**
```json
{
  "id": 1,
  "businessName": "Tech Solutions Inc",
  "phoneNumber": "+1-555-0123",
  "website": "https://techsolutions.com",
  "documents": ["business_license.pdf", "tax_certificate.pdf", "insurance_policy.pdf"]
}
```

**Error Response - Customer Not Found (404 Not Found):**
Empty response body with 404 status code when customer ID doesn't exist.

---

### 3. Schedule Appointment (Bonus Feature)

**POST** `/appointments`

Creates an appointment booking for a customer. This is a stub implementation that echoes back the input data.

**Access:** Both Admin and User roles (`ADMIN123` or `USER123` tokens)

**Postman Configuration:**
- **Method**: POST
- **URL**: `https://customer-onboarding-api.onrender.com/appointments`
- **Headers**:
  - `Content-Type: application/json`
  - `Authorization: Bearer USER123` (or `ADMIN123`)
- **Body** (raw JSON):
```json
{
  "customerId": 1,
  "datetime": "2024-12-15T10:30:00"
}
```

**Successful Response (200 OK):**
```json
{
  "customerId": 1,
  "datetime": "2024-12-15T10:30:00"
}
```

**Error Response - Validation Errors (400 Bad Request):**
```json
{
  "customerId": "Customer id is required field.",
  "datetime": "dateTime is required field."
}
```

---

### Testing Access Control with Postman

**Test Scenario: User trying to create customer (should fail)**

- **Method**: POST
- **URL**: `https://customer-onboarding-api.onrender.com/customers`
- **Headers**:
  - `Content-Type: application/json`
  - `Authorization: Bearer USER123`
- **Body**:
```json
{
  "businessName": "Unauthorized Test Company",
  "phoneNumber": "+1-555-9999"
}
```

**Expected Result**: 403 Forbidden error demonstrating proper access control.

## Access Control

The application implements role-based access control using static Bearer tokens:

### Roles and Permissions

| Role | Token | Permissions |
|------|-------|-------------|
| **Admin** | `ADMIN123` | - Create customers<br>- View customer details<br>- Schedule appointments |
| **User** | `USER123` | - View customer details<br>- Schedule appointments |

### Token Usage
Include the appropriate token in the Authorization header for all API requests:
```
Authorization: Bearer ADMIN123
```
or
```
Authorization: Bearer USER123
```

### Security Implementation
- Custom `TokenAuthenticationFilter` validates Bearer tokens
- Spring Security configuration enforces role-based endpoint access
- Stateless session management for API-only architecture
- CSRF protection disabled for REST API usage

## Deployment

This application is configured for deployment on cloud platforms and includes:

- **Environment Configuration**: Supports `PORT` environment variable for cloud deployment
- **Production Profile**: Optimized settings for production environments
- **Memory Optimization**: Configured for efficient memory usage on free-tier hosting

**Live Deployment**: The application is deployed and accessible at:
`https://customer-onboarding-api.onrender.com`

## Project Structure

```
src/main/java/com/Assignment/customer_onboarding/
├── config/
│   └── SecurityConfig.java              # Spring Security configuration
├── controller/
│   ├── CustomerController.java          # Customer REST endpoints
│   └── AppointmentController.java       # Appointment REST endpoints  
├── data/
│   └── InMemoryStorage.java             # Thread-safe data storage layer
├── exception/
│   └── GlobalExceptionHandler.java      # Centralized error handling
├── model/
│   ├── Customer.java                    # Customer entity model
│   └── AppointmentRequest.java          # Appointment request DTO
├── security/
│   └── TokenAuthenticationFilter.java   # Custom authentication filter
└── service/
    └── CustomerService.java             # Business logic layer
```
## Dockerfile
-The Dockerfile in the root of the repository contains a complete, multi-stage recipe for building and running the application inside a container.

-It starts with a lightweight Java 21 base image (openjdk:21-jdk-slim) to match the project's requirements.

-It copies the project files and uses the Maven Wrapper (mvnw) to build the application into an executable .jar file. Tests are skipped to ensure a fast and reliable build process.

-The final container is configured with an ENTRYPOINT that runs the application, listening on the port provided by the hosting environment (via the ${PORT} variable).

## Render Blueprint (render.yaml)
-The render.yaml file provides the "Infrastructure as Code" configuration for deploying the service on the Render platform.

-It specifies the creation of a web service named customer-onboarding-api.

-It tells Render to use the docker environment, which means it will look for and build the Dockerfile in the repository.

-It sets the service to run on Render's free hosting plan.

## Assumptions Made

### Data Storage
- **In-memory persistence**: Data is stored using `ConcurrentHashMap` and is lost when the application restarts
- **Thread-safe operations**: Uses `AtomicLong` for ID generation and concurrent data structures
- **No database required**: Simplified storage for demonstration and rapid deployment

### Security Model
- **Static token authentication**: Uses predefined tokens (`ADMIN123`, `USER123`) instead of dynamic JWT or OAuth2
- **Role-based authorization**: Simple two-tier permission system (Admin/User)
- **No token expiration**: Tokens remain valid indefinitely for demonstration purposes
- **No user registration**: Predefined roles and tokens only

### Document Management
- **Metadata only**: Documents are stored as filename strings, no actual file upload/storage
- **No file validation**: Document names are stored as-is without format or existence validation
- **Simulated storage**: Real-world implementation would integrate with file storage services

### Business Logic
- **Appointment scheduling**: Stub implementation that echoes input without business logic
- **No validation of relationships**: Appointment `customerId` is not validated against existing customers
- **Simplified workflow**: No complex business rules or state management

### API Design
- **RESTful conventions**: Follows standard HTTP methods and status codes
- **JSON-only communication**: No XML or other format support
- **Basic error handling**: Standard validation and HTTP error responses

### Creating a Postman Collection

1. **Create New Collection**: Name it "Customer Onboarding API"

2. **Add Environment Variables**:
   - `base_url`: `https://your-service-name.onrender.com`
   - `admin_token`: `ADMIN123`
   - `user_token`: `USER123`

### Test Sequence Recommendation

1. **Create Customer** (Admin token) → Should return 201
2. **Get Customer** (User token) → Should return 200
3. **Create Customer** (User token) → Should return 403
4. **Schedule Appointment** (User token) → Should return 200
5. **Invalid Data Tests** → Should return 400

## Performance and Limitations

### Current Limitations
- **Memory-based storage**: Limited by available RAM
- **Single instance**: No horizontal scaling support
- **No data backup**: Data loss on application restart
- **Basic security**: Production would require enhanced authentication

### Recommended Production Enhancements
- **Database integration** (PostgreSQL/MySQL)
- **JWT token authentication** with expiration
- **File upload functionality** for documents
- **Comprehensive logging** and monitoring
- **API documentation** with Swagger/OpenAPI
- **Unit and integration tests**
- **Docker containerization**

---

## Support and Documentation

For additional information about Spring Boot and the technologies used:
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Reference](https://spring.io/projects/spring-security)
- [Jakarta Bean Validation](https://beanvalidation.org/)

---
