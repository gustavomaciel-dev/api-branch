# api-branch
POST
/branches
branch:{
    "direccion":"String",
    "latitud":"String",
    "longitud:"String
}
Response: CREATED (201)

GET
/api/branches/id
Response: OK (200)
branch:{
    "direccion":"String",
    "latitud":"String",
    "longitud:"String
}
Response: Bad request (400)
branch:{
    "errors":[
        "error"
    ]
}
Response: Not found (404)

GET
/api/branches?latitud="&latitud=1234"&longitud="1234"
Response: OK (200)
branch:{
    "direccion":"String",
    "latitud":"String",
    "longitud:"String
}

