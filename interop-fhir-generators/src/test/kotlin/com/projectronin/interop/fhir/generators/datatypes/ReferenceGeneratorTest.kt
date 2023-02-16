package com.projectronin.interop.fhir.generators.datatypes

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ReferenceGeneratorTest {

    @Test
    fun `function works`() {
        val reference = reference("Patient", "123")
        assertEquals("123", reference.id?.value)
        assertEquals("Patient", reference.type?.value)
        assertEquals("Patient/123", reference.reference?.value)
        assertNotNull(reference.identifier)
        assertNull(reference.display)
    }

    @Test
    fun `generator works`() {
        val generator = ReferenceGenerator()
        val reference = generator.generate()
        assertNotNull(reference)
        assertNotNull(reference.type)
        assertNotNull(reference.identifier)
        assertNull(reference.id)
        assertNull(reference.display)
        assertNull(reference.reference)
    }
}
