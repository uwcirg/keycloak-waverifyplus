# Demographic Authentication Module

## Overview

The Demographic Authentication Module extends Keycloak's functionality by integrating demographic validation into its
authentication workflows. This module is implemented as a Keycloak Service Provider Interface (SPI) and provides custom
authenticators, factories, and services for validating demographic data submitted by users.

The SPI framework enables Keycloak to incorporate third-party services like demographic validation seamlessly, ensuring
extensibility and modularity.

## Key Features

- Custom SPI for demographic validation.
- Implementations of providers, factories, and services for demographic data verification.
- Integration with external services for validation logic.
- Support for user-defined configurations (e.g., timeout settings).

---

## Glossary

### **1. Service Provider Interface (SPI)**

An SPI defines a contract allowing developers to extend Keycloak with custom implementations or integrate external
services. Key attributes of an SPI include:

- The `getProviderClass` method specifies the main interface of the service.
- The `getProviderFactoryClass` method creates factory classes for service instantiation.

In this module, `DemographicAuthenticator` defines the SPI for demographic validation.

---

### **2. Authenticator**

An authenticator implements the logic for validating demographic information during authentication workflows. It
processes the provided user data and integrates external verification services.

Example: `DemographicAuthenticatorImpl` contains the core logic for demographic validation.

---

### **3. Authenticator Factory**

A factory class is responsible for:

- Instantiating authenticators.
- Providing configurations or dependencies.
- Managing the lifecycle of authenticators.

Example: `DemographicAuthenticatorFactory` creates instances of `DemographicAuthenticatorImpl` and injects the
`DemographicVerificationService` dependency.

---

### **4. Verification Service**

The verification service is a standalone class that connects to an external API or database to validate demographic
information. It encapsulates the logic for communication and response handling.

Example: `DemographicVerificationServiceImpl` provides a concrete implementation for interacting with the demographic
verification API.

---

## Module Structure

### **1. Interfaces and Contracts**

- `DemographicAuthenticator`: Defines the contract for demographic validation logic.

### **2. Implementation Classes**

- `DemographicAuthenticatorImpl`: Implements the SPI, handling demographic validation workflows.

### **3. Factory**

- `DemographicAuthenticatorFactory`: Manages authenticator instances, dependencies, and configurations.

### **4. Verification Services**

- `DemographicVerificationService`: Abstract service for demographic verification.
- `DemographicVerificationServiceImpl`: Concrete implementation communicating with the validation provider server.

---

## Usage Instructions

### **Building the Module**

1. Build the module using Gradle:
   ```bash
   ./gradlew build
   ```

2. Ensure all dependencies are properly resolved before deployment.

### **Deploying the Module**

1. Copy the compiled JAR file to the `providers` directory of your Keycloak server.

2. Add the following settings to your Keycloak configuration:
   ```bash
   -Dspi.demographic-validation.provider=demographic-validation-authenticator
   ```

3. Restart the Keycloak server to apply changes.

### **Configuring the Module**

- Use the Keycloak admin console to enable the demographic authenticator in the desired authentication flow.
- Adjust the configuration properties (e.g., `verification.timeout`) as required.

---

## Example Configuration

To integrate demographic validation into your authentication flow:

1. Add `DemographicAuthenticatorFactory` to your Keycloak authentication flow in the admin console.
2. Provide the required configuration (e.g., API endpoint, timeouts).
3. Deploy the updated flow and test the demographic validation functionality.

---
