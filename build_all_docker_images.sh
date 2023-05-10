#!/bin/sh
docker build -t jboss -f ./person-service/application/configuration/jboss-spring-configuration/metadata/Dockerfile ./person-service/application/configuration/jboss-spring-configuration
