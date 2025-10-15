# Lab 2 Web Server -- Project Report

## Description of Changes
The following changes were made to the original codebase:
    - Custom Error Page: It´s been created a custom error page based on the HTTP status code. A error.html file was added to the project under src/main/resources/static directory. It replaces the default error page provided by the web framework.

    - New /time Endpoint: It´s been added a new endpoint `/time` that returns the current server time in JSON format. It´s been added a TimeDTO class to represent the time data structure, a TimeProvider interface, and a TimeService for the implementation. The TimeController handles the HTTP requests to this endpoint. It´s accessivle at https://127.0.0.1:8443/time

    - SSL and HTTP/2 Support: It´s been enabled SSL support for secure connections. A self-signed certificate was generated and configured in the application properties. A PKCS12 keystore file named keystore.p12 was created and placed in the src/main/resources directory. 

    - Testing: Unit tests were added for the TimeService and TimeController to ensure the new functionality works as expected. It´s also been verified using curl commands.

## Technical Decisions
Use of DTO and Interface for Time Service:
    - A TimeDTO class was created to encapsulate the time data structure, promoting clean code and separation of concerns.
SSL Configuration using YAML:
    - The application properties were configured in YAML format for better readability and organization.
HHTTP/2 Support:
    - Enabled HTTP/2 support to improve performance and efficiency of web communications.


## Learning Outcomes
From this lab, I learned how to:
- Implement custom error handling in a web server.
- Create new endpoints that return dynamic content.
- Configure SSL for secure web communications.
- Test HTTPS endpoints using curl.
- Understand the benefits of using DTOs and interfaces in service design.


## AI Disclosure
### AI Tools Used
- Claude for consultation and structuring documentation.

### AI-Assisted Work
- Nothing
- 10% 90%
- 

### Original Work
- 100% of the code.
- 