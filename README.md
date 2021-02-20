# Farmer-Tracking

Farmer tracking project monitors periodic jobs of crop and this project is backend of mobile and web applications. With this project you can create land, 
crop and create a job about crops for example watering, fertilization.

If you want to test api call you have to get an access token with create a new user.

Create a new user
```
curl -X POST \
  http://192.168.1.38:8080/user \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 2bf9cb4b-4663-d8b0-fcde-bfb9c97bfc0c' \
  -d '{
"userName":"example",
"userEmail":"example@outlook.com",
"userPass":"1234Aa",
"userGsm":"449878879"
}'
```

Take a valid token with created user authentication values

Request:
```
curl -X POST \
  http://64.227.113.38:8080/login \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: ad95f4af-8644-1aaa-c395-4dbc53565b47' \
  -d '{
"username":"mertaksu5",
"password":"1234Aa"
}'
```

Response:
You can take token from response header with authorization tag
```
authorization →Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODUiLCJleHAiOjE2NDUzNTYzMDZ9.h-Gz1Dq-qQC-mhIVif5U_bFzRvg0bsXpPuE3BLflFOa9uwT7u6kUZDp1xkTV6WhlJ-PITope770f2gOwqYy79A
```

This token is JWT. You can call other rest api with this authorization key.

Passwords are storing with bcrypt in database. 

