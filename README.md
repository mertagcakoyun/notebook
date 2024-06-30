# Notebook API Usage Guide
## Overview
The Notebook API is designed to provide a backend service for a simple note-taking application. It allows users to register, login, and manage their notes through various endpoints. This API is built using Spring Boot and utilizes JWT for authentication.

## Features
  - User Registration and Login
  - JWT-based Authentication
  - CRUD operations for notes
  - Access control for note operations.

##  Technologies Used
 - Spring Boot: Framework used for building the backend application.
 - Spring Security: For securing the API endpoints and managing authentication.
 - JWT (JSON Web Token): For authentication and secure data transfer.
 - PostgreSQL: Database used for storing user and note data.
 - Docker

 ##  API Endpoints
### User Operations
#### Get All Users

Method: GET

URL: http://127.0.0.1:8080/api/users/getAll

Description: Retrieves all users.

```
curl --location --request GET 'http://127.0.0.1:8080/api/users/getAll'
```
#### Get User by ID
Method: GET

URL: http://127.0.0.1:8080/api/users/id/{id}

Description: Retrieves a user by ID.

```
curl --location --request GET 'http://127.0.0.1:8080/api/users/id/1'
```

#### Register User

Method: POST

URL: http://127.0.0.1:8080/api/users/register

Description: Registers a new user.
```
curl --location --request POST 'http://127.0.0.1:8080/api/users/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "nickname",
    "password": "123"
}'
```
#### Login User
Method: POST

URL: http://127.0.0.1:8080/api/users/login

Description: Logs in a user and returns a JWT token.
```
curl --location --request POST 'http://127.0.0.1:8080/api/users/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "nickname",
    "password": "123"
}'
```
### Note Operations
#### Add Note
Method: POST

URL: http://localhost:8080/api/notes

Description: Adds a new note.

Authorization: Bearer Token
```
curl --location --request POST 'http://localhost:8080/api/notes' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJasdfGqwsZXJ0bzMiLCJleHAiOjE3MTk0MjQ5OTYsImlhdCI6MTcxOTM4ODk5Nn0.f0fU9MHBrMHiN6Yo-tKiAF7Uan7kWDsntZR57XfsZB7' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "New Note Title",
    "content": "This is the content of the new note."
}'
```
#### Get All Notes
Method: GET

URL: http://localhost:8080/api/notes

Description: Retrieves all notes for the authenticated user.

Authorization: Bearer Token
```
curl --location --request GET 'http://localhost:8080/api/notes' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJasdfGqwsZXJ0bzMiLCJleHAiOjE3MTk0MjQ5OTYsImlhdCI6MTcxOTM4ODk5Nn0.f0fU9MHBrMHiN6Yo-tKiAF7Uan7kWDsntZR57XfsZ'
```
#### Update Note
Method: PUT

URL: http://localhost:8080/api/notes/{id}

Description: Updates a note.

Authorization: Bearer Token

```
curl --location --request PUT 'http://localhost:8080/api/notes/7' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJasdfGqwsZXJ0bzMiLCJleHAiOjE3MTk0MjQ5OTYsImlhdCI6MTcxOTM4ODk5Nn0.f0fU9MHBrMHiN6Yo-tKiAF7Uan7kWDsntZR57XfsZB7' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "Updated Note Title",
    "content": "This is the updated content of the note."
}'
```
#### Delete Note
Method: DELETE

URL: http://localhost:8080/api/notes/{id}

Description: Deletes a note.

Authorization: Bearer Token

```
curl --location --request DELETE 'http://localhost:8080/api/notes/9' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJasdfGqwsZXJ0bzMiLCJleHAiOjE3MTk0MjQ5OTYsImlhdCI6MTcxOTM4ODk5Nn0.f0fU9MHBrMHiN6Yo-tKiAF7Uan7kWDsntZR57XfsZB7'
```
This guide provides the necessary information to test the Notebook API endpoints using cURL commands with appropriate authorization and content headers. Each request requires a valid JWT token, which can be obtained through the login endpoint.
