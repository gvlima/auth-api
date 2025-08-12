# Spring Security with JWT implementation
This project demonstrates a complete implementation of authentication and authorization using Spring Security with JSON Web Tokens (JWT).

## Features
- User registration
- User login with generation of JWT token
- Persistence layer with H2 Database
- Password encryption with BCrypt
- JWT authentication filter for protected routes
- Role-based authorization
- Custom access denied handling
- Logout mechanism
- Refresh token

## Tecnologies
- Java 21
- Spring Boot 3.5.4
- Spring Security 6.5.2
- H2 Database
- JJWT (Java JWT)
- MapStruct
- Lombok
- Maven

## Getting Starte
To get started with this project, you will need to have the following installed on your local machine:

- JDK 21
- Maven 3+

To build and run the project, follow these steps:

```bash
# 1. Clonar o repositório
git clone https://github.com/gvlima/auth-api.git

# 2. Entrar no diretório do projeto
cd auth-api

# 3. Compilar o projeto
mvn clean install

# 4. Executar a aplicação
mvn spring-boot:run
```


