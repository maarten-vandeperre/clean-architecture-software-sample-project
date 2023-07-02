#!/bin/sh
./mvnw -T 10C clean install -Pmonolith-springboot
./mvnw -T 10C package spring-boot:repackage -DskipTests -Pmonolith-springboot -pl application/configuration/monolith-configuration-springboot