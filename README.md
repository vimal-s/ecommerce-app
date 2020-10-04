# Ecommerce-Application
 Project made as part of Udacity Nanodegree program
 
 This project main focus is to integrate spring security with the application and configure it to use 
 JWT for Authentication and Authorization
 
 Client can perform the following:
 - Register with the application
 - Add/Remove available items to/from their cart
 - Place orders
 
 # Instructions
 All the endpoints are secured except:
 - localhost:8080/api/user/create
 - localhost:8080/login
 
 A JWT is generated when the client first registers which should be passed with every subsequent
 request so that the client can be validated 
 
# Postman collection
[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/0ac5b75b200efb6b43e4)

The Authorization header needs to be updated with the generated JWT
