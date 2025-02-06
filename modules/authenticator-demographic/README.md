# Demographic & PIN Authentication Module

## Overview

This module extends Keycloak's authentication workflows by integrating demographic validation and PIN-based
authentication. It includes custom authenticators, form actions, credential providers, and services to validate
demographic data and secure user access using a PIN.

## Key Features

- **Demographic Authentication:** Validates user demographic data during registration or login.
- **PIN Authentication:** Allows users to authenticate using a secure PIN.
- **Custom Authenticators:** Implements authentication logic for both demographic verification and PIN entry.
- **Credential Management:** Supports PIN storage and validation through Keycloak's credential framework.
- **Integration with External Services:** Enables validation of demographic data via external APIs.
- **Configurable Authentication Flows:** Admins can integrate these authenticators into Keycloak's authentication
  workflows.

---

## Glossary

### **1. Demographic Verification Service**

Handles communication with external verification systems to validate user demographic information.

Example:

- `DemographicVerificationServiceImpl`: Implements external demographic validation.

---

### **2. Authenticator**

Handles authentication logic for validating demographic information and PINs during authentication workflows.

Examples:

- `DemographicAuthenticatorImpl`: Validates demographic data and integrates with `DemographicVerificationService`.
- `PinAuthenticator`: Validates user-entered PINs.

---

### **3. Authenticator Factory**

Responsible for creating instances of authenticators and providing necessary dependencies.

Examples:

- `DemographicAuthenticatorFactory`: Instantiates `DemographicAuthenticatorImpl`.
- `PinAuthenticatorFactory`: Instantiates `PinAuthenticator`.

---

### **4. Form Action**

Defines additional processing steps for user interactions with forms in Keycloak.

Example:

- `DemographicRegistrationFormAction`: Collects and processes demographic data during user registration.

---

### **5. Credential Provider**

Manages PIN storage, retrieval, and validation within Keycloak's credential framework.

Example:

- `PinCredentialProvider`: Handles PIN storage and validation.
- `PinCredentialProviderFactory`: Factory for creating `PinCredentialProvider` instances.

---

### **6. Required Actions**

Required actions are prompts that users must complete to continue authentication.

Example:

- `PinRequiredAction`: Forces users to set up a PIN if they don’t have one.

---

### **7. Token Authentication**

Allows users to authenticate using a token link received via email.

Examples:

- `TokenAuthenticator`: Validates user tokens and identifies users.
- `TokenAuthenticatorFactory`: Factory for creating token authenticators.
- `UserTokenGenerator`: Generates and assigns authentication tokens to users.

---

## Module Structure

### **1. Demographic Authentication**

- `DemographicAuthenticatorImpl`: Handles demographic validation workflows during authentication.
- `DemographicAuthenticatorFactory`: Manages demographic authenticator instances.
- `DemographicVerificationService`: Defines the contract for verifying demographic data.
- `DemographicVerificationServiceImpl`: Implements verification logic.
- `DemographicRegistrationFormAction`: Processes demographic data during user registration.

### **2. PIN Authentication**

- `PinAuthenticator`: Validates user-entered PINs.
- `PinAuthenticatorFactory`: Creates instances of `PinAuthenticator`.
- `PinCredentialModel`: Represents stored PIN credentials.
- `PinCredentialProvider`: Handles PIN storage and validation.
- `PinCredentialProviderFactory`: Factory for `PinCredentialProvider`.
- `PinRequiredAction`: Forces users to set up a PIN if missing.

### **3. Token-Based Authentication**

- `TokenAuthenticator`: Authenticates users based on a token received via email.
- `TokenAuthenticatorFactory`: Factory for creating `TokenAuthenticator` instances.
- `UserTokenGenerator`: Generates unique, non-expiring login tokens.

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

- Use the Keycloak admin console to add the appropriate authenticators or form actions to authentication flows.
- Configure the PIN and token authentication settings as needed.
- Set the baseUrl for DemographicVerificationService via Keycloak's authentication flow configuration.

---

## Example Configuration

To integrate demographic and PIN authentication into Keycloak:

1. Add `DemographicAuthenticatorFactory`, `PinAuthenticatorFactory`, or `TokenAuthenticatorFactory` to your
   authentication flow.
2. Configure the `baseUrl` property in `DemographicAuthenticatorFactory` to point to the external verification service.
3. Enable `PinRequiredAction` to ensure users set up a PIN.
4. Customize form templates for demographic and PIN input.
5. Deploy and test the authentication process.
