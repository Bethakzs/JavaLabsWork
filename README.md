# Lab 4

## Steps to Run the Application

1. Clone the repository.
2. Open the project in IntelliJ IDEA.
3. Docker should be running on your local machine.
4. Open cmd and navigate to the project directory & run the following command:
    ```shell
    docker-compose up
    ```

## Postman Integration

You can import all Postman collections from the JSON file located in the root directory of the project.

### Setup Instructions:
1. **Base URL:** If the base URL in your Postman environment is empty, set it to:  
   `http://localhost:8080/v1/api`

2. **Authorization Issues (401 Errors):**  
   If you encounter a 401 Unauthorized error, ensure that:
   - Navigate to the **Authorization** tab for the request in Postman.
   - Set the type to **Bearer Token**.
   - Paste the token you obtained from the authorization requests into the token field.


## Required Ports

Ensure that the following ports are available on your local machine:

- **8080**
- **8081**
- **8082**
- **8083**
- **9092** (Kafka)
- **5432** (PostgreSQL)

# Run the Application in IntelliJ IDEA

## Environment Variables

Before running the application, you need to set up the following environment variables in your IntelliJ IDEA run
configuration:

### For JWT Secret

- **`${JWT_SECRET}`**: Your secret key for JWT token generation.

## Database Configuration

Make sure you have a PostgreSQL database running on port **5432** with the following configuration (Also you can change
everything in the `application.properties` file):

- **Database Name**: `booking`
- **Database Name**: `user`

## Kafka Setup

Ensure that Kafka is running on your local machine on **port 9092**.

## Routes

If you want to change the routes in controller classes, you also need to change the routes in the api gateway security
config & application properties.
