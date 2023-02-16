package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.generators.datatypes.name
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PractitionerGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val practitioner = practitioner {}
        assertNull(practitioner.id)
        assertTrue(practitioner.identifier.isEmpty())
        assertEquals(1, practitioner.name.size)
    }

    @Test
    fun `function works with parameters`() {
        val practitioner = practitioner {
            id of Id("id")
            identifier of listOf(
                identifier {}
            )
            name of listOf(name { family of "Felt" })
        }
        assertEquals("id", practitioner.id?.value)
        assertEquals(1, practitioner.identifier.size)
        assertEquals("Felt", practitioner.name.first().family?.value)
    }
}
