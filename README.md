# Library-Management-System-Backend-API

## Description:

A Library Management System that allows users to store information about an Author and all his books.

## Technologies used:

- Spring Boot 3
- Java 17
- PostgreSQL
- Hibernate

## Setup Instructions:

1. **Run Maven Clean Install**
    ```bash
    mvn clean install
    ```

2. **Ensure Database Connectivity**

   Make sure you have PostgreSQL installed locally or use a Docker container for the database.

3. **Modify Datasource Properties**

   Update the datasource properties in the `application.properties` file if you have different connection credentials:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/library
    spring.datasource.username=postgres
    spring.datasource.password=postgres
    ```

4. **Run as a Spring Boot Application**

   You can run the application using your IDE or from the command line:
    ```bash
    mvn spring-boot:run
    ```

5. **Info**

   In order to run tests, Docker is required.


6. **Swagger**

   
   http://localhost:8080/api/v1/swagger-ui-library-management-system

