# Student Management REST API

**Tech Stack:** Java | Spring Boot | Maven Wrapper | JPA | MySQL

## Technologies Used
- Java
- Spring Boot
- Spring Data JPA
- JUnit & Mockito
- H2 Database
- Maven
- SpringDoc OpenAPI
- Lombok

## Description
This REST API performs full CRUD operations for managing students, their courses, and associated grades.

## Documentation
API documentation is available via Swagger UI at:  
**[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**  
Generated using **SpringDoc OpenAPI**.

## Setup & Usage
No installations required — Maven Wrapper handles project and dependency management.  
You **do not need to install Maven** separately.

### Postman Collection
Located at:  
`src/main/resources/postman-workspace/student-management.postman_collection.json`  
Includes:
- Valid and invalid API requests
- Useful for testing and referencing the documentation

### H2 Database
Configuration is available in `application.properties`.  
Access the H2 Console at:  
**[http://localhost:8080/h2-console](http://localhost:8080/h2-console)** (after starting the app)

## Testing
### Unit Tests
- Written using **JUnit** and **Mockito**
- Focused on **Service layer**
- Run using Maven Surefire:
  ```bash
  ./mvnw test

### Integration Tests
- Written using **JUnit** and **MockMvc**
- Focused on **Controller layer**
- Run using Maven FailSafe plugin:
  ```bash
  ./mvnw verify

### Full Test Run
- To run both unit and integration tests:
  ```bash
  ./mvnw clean test verify

## Acknowledgements
Special thanks to *Rayan Slim* and *Jose Portilla* for their Udemy course
**Java Web Development**, which provided foundational knowledge for building this API.

#### Author : *Mbonisi Mpala*