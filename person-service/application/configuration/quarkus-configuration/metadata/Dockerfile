FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY build/quarkus-configuration-1.0.0-SNAPSHOT-runner.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]