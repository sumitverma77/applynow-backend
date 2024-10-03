# Job Application Platform

## Overview

This project is a job application platform that allows users to find job opportunities and apply for them, while HRs can post job listings for their companies. The application is built using Spring Boot. 
## Version 1.0
This is the initial version of the project. Future versions will be released with additional features and improvements.

## Technologies Used

- **Spring Boot 3.3.4**: Framework for building the application.
- **MySQL**: Database management system for storing user and job data.
- **AWS Elastic Beanstalk**: For deploying the application in the cloud.
- **AWS RDS**: For managing the MySQL database.

## Features

- **User Registration**: Users can register and create their profiles.
- **Email OTP Verification**: Users verify their email addresses during registration.
- **Authentication**: Secure login and session management for users and HRs.

## API Endpoints

### User Registration

- **POST** `/user/register`
    - Registers a new user.
    - Request Body: `RegisterRequest` (includes user details).

### User Login

- **POST** `/user/login`
    - Logs in a user.
    - Request Body: `LoginRequest` (includes email and password).
    -
### OTP Verification

- **POST** `/user/verify-otp`
    - Verifies the OTP sent to the user's email.
    - Request Body: `OtpVerificationRequest` (includes OTP and email).

### Resend OTP

- **POST** `/user/resend-otp`
    - Resends the OTP to the user's email.
    - Request Header: `email` (the user's email address).

## Maven Dependencies

This project uses the following key dependencies:

- **Spring Boot Starter Web**: For building web applications.
- **Spring Boot Starter Security**: For authentication and security.
- **Spring Data JPA**: For database access.
- **MySQL Connector**: For connecting to the MySQL database.
- **Spring Boot Starter Mail**: For sending emails (e.g., OTP verification).
- **JWT**: For secure user authentication.

For a complete list of dependencies and their versions, please refer to the `pom.xml` file.

### Additional Notes
- **Security Considerations**: Remind users to keep their app passwords secure and not share them in public repositories.
- **Environment Variables**: Consider suggesting the use of environment variables for sensitive information instead of hardcoding them in the `application.properties` file for better security practices.

Feel free to copy this section into your README file to provide clear instructions on setting up email functionality!

## Deployment
The application is deployed on AWS Elastic Beanstalk. The backend connects to a MySQL database hosted on AWS RDS. Configuration details can be found in the application properties.