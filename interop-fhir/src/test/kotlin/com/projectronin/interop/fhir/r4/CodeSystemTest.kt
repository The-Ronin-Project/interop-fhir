package com.projectronin.interop.fhir.r4
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class CodeSystemTest {
    @Test
    fun `codecov for enum values`() {
        CodeSystem.values().forEach { enum ->
            assertNotNull(CodeSystem.values().firstOrNull { it.uri.value == enum.uri.value })
        }
    }
}
