plugins {
    id("jacoco")
    id("application")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

application {
    mainClass = "hexlet.code.App"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:5.6.3")
    implementation("org.slf4j:slf4j-simple:2.0.7")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
