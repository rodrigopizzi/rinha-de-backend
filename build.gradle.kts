import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.graalvm.buildtools.native") version "0.9.27"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    id("org.openapi.generator") version "7.0.1"
}

group = "h2r.dev"
version = "0.0.1-SNAPSHOT"
val basePackage = "h2r.dev.rinhadebackend"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // add springdoc-openapi dependencies
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    // add swagger code gen dependencies
    implementation("javax.servlet:servlet-api:2.5")
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    testImplementation("org.testcontainers:junit-jupiter:1.19.0")
    testImplementation("org.testcontainers:testcontainers:1.19.0")
    testImplementation("org.testcontainers:mongodb:1.19.0")
    testImplementation("org.junit.platform:junit-platform-suite-engine:1.10.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
    sourceSets {
        getByName("main").kotlin.srcDirs(
            "$buildDir/generated/openapi/src/main/kotlin",
            "$rootDir/src/main/java"
        )
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

openApiGenerate {
    inputSpec.set("$rootDir/src/main/resources/rinha-de-backend.yaml")
    outputDir.set("$buildDir/generated/openapi")
    generatorName.set("kotlin-spring")
    modelNameSuffix.set("Dto")
    configOptions.set(
        mapOf(
            "gradleBuildFile" to "true",
            "basePackage" to "$basePackage.application.web.api",
            "apiPackage" to "$basePackage.application.web.api",
            "modelPackage" to "$basePackage.application.web.dto",
            "useTags" to "true",
            "useBeanValidation" to "false",
            "interfaceOnly" to "true",
            "title" to "RinhaDeBackend",
            "useSpringBoot3" to "true",
            "useSwaggerUI" to "true"
        )
    )
}

tasks.compileKotlin { dependsOn("openApiGenerate") }
