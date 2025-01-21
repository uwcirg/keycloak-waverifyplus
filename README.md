# Keycloak WaverifyPlus

Keycloak WaverifyPlus is a multi-project Gradle-based application designed to integrate with Keycloak for extended
functionality. It comprises two subprojects:

1. **mock-vp**
2. **spi-demographic**

---

## Subprojects

### 1. mock-vp

**mock-vp** is a Spring Boot application that simulates a validation provider server. It provides REST endpoints for
verifying demographic information. This is used to mock real-world integrations in a controlled environment for testing
and development.

For more details, see the [mock-vp README](mock-vp/README.md).

---

### 2. spi-demographic

**spi-demographic** is a Keycloak Service Provider Interface (SPI) implementation that integrates demographic validation
into the Keycloak ecosystem. It communicates with the **mock-vp** server to perform demographic validation and provides
a mechanism to extend Keycloak functionality with custom logic.

For more details, see the [spi-demographic README](spi-demographic/README.md).

---

## How to Build and Run

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd keycloak-waverifyplus
   ```
2. Build the entire project:
   ```bash
   ./gradlew build
   ```
3. To run mock-vp, execute:
   ```bash
   ./gradlew :mock-vp:bootRun 
   ```

## Running Tests and Integration Tests

### mock-vp

- To run unit tests:
  ```bash
  ./gradlew test
  ```

### spi-demographic

- To run unit tests:
  ```bash
  ./gradlew test
  ```
- To run integration tests:
  ```bash
  ./gradlew integrationTest
  ```
