package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.codeableConcept
import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.generators.datatypes.reference
import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MedicationRequestGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val medicationRequest = medicationRequest {}
        assertNull(medicationRequest.id)
        assertNull(medicationRequest.meta)
        assertNull(medicationRequest.implicitRules)
        assertNull(medicationRequest.language)
        assertNull(medicationRequest.text)
        assertEquals(0, medicationRequest.contained.size)
        assertEquals(0, medicationRequest.extension.size)
        assertEquals(0, medicationRequest.modifierExtension.size)
        assertTrue(medicationRequest.identifier.isEmpty())
        assertNull(medicationRequest.intent)
        assertEquals(DynamicValueType.CODEABLE_CONCEPT, medicationRequest.medication?.type)
        assertNull(medicationRequest.status)
        assertNotNull(medicationRequest.subject)
        assertNotNull(medicationRequest.requester)
    }

    @Test
    fun `function works with parameters`() {
        val medicationRequest = medicationRequest {
            id of Id("id")
            identifier of listOf(
                identifier {}
            )
            intent of "intent"
            medicationCodeableConcept of codeableConcept { text of "code" }
            status of "status"
            subject of reference("Patient", "123")
            requester of reference("Requester", "321")
        }
        assertEquals("id", medicationRequest.id?.value)
        assertEquals(1, medicationRequest.identifier.size)
        assertEquals("intent", medicationRequest.intent?.value)
        val actualMedication = medicationRequest.medication?.value as CodeableConcept
        assertEquals("code", actualMedication.text?.value)
        assertEquals("status", medicationRequest.status?.value)
        assertEquals("Patient/123", medicationRequest.subject?.reference?.value)
        assertEquals("Requester/321", medicationRequest.requester?.reference?.value)
    }
}
