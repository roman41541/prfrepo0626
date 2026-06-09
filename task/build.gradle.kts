plugins {
    kotlin("jvm") version "2.3.21"
    id("io.gatling.gradle") version "3.15.1"
}

group = "perf"

repositories {
    mavenCentral()
    maven {
        url = uri("https://packages.confluent.io/maven/")
    }
}

dependencies {
    gatlingImplementation("io.gatling:gatling-core-java:3.15.1")
    gatlingImplementation("io.gatling:gatling-http-java:3.15.1")
    gatlingImplementation("org.galaxio:gatling-kafka-plugin_2.13:1.0.1")
}

kotlin {
    jvmToolchain(17)
}
