rootProject.name = "interop-fhir"

include("interop-fhir")
include("interop-fhir-generators")

for (project in rootProject.children) {
    project.buildFileName = "${project.name}.gradle.kts"
}

pluginManagement {
    plugins {
        id("com.projectronin.interop.gradle.junit") version "3.0.0"
        id("com.projectronin.interop.gradle.publish") version "3.0.0"
        id("com.projectronin.interop.gradle.spring") version "3.0.0"
        id("com.projectronin.interop.gradle.version") version "3.0.0"
    }

    repositories {
        maven {
            url = uri("https://repo.devops.projectronin.io/repository/maven-snapshots/")
            mavenContent {
                snapshotsOnly()
            }
        }
        maven {
            url = uri("https://repo.devops.projectronin.io/repository/maven-releases/")
            mavenContent {
                releasesOnly()
            }
        }
        maven {
            url = uri("https://repo.devops.projectronin.io/repository/maven-public/")
            mavenContent {
                releasesOnly()
            }
        }
        mavenLocal()
        gradlePluginPortal()
    }
}
