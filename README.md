## accountService
 
used for calculate(sum) some amounts by id(person, organization)
    
values are saved in db and kept in local cache

application.properties manages db recreation and logging level

technologies

    spring-boot
    spring-mvc
    hibernate

has rest endpoints

    GET http://localhost:8080/account/{id}
    PUT http://localhost:8080/account/{id}/{amount}
    GET http://localhost:8080/reset-statistic
    GET http://localhost:8080/get-statistic
    
statistic
    
    read:73 write:419
    readTotal:2754 writeTotal:10046

next database expected for service

    jdbc:postgresql://localhost:5432/postgres?currentSchema=account 
    schema/login/password=account

main class for run service
    
    ru.iportnyagin.accountservice.Service


## test client

there is client for test accountService
client instances are run in concurrent threads

main class for run client 

    ru.iportnyagin.client.Client

args for client
    
    host_with_port readCount writeCount id1,id2,id3...
    
example

    http://localhost:8080 50 50 1,2,3,4,5,6,7,8,9,10,11,30
