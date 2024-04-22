plugins {
    java
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.flywaydb:flyway-core")
    runtimeOnly("org.postgresql:postgresql")

    implementation ("io.jsonwebtoken:jjwt-api:0.12.3")
    implementation ("io.jsonwebtoken:jjwt-impl:0.12.3")
    implementation ("io.jsonwebtoken:jjwt-jackson:0.12.3")

    implementation ("org.springframework.boot:spring-boot-starter-websocket")
    implementation ("com.google.maps:google-maps-services:0.15.0")
    implementation ("org.springframework:spring-messaging")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

    compileOnly ("org.projectlombok:lombok")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
