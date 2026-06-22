# 🔐 Spring Boot JWT Authentication Service

A secure and scalable authentication system built using Spring Boot and JWT (JSON Web Token). This project demonstrates how to implement stateless authentication using Spring Security.

---

## 🚀 Features

- 🔑 User Registration & Login
- 🔐 JWT Token Generation & Validation
- 🛡️ Spring Security Integration
- 🔄 Stateless Authentication
- 📦 RESTful APIs
- ⚡ Clean and Scalable Architecture

---

## 🛠️ Tech Stack

- Java 17+
- Spring Boot
- Spring Security
- JWT (java-jwt library)
- Maven
- MySQL

---

## 📂 Project Structure

src/main/java/com/example/jwt/
│── controller     # API endpoints  
│── service        # Business logic  
│── repository     # Database layer  
│── model          # Entity classes  
│── security       # JWT & Security config  
│── config         # App configurations  

---

## ⚙️ Setup & Run

### Configure Database

Update `application.properties`:

spring.datasource.url=jdbc:mysql://localhost:3306/db_name  
spring.datasource.username=root  
spring.datasource.password=your_password  

### Run Application

./mvnw spring-boot:run  

---

## 🔑 API Endpoints

| Method | Endpoint       | Description        |
|--------|--------------|--------------------|
| POST   | /auth/register | Register user     |
| POST   | /auth/login    | Login & get JWT   |

---

## 🔐 JWT Flow

1. User logs in with credentials  
2. Server validates and generates JWT  
3. Client sends JWT in Authorization Header  
4. Server validates token for each request  

---

## 📸 Sample Request

POST /auth/login  

{
  "username": "user",
  "password": "password"
}

---

## 📌 Future Improvements

- ✅ Refresh Token Implementation  
- ✅ Role-Based Authorization (RBAC)  
- ✅ Docker Deployment  
- ✅ API Documentation (Swagger)  

---

## 👨‍💻

Deepak Kumar Singh  
