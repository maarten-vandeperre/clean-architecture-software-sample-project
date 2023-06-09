#!/bin/sh
GREEN='\033[0;32m'
RED='\033[0;31m'
BLUE='\033[0;34m'
DARK_GREY='\033[0;30m'
NC='\033[0m' # No Color

    VERSION=$(cat environment/.version) #version of the application
    CONTAINER_IMAGE="my-registry/my-project/monolith-springboot:$VERSION"

echo "${GREEN}\n\n\n##########################################################################################${NC}"
echo "${GREEN}##########################################################################################${NC}"
echo "${GREEN}############################### Get Version ##############################################${NC}"
echo "${GREEN}##########################################################################################${NC}"
echo "${GREEN}##########################################################################################${NC}\n\n\n"
    echo $VERSION

echo "${GREEN}\n\n\n##########################################################################################${NC}"
echo "${GREEN}##########################################################################################${NC}"
echo "${GREEN}############################### Start Maven Build ########################################${NC}"
echo "${GREEN}##########################################################################################${NC}"
echo "${GREEN}##########################################################################################${NC}\n\n\n"
    ./mvnw -T 10C clean install -DskipTests -Pmonolith-springboot

echo "${GREEN}\n\n\n##########################################################################################${NC}"
echo "${GREEN}##########################################################################################${NC}"
echo "${GREEN}############################### Start Container Build ####################################${NC}"
echo "${GREEN}##########################################################################################${NC}"
echo "${GREEN}##########################################################################################${NC}\n\n\n"
    docker build -t $CONTAINER_IMAGE \
        -f application/configuration/monolith-configuration-springboot/src/main/container/ContainerImageDefinition \
        ./application/configuration/monolith-configuration-springboot

echo "${GREEN}\n\n\n##########################################################################################${NC}"
echo "${GREEN}##########################################################################################${NC}"
echo "${GREEN}############################### End Build ################################################${NC}"
echo "${GREEN}##########################################################################################${NC}"
echo "${GREEN}##########################################################################################${NC}\n"
    echo "Container Image: ${BLUE}$CONTAINER_IMAGE${NC}"
    echo "Example run command: ${BLUE}docker run -p 8081:8080 -e \"SPRING_PROFILES_ACTIVE=inmemory\" $CONTAINER_IMAGE${NC}"
    echo "Test command (when started): ${BLUE}curl http://localhost:8081/api/people${NC}"

echo "${GREEN}\n##########################################################################################${NC}"
echo "${GREEN}##########################################################################################${NC}\n"

