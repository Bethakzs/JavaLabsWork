# Environment Setup

## Required Ports
Ensure that the following ports are available on your local machine:
- **8080**
- **8081**
- **8082**
- **8083**
- **9092** (Kafka)
- **5432** (PostgreSQL)

## Environment Variables
Before running the application, you need to set up the following environment variables in your IntelliJ IDEA run configuration:

### For Sending Emails
- **`${SPRING_MAIL_USERNAME}`**: Your email address for sending emails.
- **`${SPRING_MAIL_PASSWORD}`**: Your email account password.

### For JWT Secret
- **`${JWT_SECRET}`**: Your secret key for JWT token generation.

## Database Configuration
Make sure you have a PostgreSQL database running on port **5432** with the following configuration (Also you can change everything in the `application.properties` file):
- **Database Name**: `booking`
- **Database Name**: `user`

## Kafka Setup
Ensure that Kafka is running on your local machine on **port 9092**.
