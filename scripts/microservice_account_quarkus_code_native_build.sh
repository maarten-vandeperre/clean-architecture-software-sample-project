#!/bin/sh
GREEN='\033[0;32m'
RED='\033[0;31m'
BLUE='\033[0;34m'
DARK_GREY='\033[0;30m'
NC='\033[0m' # No Color

    VERSION=$(cat environment/.version) #version of the application
    CONTAINER_IMAGE="my-registry/my-project/microservice-account-quarkus-native:$VERSION"


echo "${GREEN}\n\n\n##########################################################################################${NC}"
echo "${GREEN}##########################################################################################${NC}"
echo "${GREEN}############################### Get Version ##############################################${NC}"
echo "${GREEN}##########################################################################################${NC}"
echo "${GREEN}##########################################################################################${NC}\n\n\n"
    echo $VERSION

echo "${GREEN}\n\n\n##########################################################################################${NC}"
echo "${GREEN}##########################################################################################${NC}"
echo "${GREEN}############################### Start Maven & Container Build ############################${NC}"
echo "${GREEN}##########################################################################################${NC}"
echo "${GREEN}##########################################################################################${NC}\n\n\n"
    ./mvnw clean -T 10C package \
        -Dquarkus.package.type=native \
        -Dquarkus.native.container-build=true \
        -Dquarkus.native.container-runtime=docker \
        -Dquarkus.container-image.build=true \
        -Dquarkus.container-image.image=$CONTAINER_IMAGE \
        -Dquarkus.native.native-image-xmx=5G \
        -am -Pmicroservice-account,quarkus-native

echo "${GREEN}\n\n\n##########################################################################################${NC}"
echo "${GREEN}##########################################################################################${NC}"
echo "${GREEN}############################### End Build ################################################${NC}"
echo "${GREEN}##########################################################################################${NC}"
echo "${GREEN}##########################################################################################${NC}\n"
    echo "Container Image: ${BLUE}$CONTAINER_IMAGE${NC}"
    echo "Example run command: ${BLUE}docker run -p 8086:8081 -e \"QUARKUS_PROFILE=db-in-memory\" $CONTAINER_IMAGE${NC}"
    echo "Test command (when started): ${BLUE}curl http://localhost:8086/api/accounts${NC}"

echo "${GREEN}\n##########################################################################################${NC}"
echo "${GREEN}##########################################################################################${NC}\n"