#!/bin/sh
./mvnw -T 10C clean package -Dquarkus.package.type=uber-jar -am -Pmonolith-quarkus