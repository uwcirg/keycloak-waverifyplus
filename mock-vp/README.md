# Waverify Mock Verification Proxy (MVP)

This project is a Spring Boot application designed to validate demographic information via a REST API.

## Prerequisites

Before running or testing the application, ensure you have the following installed:

- Java 17 or later
- Gradle (or use the Gradle wrapper provided in the project)

## Running the Tests

The tests for this project use JUnit 5 and Spring Boot's testing framework. You can run the tests without starting the
Spring Boot server.

To execute the tests, use the following command:

```bash
./gradlew :mock-vp:test
```

This will run all the tests in the `mock-vp` module and generate a report in the `build/reports/tests/test` directory.

## Starting the Spring Boot Server

To start the application, use the following command:

```bash
./gradlew :mock-vp:bootRun
```

The server will start and listen on the default port `8080`. The API can be accessed at `http://localhost:8080`.

### Example Endpoint

- **POST** `/api/validation`
    - Validates demographic information based on the provided JSON request.
    - Example request body:
      ```json
      {
          "firstName": "John",
          "lastName": "Doe",
          "dateOfBirth": "1990-01-01"
      }
      ```

## Building the Project

To build the `mock-vp` module, use the following command:

```bash
./gradlew :mock-vp:build
```

This will compile the project, run the tests, and package the application into a JAR file located in the `build/libs`
directory.

## Additional Notes

- You do not need to start the Spring Boot server to run the tests. The tests are designed to run in isolation using
  Spring Boot's test context.
- If you encounter any issues, check the logs in the `build` directory for detailed error reports.

For further details, refer to the Spring Boot documentation or contact the project maintainer.
