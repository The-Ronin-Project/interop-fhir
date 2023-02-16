package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.r4.datatype.Period
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ParticipantGeneratorTest {
    @Test
    fun `function works with default`() {
        val participant = participant {}
        assertNotNull(participant.type)
        assertNotNull(participant.actor)
        assertNull(participant.required)
        assertNull(participant.status)
        assertNull(participant.period)
    }

    @Test
    fun `function works with parameters`() {
        val participant = participant {
            type of listOf(codeableConcept { })
            actor of reference("Patient", "123")
            required of "required"
            status of "status"
            period of Period()
        }
        assertNotNull(participant.type)
        assertEquals("Patient/123", participant.actor?.reference?.value)
        assertEquals("required", participant.required?.value)
        assertEquals("status", participant.status?.value)
        assertNotNull(participant.period)
    }
}
