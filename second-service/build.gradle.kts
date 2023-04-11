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

val resilience4jVersion = "2.0.2"

dependencies {
	implementation(layout.files("../utils/microframe-utils-1.0.1.jar"))

	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.cloud:spring-cloud-starter-config:4.0.1")
	implementation("org.springframework.cloud:spring-cloud-starter-bootstrap:4.0.1")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("javax.xml.bind:jaxb-api:2.3.1")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:4.0.0")

	implementation("io.github.resilience4j:resilience4j-spring-boot3:${resilience4jVersion}")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("io.github.resilience4j:resilience4j-circuitbreaker:${resilience4jVersion}")
//	implementation("io.github.resilience4j:resilience4j-retry:${resilience4jVersion}")
	implementation("io.github.resilience4j:resilience4j-bulkhead:${resilience4jVersion}")
//	implementation("io.github.resilience4j:resilience4j-timelimiter:${resilience4jVersion}")

	implementation("org.springframework.boot:spring-boot-starter-security:3.0.2")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client:3.0.2")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server:3.0.2")
	implementation("org.springframework.security:spring-security-oauth2-jose:6.0.2")

	implementation("org.springframework.cloud:spring-cloud-stream:4.0.1")
	implementation("org.springframework.cloud:spring-cloud-starter-stream-kafka:4.0.1")
	implementation("org.springframework.kafka:spring-kafka:3.0.5")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

extra["keycloakVersion"] = "21.0.1"

dependencyManagement {
	imports {
		mavenBom("org.keycloak.bom:keycloak-adapter-bom:${property("keycloakVersion")}")
	}
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

