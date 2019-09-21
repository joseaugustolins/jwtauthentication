# jwtauthentication
###Authentication and Authorization with Spring Boot and JWT

- GET in http://localhost:8080/publico/teste( Allowed for All)

- It is mandatory to start PostgreSQL and insert user records on the database
` docker run -p 5432:5432 -d postgres`
`insert into role(nome_role) values('ROLE_ADMIN');
insert into role(nome_role) values('ROLE_USER');
insert into usuario(login, nome, senha) values('joao', 'joao carlos', '$2a$10$Qp8y0r6u62BFtDAPkeZLJew584Q9codaZTAaIy.WyFszxz.GLbb42');
insert into usuario(login, nome, senha) values('maria', 'maria jos', '$2a$10$Qp8y0r6u62BFtDAPkeZLJew584Q9codaZTAaIy.WyFszxz.GLbb42');
insert into usuarios_roles(usuario_id, role_id) values('joao', 'ROLE_ADMIN');
insert into usuarios_roles(usuario_id, role_id) values('joao', 'ROLE_USER');
insert into usuarios_roles(usuario_id, role_id) values('maria', 'ROLE_USER');`

## To GET the TOKEN - Insert Users on the database
- POST in  http://localhost:8080/pegaToken without header. Required put on body JSON(application/json):
`{
"username": "joao",
"password": "123"
}`

- OBS.: The user must in the usuario table on postgresql database. The response body is a token.

- Put the token generated on the header: Key: Authorization Value: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYXJpYSIsImV4cCI6MTU2ODg0OTIyNywiaWF0IjoxNTY4ODMxMjI3fQ.y0HNhi12mqgy9ihYD7Nc2h67VLDrwIwbiFv3uoe42KY4_QDDQpIOhQAiCVpUeIrvUPDNLXMgSM2nlPAVRftw1Q
- GET in http://localhost:8080/autenticado - Only allowed users can access this get url
-
- GET http://localhost:8080/soadmin - Only admin can access this get url