import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.0"
}

allprojects.filter { !(it.name == "platform" || it.parent?.name == "platform") }.forEach {
    println("test ${it.name}")

    it.apply(plugin = "idea")
    it.apply(plugin = "org.jetbrains.kotlin.jvm")
    it.apply(plugin = "java")

    it.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

        sourceSets.getByName("main") {
            java.srcDir("src/main/java")
            java.srcDir("src/main/kotlin")
        }
        sourceSets.getByName("test") {
            java.srcDir("src/test/java")
            java.srcDir("src/test/kotlin")
        }
    }

    it.tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}

subprojects {
    group = "com.redhat.demo"
    version = "1.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
        mavenLocal()
    }

    tasks.withType<Test> {
        systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
        maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
        setForkEvery(100)
    }
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-parameters")
    }
}