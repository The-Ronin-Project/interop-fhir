plugins {
    // Version must be defined before publish in order to set the proper version
    id("com.projectronin.interop.gradle.version")

    id("com.projectronin.interop.gradle.junit")
    id("com.projectronin.interop.gradle.publish")
    id("com.projectronin.interop.gradle.spring")
}

dependencies {
    implementation(libs.interop.common)
    implementation(libs.interop.commonJackson)
    implementation(libs.fhir.validation)

    testImplementation(libs.jackson.module.kotlin)
    testImplementation(libs.mockk)

    // Used for some tests utilizing classpath inspection to verify configuration
    testImplementation(libs.classgraph)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += listOf("-opt-in=kotlin.RequiresOptIn")
    }
}

tasks.withType<Test> {
    minHeapSize = "2048m"
    maxHeapSize = "4096m"
}
