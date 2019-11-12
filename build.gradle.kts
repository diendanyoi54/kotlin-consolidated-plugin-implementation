import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.2.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("jvm") version "1.3.50"
    kotlin("plugin.spring") version "1.3.50"

    // TODO: Figure out how to successfully make these reside in kotlin-consolidated-plugin (may be related to https://github.com/gradle/gradle/issues/1156)
    // Even though these must manually be specified here, it still uses the custom plugin's configuration
    jacoco
    `maven-publish`
}

java.sourceCompatibility = JavaVersion.VERSION_1_8

springBoot.buildInfo {
    group = project.properties["group"].toString()
    version = project.properties["version"].toString()
    description = project.properties["description"].toString()
}

buildscript { // Custom plugin must be accessed by this buildscript
    repositories {
        maven {
            url = uri("https://${project.properties["nexus.host"].toString()}:${project.properties["nexus.port.jar"].toString()}/repository/maven-public")
            credentials {
                username = project.properties["nexus.user"].toString()
                password = project.properties["nexus.password"].toString()
            }
        }
    }
    dependencies { classpath("com.company:kotlin-consolidated-plugin:1.0.0-SNAPSHOT") }
}

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-oauth2")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.springframework.security:spring-security-test")

    // Jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Logging
    implementation("io.github.microutils:kotlin-logging:1.6.26") {
        exclude(module = "kotlin-stdlib-common")
    }

    // Http
    implementation("khttp:khttp:0.1.0")

    // Docs
    testImplementation("capital.scalable:spring-auto-restdocs-core:2.0.6")
    testImplementation("capital.scalable:spring-auto-restdocs-json-doclet:2.0.6")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc:2.0.3.RELEASE")

    // Test
    testImplementation("io.mockk:mockk:1.9.3") {
        exclude(module = "kotlin-reflect")
    }
}

dependencyManagement.imports { mavenBom("org.springframework.cloud:spring-cloud-dependencies:Greenwich.SR2") }

configurations.all {
    exclude(group = "ch.qos.logback", module = "logback-classic")
    exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

apply(plugin = "com.company.kotlinconsolidatedplugin") // Custom plugin cannot reside in plugin declaration above