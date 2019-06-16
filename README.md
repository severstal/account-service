next database expected for service

jdbc:postgresql://localhost:5432/postgres?currentSchema=account 

schema/login/password=account

main class for run service
ru.iportnyagin.accountservice.Service

main class for run client 
ru.iportnyagin.client.Client

be careful with args:
host_with_port readCount writeCount id1,id2,id3...
http://localhost:8080 50 50 1,2,3,4,5,6,7,8,9,10,11,30

application.properties manages db recreation and logging level 
