package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.r4.datatype.Period
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class EncounterParticipantGeneratorTest {
    @Test
    fun `function works with default`() {
        val participant = encounterParticipant {}
        assertNotNull(participant.type)
        assertNull(participant.period)
        assertNotNull(participant.individual)
    }

    @Test
    fun `function works with parameters`() {
        val participant = encounterParticipant {
            type of listOf(codeableConcept { })
            individual of reference("Patient", "123")
            required of "required"
            period of Period()
        }
        assertNotNull(participant.type)
        assertEquals("Patient/123", participant.individual?.reference?.value)
        assertNotNull(participant.period)
    }
}
