# Customer Onboarding REST API

A Spring Boot REST API for managing customer onboarding with role-based access control and appointment scheduling functionality.

##  Features

- **Customer Management**: Create and view customer information
- **Role-Based Access Control**: Admin and User roles with different permissions
- **Token-Based Authentication**: Simple Bearer token authentication
- **Appointment Scheduling**: Basic appointment booking functionality
- **Input Validation**: Comprehensive validation with error handling
- **In-Memory Storage**: Thread-safe data persistence using ConcurrentHashMap

## 🛠 Tech Stack

- **Java 17+(java 21 used)**
- **Spring Boot 3.x**
- **Spring Security**
- **Maven**
- **Jakarta Validation**

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+

##  Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd customer-onboarding
```

### 2. Build the Project
```bash
mvn clean compile
```

### 3. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

##  Authentication

The API uses Bearer token authentication with two predefined roles:

| Token | Role | Permissions |
|-------|------|------------|
| `ADMIN123` | Admin | Full access to all endpoints |
| `USER123` | User | Read-only access to customer details and appointment creation |

### Usage
Include the token in the Authorization header:
```
Authorization: Bearer ADMIN123
```

## API Endpoints

### 1. Create Customer
**POST** `/customers`

Creates a new customer record.

**Access:** Admin only

**Request Body:**
```json
{
  "businessName": "Tech Solutions Inc",
  "phoneNumber": "+1-555-0123",
  "website": "https://techsolutions.com",
  "documents": ["business_license.pdf", "tax_certificate.pdf"]
}
```

**Response:**
```json
{
  "id": 1,
  "businessName": "Tech Solutions Inc",
  "phoneNumber": "+1-555-0123",
  "website": "https://techsolutions.com",
  "documents": ["business_license.pdf", "tax_certificate.pdf"]
}
```

**Status Codes:**
- `201 Created` - Customer successfully created
- `400 Bad Request` - Validation errors
- `403 Forbidden` - Insufficient permissions

### 2. Get Customer Details
**GET** `/customers/{id}`

Retrieves customer information by ID.

**Access:** Admin and User roles

**Response:**
```json
{
  "id": 1,
  "businessName": "Tech Solutions Inc",
  "phoneNumber": "+1-555-0123",
  "website": "https://techsolutions.com",
  "documents": ["business_license.pdf", "tax_certificate.pdf"]
}
```

**Status Codes:**
- `200 OK` - Customer found
- `404 Not Found` - Customer not found
- `403 Forbidden` - Insufficient permissions

### 3. Schedule Appointment (Bonus)
**POST** `/appointments`

Creates an appointment booking.

**Access:** Admin and User roles

**Request Body:**
```json
{
  "customerId": 1,
  "datetime": "2024-12-15T10:30:00"
}
```

**Response:**
```json
{
  "customerId": 1,
  "datetime": "2024-12-15T10:30:00"
}
```

**Status Codes:**
- `200 OK` - Appointment scheduled
- `400 Bad Request` - Validation errors
- `403 Forbidden` - Insufficient permissions

##  Testing with cURL

### Create a Customer (Admin)
```bash
curl -X POST http://localhost:8080/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer ADMIN123" \
  -d '{
    "businessName": "Tech Solutions Inc",
    "phoneNumber": "+1-555-0123",
    "website": "https://techsolutions.com",
    "documents": ["business_license.pdf", "tax_certificate.pdf"]
  }'
```

### Get Customer Details (User)
```bash
curl -X GET http://localhost:8080/customers/1 \
  -H "Authorization: Bearer USER123"
```

### Schedule Appointment (User)
```bash
curl -X POST http://localhost:8080/appointments \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer USER123" \
  -d '{
    "customerId": 1,
    "datetime": "2024-12-15T10:30:00"
  }'
```

### Test Access Control (Should fail)
```bash
# User trying to create customer (should return 403)
curl -X POST http://localhost:8080/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer USER123" \
  -d '{
    "businessName": "Test Company",
    "phoneNumber": "+1-555-9999"
  }'
```

## Testing with Postman

### Import Collection
Create a new Postman collection with the following requests:

1. **Create Customer**
   - Method: POST
   - URL: `http://localhost:8080/customers`
   - Headers: 
     - `Content-Type: application/json`
     - `Authorization: Bearer ADMIN123`
   - Body: Raw JSON (see example above)

2. **Get Customer**
   - Method: GET
   - URL: `http://localhost:8080/customers/1`
   - Headers: `Authorization: Bearer USER123`

3. **Schedule Appointment**
   - Method: POST
   - URL: `http://localhost:8080/appointments`
   - Headers: 
     - `Content-Type: application/json`
     - `Authorization: Bearer USER123`
   - Body: Raw JSON (see example above)

## Error Handling

The API provides comprehensive error responses:

### Validation Errors (400 Bad Request)
```json
{
  "businessName": "BusinessName is Required Field",
  "phoneNumber": "PhoneNumber is Required Field"
}
```

### Authorization Errors (403 Forbidden)
```json
{
  "timestamp": "2024-08-07T10:30:00.000+00:00",
  "status": 403,
  "error": "Forbidden",
  "path": "/customers"
}
```

### Not Found Errors (404 Not Found)
Empty response body with 404 status code.

##  Data Storage

The application uses in-memory storage with the following characteristics:

- **Thread-Safe**: Uses `ConcurrentHashMap` and `AtomicLong`
- **Auto-Incrementing IDs**: Automatically assigns unique IDs to customers
- **Temporary**: Data is lost when the application restarts

## Project Structure


##  Assumptions Made

1. **Document Storage**: Documents are stored as metadata (filenames) only, no actual file upload/storage
2. **Authentication**: Simple token-based authentication is sufficient for this demo
3. **Data Persistence**: In-memory storage is acceptable, data doesn't need to survive restarts
4. **ID Generation**: Auto-incrementing Long IDs starting from 1
5. **Appointment Logic**: No business logic required, just echo the input back
6. **Validation**: Basic field validation is sufficient
7. **Error Responses**: Standard Spring Security error responses are acceptable

## Future Enhancements

- Database integration (PostgreSQL/MySQL)
- JWT token authentication
- File upload functionality for documents
- Comprehensive appointment management
- API documentation with Swagger/OpenAPI
- Unit and integration tests
- Docker containerization
- Logging and monitoring

## API Testing Summary

| Endpoint | Method | Admin Token | User Token | Expected Result |
|----------|--------|-------------|------------|----------------|
| `/customers` | POST | ✅ 201 | ❌ 403 | Role-based access working |
| `/customers/{id}` | GET | ✅ 200 | ✅ 200 | Both roles can read |
| `/appointments` | POST | ✅ 200 | ✅ 200 | Both roles can schedule |

## Security Notes

- Tokens are hardcoded for demo purposes only
- In production, use proper JWT tokens with expiration
- Consider implementing HTTPS for secure token transmission
- Add rate limiting for production deployment
