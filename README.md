# api-branch v1.0
#### Description
Api allows crate a new branch, get a branch by id or get nearest branch to laitude and longitude params

#### Compile and Execute project
```

 1) git clone https://github.com/gustavomaciel-dev/api-branch.git
 2) got to folder /api-branch:
 3)mvn clean package
 4)docker build -t api-branch:0.0.1 .
 5)docker run -d --name api-branch -p 8080:8080 api-branch:0.0.1
```
#### Health
```
http://localhost:8080/actuator/health
```

# Http methods
examples data in resources/data.sql
##### POST

```
/api/v1/branches  create a new branch
{
    "direccion":"String",
    "latitud":"String",
    "longitud:"String
}

EXAMPLE: 
    REQUEST
        {
            "address": "mendoza 4152, Quilmes Oeste",
            "latitude": 9876.0,
            "longitude": 3218.0
        }
    RESPONSE CREATED (201)
        {
            "id": 5,
            "address": "mendoza 4152, Quilmes Oeste",
            "latitude": 9876.0,
            "longitude": 3218.0
        }
```
##### GET BY ID (return a branch by ID)

```
/api/v1/branches/{id}
/api/v1/branches/5

Response: OK (200)
{
    "direccion":"String",
    "latitud":"String",
    "longitud:"String
}
Example:
    {
      "id": 5,
      "address": "mendoza 4152, Quilmes Oeste",
      "latitude": 9876,
      "longitude": 3218
    }
 ```
 
##### GET NEAREST AT POINT (return nearest branch by latitude and longitude parameters)

```
/api/v1/branches/nearest?latitude=1234&longitude=4324
Response: OK (200)
{
    "direccion":"String",
    "latitud":"String",
    "longitud:"String
}
Example:
    Response
    {
        "id": 2,
        "address": "calle808",
        "latitude": 1234.0,
        "longitude": 4321.0
    }
```
#### Dependencies
```
h2
lombok
modelmapper
openapi
actuator
jpa
```

