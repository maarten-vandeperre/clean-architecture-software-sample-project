plugins {
  `java-platform`
}

javaPlatform {
  allowDependencies()
}
dependencies {
  api(platform("org.springframework.boot:spring-boot-dependencies:${properties.get("springBootVersion")}"))
}
