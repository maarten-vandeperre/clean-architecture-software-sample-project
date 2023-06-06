# clean-architecture-software-sample-project
Example implementation of clean architecture (with regards to the software development itself). 


## WIP
TODO extend README



use cases => validation ugly
naming domain => ugly
no created status, but just ref
DRY
startup ./mvnw compile -Dquarkus.profile=db-postgres quarkus:dev -Pmonolith
startup ./mvnw compile -Dquarkus.profile=db-postgres quarkus:dev -microservice-account


Spring Boot multi module start : mvn spring-boot:run -Pmonolith-springboot -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=inmemory" -pl application/configuration/monolith-configuration-springboot



http://localhost:8080/q/graphql-ui/?