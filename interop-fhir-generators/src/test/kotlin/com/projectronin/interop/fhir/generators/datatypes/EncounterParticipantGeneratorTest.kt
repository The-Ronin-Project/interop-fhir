package com.projectronin.interop.fhir.generators.datatypes

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class EncounterParticipantGeneratorTest {
    @Test
    fun `function works with default`() {
        val participant = encounterParticipant {}
        assertNull(participant.id)
        assertEquals(0, participant.extension.size)
        assertEquals(0, participant.modifierExtension.size)
        assertEquals(0, participant.type.size)
        assertNull(participant.period)
        assertNotNull(participant.individual)
    }

    @Test
    fun `function works with parameters`() {
        val participant = encounterParticipant {
            type of listOf(codeableConcept { })
            individual of reference("Patient", "123")
            period of period { }
        }
        assertEquals(1, participant.type.size)
        assertEquals("Patient/123", participant.individual?.reference?.value)
        assertNotNull(participant.period)
    }
}
