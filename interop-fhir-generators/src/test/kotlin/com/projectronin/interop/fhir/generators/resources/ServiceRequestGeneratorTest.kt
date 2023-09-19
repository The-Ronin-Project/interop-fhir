package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.reference
import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ServiceRequestGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val serviceRequest = serviceRequest {}

        assertNull(serviceRequest.id)
        assertNull(serviceRequest.meta)
        assertNull(serviceRequest.implicitRules)
        assertNull(serviceRequest.language)
        assertNull(serviceRequest.text)
        assertEquals(0, serviceRequest.contained.size)
        assertEquals(0, serviceRequest.extension.size)
        assertEquals(0, serviceRequest.modifierExtension.size)
        assertEquals(0, serviceRequest.identifier.size)
        assertEquals(0, serviceRequest.instantiatesCanonical.size)
        assertEquals(0, serviceRequest.instantiatesUri.size)
        assertEquals(0, serviceRequest.basedOn.size)
        assertEquals(0, serviceRequest.replaces.size)
        assertNull(serviceRequest.requisition)
        assertNotNull(serviceRequest.status)
        assertNotNull(serviceRequest.intent)
        assertEquals(0, serviceRequest.category.size)
        assertNull(serviceRequest.priority)
        assertNull(serviceRequest.doNotPerform)
        assertNull(serviceRequest.code)
        assertEquals(0, serviceRequest.orderDetail.size)
        assertNull(serviceRequest.quantity)
        assertNotNull(serviceRequest.subject)
        assertNull(serviceRequest.encounter)
        assertNull(serviceRequest.occurrence)
        assertNull(serviceRequest.asNeeded)
        assertNull(serviceRequest.authoredOn)
        assertNull(serviceRequest.requester)
        assertNull(serviceRequest.performerType)
        assertEquals(0, serviceRequest.performer.size)
        assertEquals(0, serviceRequest.locationCode.size)
        assertEquals(0, serviceRequest.locationReference.size)
        assertEquals(0, serviceRequest.reasonCode.size)
        assertEquals(0, serviceRequest.reasonReference.size)
        assertEquals(0, serviceRequest.insurance.size)
        assertEquals(0, serviceRequest.supportingInfo.size)
        assertEquals(0, serviceRequest.specimen.size)
        assertEquals(0, serviceRequest.bodySite.size)
        assertEquals(0, serviceRequest.note.size)
        assertNull(serviceRequest.patientInstruction)
        assertEquals(0, serviceRequest.relevantHistory.size)
    }

    @Test
    fun `function works with parameters`() {
        val serviceRequest = serviceRequest {
            requester of reference("Practitioner", "1234")
            patientInstruction of "No food 8 hours before"
        }

        assertEquals(Reference(reference = FHIRString("Practitioner/1234")), serviceRequest.requester)
        assertEquals(FHIRString("No food 8 hours before"), serviceRequest.patientInstruction)
    }
}
