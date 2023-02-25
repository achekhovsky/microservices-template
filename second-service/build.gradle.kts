import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	id("org.springframework.boot") version "3.0.2"
	id("io.spring.dependency-management") version "1.1.0"
	id("org.jetbrains.kotlin.jvm") version "1.8.0"
	id("org.jetbrains.kotlin.plugin.spring") version "1.8.0"
	id("com.palantir.docker") version "0.34.0"
}

group = "com.microframe"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.cloud:spring-cloud-starter-config:4.0.1")
	implementation("org.springframework.cloud:spring-cloud-starter-bootstrap:4.0.1")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("javax.xml.bind:jaxb-api:2.3.1")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}


docker {
	name = "${project.group}-${project.name}:${project.version}"
	tag("microframeSecond", "${project.version}")
	setDockerfile(file("Dockerfile"))
	buildArgs(mapOf("JAR_FILE" to "/build/libs/${project.name}-$version.jar"))
	copySpec.from("build/libs").into("build/libs")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.named<BootJar>("bootJar") {
	layered {
		enabled.set(true)
		includeLayerTools.set(true)
	}
}

