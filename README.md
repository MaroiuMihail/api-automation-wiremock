#  API Automation – WireMock + RestAssured
![API Tests](https://github.com/MaroiuMihail/api-automation-wiremock/actions/workflows/api-tests.yml/badge.svg)

API automation project built to demonstrate clean test architecture, contract validation, and realistic API behavior using WireMock.

---

## Tech Stack
- Java 17
- Maven
- JUnit 5
- RestAssured
- WireMock (embedded)

---

## What is covered

### Authentication
- Generate auth token (200 OK)
- Invalid credentials (401 Unauthorized)

### Orders API
- Create order
    - 201 Created
    - 400 Validation error
    - 401 Missing authorization
- Get order by ID
    - 200 OK
    - 404 Not found
- Update order status
    - 200 OK (valid transition)
    - 409 Conflict (invalid business transition)

---

## Why WireMock
- Full control over API behavior
- Stable and deterministic tests
- No external dependencies
- Business rules simulation (e.g. 409 conflicts)

---

## How to run tests

```bash
mvn clean test
```
---
## CI
```bash
GitHub Actions runs the test suite on every push and pull request
```