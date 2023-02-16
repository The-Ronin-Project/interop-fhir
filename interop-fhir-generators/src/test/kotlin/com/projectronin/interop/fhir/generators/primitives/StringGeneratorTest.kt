package com.projectronin.interop.fhir.generators.primitives

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class StringGeneratorTest {
    @Test
    fun `can generate`() {
        val generator = StringGenerator()
        val string = generator.generate()
        assertNotNull(string)
        assertTrue(string.length >= 15)
    }
}
