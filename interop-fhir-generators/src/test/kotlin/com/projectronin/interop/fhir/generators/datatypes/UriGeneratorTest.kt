package com.projectronin.interop.fhir.generators.datatypes

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class UriGeneratorTest {
    @Test
    fun `generator works`() {
        val generator = UriGenerator()
        val uri = generator.generate()
        assertNotNull(uri)
        assertNotNull(uri.value)
    }

    @Test
    fun `infix works`() {
        val uri = UriGenerator()
        uri of "123"
        assertEquals("123", uri.generate().value)
    }
}
