plugins {
    alias(libs.plugins.interop.gradle.junit)
    alias(libs.plugins.interop.gradle.spring)
}

dependencies {
    implementation(project(":interop-fhir"))

    implementation(libs.interop.common)
    implementation(libs.interop.commonJackson)
    implementation(libs.ronin.test.data.generator)

    implementation(libs.jackson.core)
    implementation(libs.datafaker)
    implementation(libs.jackson.databind)

    testImplementation(libs.jackson.module.kotlin)
    testImplementation(libs.mockk)

    // Used for some tests utilizing classpath inspection to verify configuration
    testImplementation(libs.classgraph)
}
