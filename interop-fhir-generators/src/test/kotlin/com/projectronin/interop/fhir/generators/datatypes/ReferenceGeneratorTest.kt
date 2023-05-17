package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ReferenceGeneratorTest {
    @Test
    fun `function works`() {
        val reference = reference("Patient", "123")
        assertNull(reference.id)
        assertEquals(0, reference.extension.size)
        assertEquals(FHIRString("Patient/123"), reference.reference)
        assertNull(reference.type)
        assertNull(reference.identifier)
        assertNull(reference.display)
    }

    @Test
    fun `generator works with default`() {
        val reference = ReferenceGenerator().generate()

        assertNull(reference.id)
        assertEquals(0, reference.extension.size)
        assertNull(reference.reference)
        assertNull(reference.type)
        assertNull(reference.identifier)
        assertNull(reference.display)
    }

    @Test
    fun `generator works with values`() {
        val generator = ReferenceGenerator()
        generator.id of "1234"
        generator.type of "Practitioner"
        val reference = generator.generate()

        assertEquals(FHIRString("1234"), reference.id)
        assertEquals(0, reference.extension.size)
        assertNull(reference.reference)
        assertEquals(Uri("Practitioner"), reference.type)
        assertNull(reference.identifier)
        assertNull(reference.display)
    }
}
