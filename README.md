# clean-architecture-software-sample-project
Example implementation of clean architecture (with regards to the software development itself). 

## Clean Architecture - my interpretation
TODO introduction
![Clean Architecture - Onion layer](images/clean_architecture.jpg "Clean Architecture - Onion layer")
### Core - Domain & use cases
TODO
#### Domain
TODO
#### Use cases
TODO
### Infrastructure - Data providers & others
TODO
#### Data providers
TODO
#### Others
TODO
### Configuration(s)
TODO

![Clean Architecture - Onion layer - My opinion](images/clean_architecture_my_opinion.jpg "Clean Architecture - Onion layer - My opinion")

## Comparing/Evaluating Quarkus and Spring Boot
TODO

## Extract (Knative) microservices (Quarkus) from monolith (Spring Boot)
TODO

## Knative versus AWS Lambda and Azure Functions
TODO
![Knative versus AWS Lambda and Azure Functions](images/serverless.jpg "Knative versus AWS Lambda and Azure Functions")

## WIP
TODO extend README



use cases => validation ugly
naming domain => ugly
no created status, but just ref
DRY
startup ./mvnw compile -Dquarkus.profile=db-postgres quarkus:dev -Pmonolith
startup ./mvnw compile -Dquarkus.profile=in-memory quarkus:dev -Pmonolith
startup ./mvnw compile -Dquarkus.profile=db-postgres quarkus:dev -microservice-account


Spring Boot multi module start : mvn spring-boot:run -Pmonolith-springboot -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=inmemory" -pl application/configuration/monolith-configuration-springboot



http://localhost:8080/q/graphql-ui/?