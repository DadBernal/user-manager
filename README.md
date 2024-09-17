# easy-rest-api

User Manager is an api rest project that use springboot-data-rest to implement it.
It uses spring-data-jpa to implement repositories and in-memory database H2 to persist de data.
Also, it uses Spring JWT for authentication.

For this example there was created 4 endpoint to manage 'Users':

### Get Users: 
* Method: GET
* endpoint: /users
* Need Authentication
* Response: 
 

### Get User by email:
* Method: GET
* endpoint: /user/<email>
* input: 'email'
* Need Authentication
* Response: 


### Create User:
* Method: POST
* endpoint: /auth/user
* Request:


### User Login
* Method: POST
* endpoint: /auth/login
* Request:
