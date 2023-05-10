plugins {
    id("org.springframework.boot")
    id("war")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(platform(project(":platform:spring-platform")))

    implementation(project(":person-service:application:core:core-utils"))
    implementation(project(":person-service:application:core:domain"))
    implementation(project(":person-service:application:core:usecases"))
    implementation(project(":person-service:application:dataproviders:dataproviders-inmemory"))

    implementation("org.springframework.boot:spring-boot-starter-web"){
        exclude(module = "spring-boot-starter-tomcat")
        exclude(module = "logback-classic")
    }
}

val applicationClass = "com.redhat.demo.appdev.personservice.configuration.PersonServiceApplication"

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    archiveClassifier.set("bootJar")
    mainClass.set(applicationClass)
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootWar>("bootWar") {
    archiveClassifier.set("bootWar")
    mainClass.set(applicationClass)
}

tasks.named<Jar>("jar") {
    archiveClassifier.set("")
}
