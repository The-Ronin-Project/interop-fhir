plugins {
    alias(libs.plugins.interop.gradle.junit)
    alias(libs.plugins.interop.gradle.spring)
}

dependencies {
    implementation(libs.interop.common)
    implementation(libs.interop.commonJackson)
    // Used for ResourceType enum
    implementation(libs.event.interop.resource.internal)

    implementation(libs.classgraph)

    implementation(libs.jackson.core)
    implementation(libs.jackson.databind)

    testImplementation(libs.jackson.module.kotlin)
    testImplementation(libs.mockk)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += listOf("-opt-in=kotlin.RequiresOptIn")
    }
}

tasks {
    test {
        testLogging {
            events("passed", "skipped", "failed")
            showStackTraces = true
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        }
    }
}
