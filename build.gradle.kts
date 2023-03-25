import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    api("net.javacrumbs.json-unit:json-unit-assertj:2.35.0")
    implementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("commons-io:commons-io:2.6")
    implementation("com.github.erosb:everit-json-schema:1.14.1")

    api("org.json:json:20220320")

    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}


tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}