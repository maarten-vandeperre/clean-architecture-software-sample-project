plugins {
  `java-platform`
}

javaPlatform {
  allowDependencies()
}
dependencies {
  api(platform("io.quarkus:quarkus-bom:${properties.get("quarkusVersion")}"))
}
