import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.10"
}

group = "com.microframe.custom.utils"
version = "1.0.1"

repositories {
    mavenCentral()
}

val jakartaServletApiVersion = "6.0.0"
val slf4jVersion = "2.0.6"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    compileOnly("jakarta.servlet:jakarta.servlet-api:$jakartaServletApiVersion")
}

tasks.register<Copy>("copyJarToRootDir") {
        from(layout.buildDirectory.dir("libs"))
        into("${rootProject.projectDir}")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}