plugins {
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
    implementation("com.h2database:h2:2.2.220")
    implementation("com.zaxxer:HikariCP:5.1.0")

    implementation("io.javalin:javalin:5.6.3")
    implementation("io.javalin:javalin-rendering:5.6.3")
    implementation("org.slf4j:slf4j-simple:2.0.7")
    implementation("org.projectlombok:lombok:1.18.26")
    implementation("gg.jte:jte:3.1.0")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
