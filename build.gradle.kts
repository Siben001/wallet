import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.Logging
import org.jooq.meta.jaxb.Property
val jooqVersion = "3.15.9"
buildscript {
	configurations["classpath"].resolutionStrategy.eachDependency {
		if (requested.group == "org.jooq") {
			useVersion("3.15.9")
		}
	}
}
plugins {
	id("org.springframework.boot") version "2.6.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("nu.studer.jooq") version "6.0"
	kotlin("jvm") version "1.5.0"
	kotlin("kapt") version "1.5.0"
	kotlin("plugin.allopen") version "1.5.0"
	kotlin("plugin.spring") version "1.5.0"
}
group = "wallet"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11
repositories {
	mavenCentral()
}
dependencies {
// implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
// implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.liquibase:liquibase-core")
	implementation("org.springframework:spring-jdbc")
//  implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")
	implementation("io.github.microutils:kotlin-logging:1.12.5")
	implementation("ch.qos.logback:logback-classic:1.2.6")
	jooqGenerator("org.postgresql:postgresql:42.3.2")
	jooqGenerator("org.jooq:jooq-meta-extensions-liquibase")
	jooqGenerator("org.liquibase:liquibase-core:3.10.3")
	jooqGenerator("org.slf4j:slf4j-jdk14:1.7.30")
	runtimeOnly("org.postgresql:postgresql")
//  runtimeOnly("io.r2dbc:r2dbc-postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}
tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}
tasks.withType<Test> {
	useJUnitPlatform()
}
jooq {
	version.set(jooqVersion)
	configurations {
		create("main") {
			generateSchemaSourceOnCompilation.set(true)
			jooqConfiguration.apply {
				logging = Logging.WARN
				generator.apply {
					name = "org.jooq.codegen.KotlinGenerator"
					database.apply {
						name =  "org.jooq.meta.extensions.liquibase.LiquibaseDatabase"
//            includes = ".*"
//            excludes = "databasechangelog|databasechangeloglock"
//            inputSchema = "public"
						properties.add(
							Property()
								.withKey("scripts")
								.withValue("src/main/resources/db/changelog/db.changelog-master.xml")
						)
						properties.add(
							Property()
								.withKey("includeLiquibaseTables")
								.withValue("false")
						)
					}
					generate.apply {
						isDeprecated = false
						isRecords = true
						isImmutablePojos = true
					}
					target.apply {
						packageName = "wallet.demo"
						directory = "build/generated-src/jooq/main/kotlin"
					}
				}
			}
		}
	}
}
tasks.named<nu.studer.gradle.jooq.JooqGenerate>("generateJooq") { allInputsDeclared.set(true) }


