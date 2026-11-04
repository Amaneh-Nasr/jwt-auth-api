# JWT Auth API

A REST API for user registration, login and role-based access with JSON Web Tokens (JWT). Built with **Spring Boot 3**, **Spring Security**, **Spring Data JPA** and **MySQL**.

## Features

- User registration (returns `409 Conflict` if the email address already exists)
- Login that returns a JWT
- Role-based access control with the roles `USER` and `ADMIN`
- Password reset with a reset token
- Password change for logged-in users
- API documentation with Swagger UI (springdoc-openapi)

## Technologies

Java 17 · Spring Boot 3.5.14 · Spring Security · Spring Data JPA · MySQL · JJWT 0.12.7 · springdoc-openapi 2.8.13 · Lombok · Maven

## API Endpoints

| Method | Endpoint                | Description                                            |
|--------|-------------------------|--------------------------------------------------------|
| POST   | `/auth/register`        | Register a new user                                    |
| POST   | `/auth/login`           | Log in and receive a JWT                               |
| POST   | `/auth/forgot-password` | Create a password reset token for an email address     |
| POST   | `/auth/reset-password`  | Set a new password with a reset token                  |
| POST   | `/user/change-password` | Change the password of the logged-in user              |
| GET    | `/user/profile`         | Example endpoint, requires the role `USER`             |
| GET    | `/admin/users`          | Example endpoint, requires the role `ADMIN`, lists all users |
| GET    | `/auth/test`, `/test`   | Test endpoints                                         |

Protected endpoints expect the token in the `Authorization` header:

```
Authorization: Bearer <token>
```

**Note:** To keep the project simple, `/auth/forgot-password` returns the reset token in the response. A real application would send it by email instead.

## Run the application

**Prerequisites:** JDK 17, MySQL

1. Clone the repository:

   ```
   git clone https://github.com/Amaneh-Nasr/jwt-auth-api.git
   cd jwt-auth-api/jwt-auth-api
   ```
2. Create the database:

   ```sql
   CREATE DATABASE jwt_auth;
   ```
3. Open `src/main/resources/application.properties` and set your own MySQL username and password. By default the application expects MySQL at `localhost:3307` and the database `jwt_auth`. The tables are created automatically on startup.
4. Start the application:

   ```
   ./mvnw spring-boot:run
   ```

   On Windows: `.\mvnw.cmd spring-boot:run`
5. Open Swagger UI (the application runs on port 8080):

   ```
   http://localhost:8080/swagger-ui/index.html
   ```

## Author

**Amaneh Nasrramzy**