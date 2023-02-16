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
        assertTrue(appt.identifier.isEmpty())
        assertNull(appt.status)
        assertNull(appt.cancelationReason)
        assertTrue(appt.participant.isEmpty())
        assertNotNull(appt.minutesDuration)
        assertNotNull(appt.start)
        assertNotNull(appt.end)
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
