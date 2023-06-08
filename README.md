# clean-architecture-software-sample-project
Example implementation of clean architecture (with regards to the software development itself). 

## Clean Architecture - my interpretation
The clean architecture concepts I will talk about, are concepts I took/learned from the book "Clean Architecture - A Craftsman's Guide to Software Structure and Design (Robert C. Martin Series)".
As I really advise to read the book, this repository is a very brief summary of what the main concepts are about and an example implementation in Kotlin. 
I will use this setup to make it fairly easy to evaluate/compare Spring Boot with Quarkus and to extract microservices or serverless functions from a monolith. In the next 
section I will share my view on clean architecture and map it on the example project that you can find under /application


![Clean Architecture - Book](images/clean_architecture_book.jpg "Clean Architecture - Book")

The way I would summarize clean architecture is to keep your options open and to reduce the risks on regression. By keeping your options open, I mean that you can 
fall in love with a library, a database technology, ..., but you should never marry with it. You should be able to fairly easy change, add or remove libraries, 
databases, infrastructural components, .... On the "reduce risks on regression" part, clean architecture introduces use cases instead of services. This is violating 
the "don't repeat yourself" (i.e., DRY) principles a little bit (most of it depends on the real implementation) by isolating code to just the user actions. E.g.,
instead of having a person service with "create person", "update email", "changeAddress", .... methods, which are sharing some shared private methods, "CreatePersonUseCase",
"UpdatePersonEmailUseCase", "ChangePersonAddressUseCase", ... classes are created without reuse of code. In this simple example this seems overkill, but the advantage 
of this way of working (especially in more complex scenarios) is that the risk on regression is limited to the user actions you're developing/enhancing/editing: 
Changing the "CreatePersonUseCase" will not affect the "UpdatePersonEmailUseCase", reducing the risk of regression on parts where you don't expect it. As in the end,
every piece of code you touch, can get broken. Clean architecture enforces this by stepping away from the "lasagna layered approach" that service oriented architecture
was (controller layer - service layer - database layer) and introducing an onion layered approach (see image).
![Clean Architecture - Onion layer](images/clean_architecture.jpg "Clean Architecture - Onion layer")
### Core - Domain & use cases
The first layer I will talk about, is the center of the application stack: the core layer. Sometimes this layer is even split in domain (i.e., data classes, POJOs)
and use cases (i.e., the business logic). This is the layer that will contain all the business logic and is bound to an important rule: within the core layer, no 
external (or even internal developed) libraries or dependencies should be used. The only dependency which is allowed over here is the programming language (i.e., Kotlin 
in our case). When developing in plain Java, one other library is open for discussion: Lombok. As this is a library which generates classes and works at compile time, 
it can be allowed in the core layer (i.e., if you want to remove it, you can by replacing the annotated classes with the generated ones). Does this then mean 
that you can't use external libraries? No, you can (and should, do not reinvent the wheel): but you'll have to inject them via interfaces. Database access and other 
infrastructure related code has to follow the same rules as for the external libraries: no implementations in the core, only inject it in the core via interfaces. 
Important aspect over here: even though file bases access is natively enabled in Java and Kotlin itself, it should not be part of the core layer and injected via interfaces 
from an infrastructure module.
One last rule, that I believe is important: Changes in clean architecture should only propagate from the inside to the outer layers, not the other way around. So a change in infrastructure 
should never trigger a change in the core layer.
#### Domain
This is the module that contains the data classes. Both write and read models are residating over here. Although it can be as well that you only have your write model over here 
and that you define the read model on controller level (i.e., in the configuration layer) and map on it via presenters. As I fetch the data via use cases as well, I go for having 
the write and the read model in the domain layer.
#### Use cases
These classes contain the business logic and can be mapped on "commands" from domain driven design. As the business logic is now infrastructure and dependency independent, 
it should be fairly easy to let it withstand the test of time: the layers around it make it (as you'll see in the example application) fairly easy to change infrastructural components 
and/or libraries without having to touch the core/business logic, resulting less in "we don't dare to touch it", which is killing innovation. One discussion point in this section is what 
to do with read methods. You can as well opt/argue for having repositories wired in the (e.g., REST) controllers and mapped on read models via presenters, but I prefer having read commands 
implemented via use cases to. You can implement some aggregations on this level and/or security is part of the core layer as well (to my opinion). It's for that the domain layer contains 
read and write data models and that the use cases cover creation, editing, deletion and read operations. 
Another principle I use within the use case layer, is that every interface that will provide data is called a "repository", even if the real implementation would be HTTP based, like REST.
Reason to do so, and to not call it a "xClient" is that this would bring infrastructure logic implicitly in the core layer: when you would extract a microservice from within a monolith, a core 
interface would be renamed from "xRepository" to "xClient" then. Changes in the core layer when changing infrastructure are not allowed, hence everything that provides data, is a "Repository" for me.
### Infrastructure - Data providers & others
Within the infrastructure layer you implement the connections with infrastructural components (like the name already described). Often you have a submodule per technology or component.
This will make it easier to perform updates, changes or replacements.
#### Data providers
The data providers layer often maps only on database access. To my understanding, this can be database **and** API access: E.g., calling another service's REST API would be 
a dataproviders module as well. Reason behind it: you use a dataprovider to fetch data. If the data is coming from database or an external service, that does not matter. 
E.g., you have a monolith with person and address data. If you extract the address data into a separate microservice, the data provider for address data switched from being 
a database implementation to a REST/GraphQL implementation. In my opinion, that should not result in having to move the submodule to another parent module.
### Entrypoints (REST, GraphQL, gRPC, ...)
There are implementation of clean architecture that have a separate layer/module within the infrastructure layer for exposing endpoints (i.e., REST, GraphQL, gRPC, ....). 
I am not doing this for the following reason: it is the configuration layer that will decide on what functionality is getting exposed, so it is the responsability of the 
configuration to define the endpoints. E.g., if you have person and address data REST APIs in a monolith (i.e., see the example application) and you would like to extract a 
microservice/serverless function next to it and they would share an "endpoints" submodule, then the person data REST endpoints would as well be exposed on the address microservice, 
which is not the purpose. So in my examples, my understanding, exposing endpoints are not a part of the infrastructure layer, but part of the configuration layer. 
An extra added value of developing like this: the exposing of the endpoints is often framework dependent (e.g., SpringBoot, Quarkus, ...). By moving this to the configuration layer, 
your infrastructure layer is unaware of the choice you've made in the configuration layer and you don't have to touch it when interchanging SpringBoot with Quarkus or the other 
way around. **Changes in clean architecture should only propagate from the inside to the outer layers, not the other way around.**
#### Others
As infrastructure is quite generic, other submodules can be part of this section: e.g., thinking about file system access.
### Configuration(s)
Over here you have the "glue", the wiring of your application and its layers: which infrastructure to use, which use cases, which endpoints your exposing and in which technology, .... 
Often this comes down to Spring or SpringBoot for legacy applications or Quarkus for CloudNative applications. In our example project we have multiple subconfigurations: 
we started with a monolith (in Quarkus and SpringBoot), and then gradually extracted microservices from the monolith. These microservices are then serverless served. 
If you're interested in how you would break down the monolith into microservices in a step-by-step flow, check out [this Voxxed Days talk](https://www.youtube.com/watch?v=ekkwMIMVA2Y) about decomposing the monolith with Knative
(as in the real world, you often don't have the time and especially not the budget to always rewrite applications from scratch).

+ see if it mathers for quarkus uberjar

![Clean Architecture - Onion layer - My opinion](images/clean_architecture_my_opinion.jpg "Clean Architecture - Onion layer - My opinion")

## Project setup 
![Clean Architecture - Project setup](images/application_code_base.jpg "Clean Architecture - Project setup")

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