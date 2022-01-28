import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version Versions.SPRING_BOOT apply false
    id("io.spring.dependency-management") version Versions.SPRING_DEPENDENCY_MANAGEMENT apply false
    kotlin("jvm") version Versions.KOTLIN apply false
    kotlin("kapt") version Versions.KOTLIN apply false
    kotlin("plugin.spring") version Versions.KOTLIN apply false
    kotlin("plugin.allopen") version Versions.KOTLIN apply false
}

allprojects {
    group = "com.briolink"
    version = "1.0.0-SNAPSHOT"

    tasks.withType<JavaCompile> {
        sourceCompatibility = Versions.JAVA.toString()
        targetCompatibility = Versions.JAVA.toString()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = Versions.JAVA.toString()
        }
    }
}

subprojects {
    repositories {
        mavenCentral()
        mavenLocal()
        maven {
            url = uri("https://gitlab.com/api/v4/projects/32844103/packages/maven")

            authentication {
                create<HttpHeaderAuthentication>("header")
            }

            credentials(HttpHeaderCredentials::class) {
                name = "Deploy-Token"
                value = System.getenv("CI_DEPLOY_PASSWORD")
            }
        }
    }

    apply {
        plugin("kotlin")
        plugin("io.spring.dependency-management")
    }

    val implementation by configurations

    dependencies {
        // Briolnik permission
        implementation("com.briolink:permission:0.1.0-SNAPSHOT")
        implementation("me.paulschwarz:spring-dotenv:${Versions.SPRING_DOTENV}")
    }
}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
