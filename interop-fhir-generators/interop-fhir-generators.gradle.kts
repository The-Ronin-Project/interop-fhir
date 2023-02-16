plugins {
    // Version must be defined before publish in order to set the proper version
    id("com.projectronin.interop.gradle.version")

    id("com.projectronin.interop.gradle.junit")
    id("com.projectronin.interop.gradle.publish")
    id("com.projectronin.interop.gradle.spring")
}

dependencies {
    implementation(project(":interop-fhir"))

    implementation(libs.interop.common)
    implementation(libs.interop.commonJackson)
    implementation(libs.ronin.test.data.generator)

    implementation(libs.jackson.core)
    implementation(libs.javafaker)
    implementation(libs.jackson.databind)

    testImplementation(libs.jackson.module.kotlin)
    testImplementation(libs.mockk)

    // Used for some tests utilizing classpath inspection to verify configuration
    testImplementation(libs.classgraph)
}
