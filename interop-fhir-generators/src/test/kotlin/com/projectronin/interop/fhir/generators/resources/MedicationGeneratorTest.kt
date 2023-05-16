package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.codeableConcept
import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MedicationGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val medication = medication {}
        assertNull(medication.id)
        assertNull(medication.meta)
        assertNull(medication.implicitRules)
        assertNull(medication.language)
        assertNull(medication.text)
        assertEquals(0, medication.contained.size)
        assertEquals(0, medication.extension.size)
        assertEquals(0, medication.modifierExtension.size)
        assertTrue(medication.identifier.isEmpty())
        assertNull(medication.status)
        assertNotNull(medication.code)
    }

    @Test
    fun `function works with parameters`() {
        val medication = medication {
            id of Id("id")
            identifier of listOf(
                identifier {}
            )
            status of "status"
            code of codeableConcept {
                text of "code"
            }
        }
        assertEquals("id", medication.id?.value)
        assertEquals(1, medication.identifier.size)
        assertEquals("status", medication.status?.value)
        assertEquals("code", medication.code?.text?.value)
    }
}
