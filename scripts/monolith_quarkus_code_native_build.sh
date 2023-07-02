#!/bin/sh
./mvnw -T 10C package -Pnative -Dquarkus.native.container-build=true -am -Pmonolith-quarkus