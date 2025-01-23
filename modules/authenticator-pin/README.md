# PIN Authentication Module

## Overview

The PIN Authentication Module enhances Keycloak authentication by introducing a second-factor authentication mechanism
using a Personal Identification Number (PIN). This module allows users to securely authenticate by entering their PIN,
which is validated against a credential store.

## Key Features

- **Custom Authenticator**: Provides the logic for handling PIN-based authentication.
- **Credential Provider**: Manages PIN credentials for users.
- **Factory Support**: Facilitates configurability and integration into Keycloak’s authentication flows.
- **Required Action**: Enables users to set up their PIN as part of the authentication process.

---

## Glossary

### **1. PIN Authenticator**

The `PinAuthenticator` class implements the authentication logic for validating a user's PIN during login. Key methods
include:

- `authenticate`: Initiates the authentication process by displaying a form for PIN input.
- `action`: Handles the user's input and validates the PIN.

### **2. PIN Authenticator Factory**

The `PinAuthenticatorFactory` class:

- Creates instances of the `PinAuthenticator`.
- Defines configuration properties such as `cookie.max.age`.
- Manages authenticator lifecycle and integration.

### **3. Credential Provider**

The `PinCredentialProvider` class:

- Manages PIN credentials, including creation, validation, and deletion.
- Provides metadata about the credential type.

### **4. Required Action**

The `PinRequiredAction` class:

- Ensures that users set up a PIN if none is configured.
- Provides a challenge form for PIN setup and processes user input to create the credential.

---

## Module Structure

### **1. Authenticator**

- **Class**: `PinAuthenticator`
- **Description**: Implements the authentication logic for validating PINs.

### **2. Authenticator Factory**

- **Class**: `PinAuthenticatorFactory`
- **Description**: Manages the creation and configuration of the authenticator.

### **3. Credential Management**

- **Class**: `PinCredentialProvider`
- **Description**: Handles PIN credential operations such as creation and validation.

- **Class**: `PinCredentialProviderFactory`
- **Description**: Provides instances of the `PinCredentialProvider`.

### **4. Required Action**

- **Class**: `PinRequiredAction`
- **Description**: Facilitates PIN setup for users.

- **Class**: `PinRequiredActionFactory`
- **Description**: Manages the lifecycle of the `PinRequiredAction`.

---

## Configuration

### **Authenticator Configuration**

To integrate the PIN authenticator into your Keycloak authentication flow:

1. Add `PinAuthenticatorFactory` to the desired flow in the Keycloak admin console.
2. Configure the `cookie.max.age` property to define the lifespan of the session cookie.

### **Required Action Configuration**

1. Add `PinRequiredActionFactory` as a required action in the admin console.
2. Configure users to set up their PIN as part of their authentication requirements.

---

## Deployment Instructions

### **Building the Module**

1. Build the module using Gradle:
   ```bash
   ./gradlew build
   ```

2. Verify that the build completes successfully and produces the JAR file.

### **Deploying the Module**

1. Copy the JAR file to the `providers` directory of your Keycloak server.
2. Update the Keycloak configuration:
   ```bash
   -Dspi.pin-authenticator.provider=pin-authenticator
   ```
3. Restart the Keycloak server to apply changes.

---

## Example Configuration

1. Add the `PinAuthenticator` to your authentication flow.
2. Enable the `PinRequiredAction` for users who need to set up their PIN.
3. Test the authentication flow to ensure PIN validation works correctly.

---
