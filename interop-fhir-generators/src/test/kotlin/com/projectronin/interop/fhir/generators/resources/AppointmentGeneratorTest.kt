package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.generators.datatypes.participant
import com.projectronin.interop.fhir.generators.primitives.instant
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AppointmentGeneratorTest {

    @Test
    fun `function works with defaults`() {
        val appt = appointment {}
        assertNull(appt.id)
        assertNull(appt.meta)
        assertNull(appt.implicitRules)
        assertNull(appt.language)
        assertNull(appt.text)
        assertEquals(0, appt.contained.size)
        assertEquals(0, appt.extension.size)
        assertEquals(0, appt.modifierExtension.size)
        assertTrue(appt.identifier.isEmpty())
        assertNotNull(appt.status)
        assertNull(appt.cancelationReason)
        assertEquals(0, appt.serviceCategory.size)
        assertEquals(0, appt.serviceType.size)
        assertEquals(0, appt.specialty.size)
        assertNull(appt.appointmentType)
        assertEquals(0, appt.reasonCode.size)
        assertEquals(0, appt.reasonReference.size)
        assertNull(appt.priority)
        assertNull(appt.description)
        assertEquals(0, appt.supportingInformation.size)
        assertNull(appt.start)
        assertNull(appt.end)
        assertNull(appt.minutesDuration)
        assertEquals(0, appt.slot.size)
        assertNull(appt.created)
        assertNull(appt.comment)
        assertNull(appt.patientInstruction)
        assertEquals(0, appt.basedOn.size)
        assertEquals(0, appt.participant.size)
        assertEquals(0, appt.requestedPeriod.size)
    }

    @Test
    fun `function works with parameters`() {
        val appt = appointment {
            id of Id("id")
            identifier of listOf(
                identifier {}
            )
            status of "status"
            participant of listOf(
                participant {}
            )
            minutesDuration of 10
            start of instant { year of 1990 }
            end of instant { year of 1990 }
        }
        assertEquals("id", appt.id?.value)
        assertEquals(1, appt.identifier.size)
        assertEquals("status", appt.status?.value)
        assertEquals(1, appt.participant.size)
        assertEquals(10, appt.minutesDuration?.value)
        assertTrue(appt.start?.value?.startsWith("1990")!!)
        assertTrue(appt.end?.value?.startsWith("1990")!!)
    }
}
