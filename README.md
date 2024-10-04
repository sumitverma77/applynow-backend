# Job Application Platform

## Overview

This project is a job application platform that allows users to find job opportunities and apply for them, while HRs can post job listings for their companies. The application is built using Spring Boot.

## Version History

### Version 1.0
- Initial version of the project with the following features:
  - User Registration: Users can register and create their profiles.
  - Email OTP Verification: Users verify their email addresses during registration.
  - Authentication: Secure login with JWT authentication

### Version 2.0
- Introduced new features for job posting and management:
  - Job Posting: HRs can post new job listings.
  - Approve Jobs: Admin can approve job listings for visibility.
  - Delete Jobs: Admin can delete job listings.
  - Retrieve Verified Jobs: Users can retrieve only the verified job listings.

## Technologies Used

- **Spring Boot 3.3.4**: Framework for building the application.
- **MySQL**: Database management system for storing user and job data.
- **AWS Elastic Beanstalk**: For deploying the application in the cloud.
- **AWS RDS**: For managing the MySQL database.

## Features

- **User Registration**: Users can register and create their profiles.
- **Email OTP Verification**: Users verify their email addresses during registration.
- **Authentication**: Secure login and session management for users and HRs.
- **Job Posting**: HRs can post new job listings.
- **Retrieve Jobs**: Users and HRs can retrieve job listings based on criteria.
- **Approve Jobs**: Admin can approve job listings for visibility.
- **Delete Jobs**: Admin can delete job listings.
- **Retrieve Verified Jobs**: users can retrieve only the verified job listings.

## API Endpoints

### User Registration

- **POST** `/user/register`
  - Registers a new user.
  - Request Body: `RegisterRequest` (includes user details).

### User Login

- **POST** `/user/login`
  - Logs in a user.
  - Request Body: `LoginRequest` (includes email and password).

### OTP Verification

- **POST** `/user/verify-otp`
  - Verifies the OTP sent to the user's email.
  - Request Body: `OtpVerificationRequest` (includes OTP and email).

### Resend OTP

- **POST** `/user/resend-otp`
  - Resends the OTP to the user's email.
  - Request Header: `email` (the user's email address).

### Job Posting

- **POST** `/job/post`
  - Posts a new job listing.
  - Request Body: `AddJobRequest` (includes job details).

### Retrieve Jobs

- **POST** `/job/get`
  - Retrieves job listings based on criteria.
  - Request Body: `GetJobRequest` (includes search parameters).

### Retrieve Verified Jobs

- **POST** `/job/get/verified-jobs`
  - Retrieves only verified job listings.
  - Request Body: `GetJobRequest` (includes search parameters).

### Approve Job

- **POST** `/job/approve`
  - Approves a job listing.
  - Request Header: `JOB_ID`, `DATE`, `API_KEY`.

### Delete Job

- **DELETE** `/job/delete`
  - Deletes a job listing.
  - Request Header: `JOB_ID`, `DATE`, `API_KEY`.

## Maven Dependencies

This project uses the following key dependencies:

- **Spring Boot Starter Web**: For building web applications.
- **Spring Boot Starter Security**: For authentication and security.
- **Spring Data JPA**: For database access.
- **MySQL Connector**: For connecting to the MySQL database.
- **Spring Boot Starter Mail**: For sending emails (e.g., OTP verification).
- **JWT**: For secure user authentication.
- **AWS Java SDK DynamoDB**: For managing and accessing DynamoDB tables.
- **AWS Java SDK RDS**: For managing AWS RDS instances.

For a complete list of dependencies and their versions, please refer to the `pom.xml` file.

## Deployment

The application is deployed on AWS Elastic Beanstalk. The Job service backend connects to DynamoDB & user service connects to MySQL database hosted on AWS RDS . Configuration details can be found in the application properties.
