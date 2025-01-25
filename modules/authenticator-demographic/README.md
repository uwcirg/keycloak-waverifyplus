# Demographic Authentication Module

## Overview

The Demographic Authentication Module extends Keycloak's functionality by integrating demographic validation into its
authentication workflows. This module includes custom authenticators, form actions, and services to validate demographic
data submitted by users during registration or login processes.

## Key Features

- Custom authenticator for demographic validation during authentication workflows.
- Custom form action for collecting and processing demographic information during user registration.
- Integration with external services for validation logic.
- Support for user-defined configurations (e.g., timeout settings).

---

## Glossary

### **1. Authenticator**

An authenticator implements the logic for validating demographic information during authentication workflows. It
processes the provided user data and integrates external verification services.

Example: `DemographicAuthenticatorImpl` contains the core logic for demographic validation.

---

### **2. Authenticator Factory**

A factory class is responsible for:

- Instantiating authenticators.
- Providing configurations or dependencies.
- Managing the lifecycle of authenticators.

Example: `DemographicAuthenticatorFactory` creates instances of `DemographicAuthenticatorImpl` and injects the
`DemographicVerificationService` dependency.

---

### **3. Form Action**

A form action defines additional steps or validations during user interactions with forms in Keycloak. This includes
processing data submitted during registration or login workflows.

Example: `DemographicRegistrationFormAction` processes demographic data during registration.

---

### **4. Verification Service**

The verification service is a standalone class that connects to an external API or database to validate demographic
information. It encapsulates the logic for communication and response handling.

Example: `DemographicVerificationServiceImpl` provides a concrete implementation for interacting with the demographic
verification API.

---

## Module Structure

### **1. Authenticator**

- `DemographicAuthenticatorImpl`: Handles demographic validation workflows during authentication.

### **2. Authenticator Factory**

- `DemographicAuthenticatorFactory`: Manages authenticator instances, dependencies, and configurations.

### **3. Form Action**

- `DemographicRegistrationFormAction`: A form action that collects and processes demographic data during user
  registration. It validates the data and stores it in the user's attributes upon successful submission.

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
   -Dkeycloak.profile.feature.registration-form-action=enabled
   ```
3. Restart the Keycloak server to apply changes.

### **Configuring the Module**

- Use the Keycloak admin console to add the `DemographicRegistrationFormAction` or `DemographicAuthenticatorFactory` to
  the desired flow.
- Adjust the configuration properties (e.g., API endpoint, timeouts) as required.

---

## Example Configuration

To integrate demographic validation into your workflows:

1. Add `DemographicAuthenticatorFactory` or `DemographicRegistrationFormAction` to your Keycloak flow in the admin
   console.
2. Ensure that demographic fields (e.g., age, gender, location) are included in the form template for registration
   flows.
3. Deploy the updated flow and test the demographic validation functionality.

---

## Form Action: `DemographicRegistrationFormAction`

### Overview

`DemographicRegistrationFormAction` collects demographic information during user registration and validates the
submitted data.

### Key Features

- Dynamically adds demographic fields (e.g., age, gender, location) to the registration form.
- Validates submitted demographic data to ensure completeness.
- Stores validated demographic data in user attributes.

### Deployment

Include the compiled JAR in the `providers` directory and enable the form action in your Keycloak configuration.
Customize the form template to display demographic fields.
