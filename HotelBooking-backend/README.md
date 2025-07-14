# 🏨 Hotel Grand Vista - Booking System

A comprehensive hotel booking management system built with Spring Boot, featuring user authentication, room management, booking system, and payment integration with Stripe.

🌟 Key Features
Secure Authentication: JWT-based login, role-based access (Admin, Customer), and password protection.
Room Management: Add, update, delete, and search rooms by type, price, and availability. Supports image uploads.
Booking System: Create/manage bookings with status tracking, date validation, and booking history.
Payments: Integrated Stripe gateway for secure payments, transaction tracking, and email notifications.
Email Alerts: Automated booking and payment notifications via SMTP (Gmail).
Advanced: File uploads (up to 2GB), data validation, robust error handling, CORS support, and Dockerized deployment.

## 🛠 Technology Stack

### Backend
- **Java 17**
- **Spring Boot 3.5.3**
- **Spring Security** with JWT
- **Spring Data JPA**
- **MySQL 8.0** Database
- **Maven** for dependency management
- **Lombok** for boilerplate code reduction

### Payment & External Services
- **Stripe API** for payment processing
- **SMTP** for email notifications
- **JWT** for authentication

## 📋 Prerequisites

Before running this application, make sure you have:

- **Java 17** or higher
- **Maven 3.6+**
- **MySQL 8.0** or higher
- **Stripe Account** (for payment processing)
- **Gmail Account** (for email notifications)

## 🚀 Quick Start

### Option 1: Local Development

1. **Clone the repository**
   ```bash
   git clone https://github.com/Ronakpadmani/Hotel-Grandvista.git
   cd Hotel-Grandvista/HotelBooking
   ```

2. **Configure database**
   - Create MySQL database named `hotel`
   - Update `src/main/resources/application.properties` with your database credentials

3. **Set up environment variables**
   ```bash
   cp env.example .env
   # Edit .env file with your actual credentials
   ```

4. **Build and run**
   ```bash
   mvn clean package -DskipTests
   java -jar target/HotelBooking-0.0.1-SNAPSHOT.jar
   ```

## 🔧 Configuration

### Environment Variables

Create a `.env` file in the `HotelBooking` directory:

```env
# Database Configuration
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/hotel
SPRING_DATASOURCE_USERNAME=your_username
SPRING_DATASOURCE_PASSWORD=your_password

# JWT Configuration
SECRETE_JWT_STRING=your_jwt_secret_key_here

# Mail Configuration
SPRING_MAIL_HOST=smtp.gmail.com
SPRING_MAIL_PORT=587
SPRING_MAIL_USERNAME=your_email@gmail.com
SPRING_MAIL_PASSWORD=your_email_app_password

# Stripe Configuration
STRIPE_API_PUBLIC_KEY=your_stripe_public_key
STRIPE_API_SECRET_KEY=your_stripe_secret_key
```

### Database Setup

1. Create MySQL database:
   ```sql
   CREATE DATABASE hotel;
   ```

2. The application will automatically create tables using JPA/Hibernate with `spring.jpa.hibernate.ddl-auto=update`

## 📚 API Documentation

### Authentication Endpoints

| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| POST | `/api/auth/register` | Register new user | Public |
| POST | `/api/auth/login` | User login | Public |

### User Management

| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| GET | `/api/user/all` | Get all users | Admin |
| GET | `/api/user/account` | Get own account details | Customer |
| PUT | `/api/user/update` | Update own account | Customer |
| DELETE | `/api/user/delete` | Delete own account | Customer |
| GET | `/api/user/bookings` | Get booking history | Customer |

### Room Management

| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| GET | `/api/room/all` | Get all rooms | Public |
| GET | `/api/room/{id}` | Get room by ID | Public |
| POST | `/api/room/add` | Add new room | Admin |
| PUT | `/api/room/update` | Update room | Admin |
| DELETE | `/api/room/delete/{id}` | Delete room | Admin |
| GET | `/api/room/available` | Get available rooms | Public |
| GET | `/api/room/types` | Get room types | Public |
| GET | `/api/room/search` | Search rooms | Public |

### Booking Management

| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| GET | `/api/booking/all` | Get all bookings | Admin |
| POST | `/api/booking` | Create booking | Admin/Customer |
| GET | `/api/booking/{reference}` | Get booking by reference | Public |
| PUT | `/api/booking/update` | Update booking | Admin |

### Payment Processing

| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| POST | `/api/payment/pay` | Create payment intent | Public |
| PUT | `/api/payment/update` | Update payment status | Public |

