# Demographic SPI

## Overview

This module provides integration between Keycloak and the demographic verification service. It is implemented as a
Keycloak Service Provider Interface (SPI) and includes custom providers, factories, and services for validating
demographic data during authentication workflows.

The SPI framework allows Keycloak to be extended with custom logic, making it possible to integrate third-party services
such as a verification service for demographic validation.

## Key Features

- Provides an SPI for demographic validation.
- Implements a custom provider and factory to interact with the verification service.
- Supports the validation of demographic data submitted by users during authentication or registration workflows.

---

## Glossary

### **1. Service Provider Interface (SPI)**

An SPI in Keycloak defines a contract that allows developers to implement custom extensions or integrate external
services into Keycloak. It specifies:

- The `getProviderClass` method for defining the primary interface of the service.
- The `getProviderFactoryClass` method for creating factory classes that instantiate the service.

Example in this module: `DemographicSpi` defines the SPI for demographic validation.

---

### **2. Provider**

A provider is a concrete implementation of an SPI. It provides the actual functionality specified by the SPI contract.

Example in this module: `DemographicProviderImpl` implements the demographic validation logic.

---

### **3. Provider Factory**

A provider factory is responsible for creating instances of providers. It handles:

- Initializing the provider instance.
- Providing configurations or dependencies required by the provider.

Example in this module: `DemographicProviderFactory` creates instances of `DemographicProviderImpl` and injects the
`DemographicVerificationService` dependency.

---

### **4. Verification Service**

The verification service is a custom class (`DemographicVerificationService`) used to connect with an external API or
database for demographic validation. It encapsulates the logic for sending demographic data and receiving verification
results.

Example in this module: The factory initializes the `DemographicVerificationService` and passes it to the provider
instances for use in validation.

---

## Usage Instructions

1. **Build the Module**:
   Run the following command to build the module:
   ```bash
   ./gradlew build
   ```

2. **Deploy the Module**:
   Copy the compiled JAR file to the `providers` directory of your Keycloak server.

3. **Configure the Module**:
   Add the following settings to your Keycloak configuration to enable the demographic SPI:
   ```bash
   -Dspi.demographic-validation.provider=demographic-validation
   ```

4. **Start Keycloak**:
   Restart your Keycloak server to load the new module.

---
