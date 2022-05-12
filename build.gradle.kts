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

tasks.withType<Test> {
    minHeapSize = "2048m"
    maxHeapSize = "4096m"
}

// For now, we will need to manually invoke the updateFHIRImplGuide gradle task.
val updateFHIRImplGuide = task<Exec>("updateFHIRImplGuide") {
    workingDir = projectDir
    commandLine("./update_FHIR_impl_guide.sh")
}
