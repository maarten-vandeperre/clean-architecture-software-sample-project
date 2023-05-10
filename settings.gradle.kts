pluginManagement {
    val quarkusPluginVersion: String by settings
    val quarkusPluginId: String by settings
    val springBootPluginVersion: String by settings
    val springBootPluginId: String by settings
    repositories {
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
    plugins {
        id(quarkusPluginId) version quarkusPluginVersion
        id(springBootPluginId) version springBootPluginVersion
    }
}
rootProject.name = "clean-architecture-software-sample-project"

include(":platform:quarkus-platform")
include(":platform:spring-platform")

include(":person-service:application:core:core-utils")
include(":person-service:application:core:domain")
include(":person-service:application:core:usecases")
include(":person-service:application:dataproviders:dataproviders-inmemory")
include(":person-service:application:configuration:quarkus-configuration")
include(":person-service:application:configuration:jboss-spring-configuration")
include(":testing:api-testing")

rootProject.children
        .flatMap { child -> if (child.children.isEmpty()) listOf(child) else child.children }
        .flatMap { child -> if (child.children.isEmpty()) listOf(child) else child.children }
        .flatMap { child -> if (child.children.isEmpty()) listOf(child) else child.children }
        .flatMap { child -> if (child.children.isEmpty()) listOf(child) else child.children }
        .forEach { subproject ->
            println("configure: " + subproject.name + ".gradle.kts")
            subproject.buildFileName = subproject.name + ".gradle.kts"
        }