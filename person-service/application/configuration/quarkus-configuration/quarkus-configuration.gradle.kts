plugins {
    id("io.quarkus")
}

dependencies {
    implementation(platform(project(":platform:quarkus-platform")))
    implementation("io.quarkus:quarkus-container-image-jib")
    implementation("io.quarkus:quarkus-config-yaml")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-kotlin")

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")

    implementation(project(":person-service:application:core:core-utils"))
    implementation(project(":person-service:application:core:domain"))
    implementation(project(":person-service:application:core:usecases"))
    implementation(project(":person-service:application:dataproviders:dataproviders-inmemory"))

    //########################################################################################
    //################################## real dependencies ###################################
    //########################################################################################

    implementation("io.quarkus:quarkus-resteasy-reactive")
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.quarkus:quarkus-smallrye-openapi")
}