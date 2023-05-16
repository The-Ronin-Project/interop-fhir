package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.codeableConcept
import com.projectronin.interop.fhir.generators.datatypes.coding
import com.projectronin.interop.fhir.generators.datatypes.encounterParticipant
import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.generators.datatypes.period
import com.projectronin.interop.fhir.generators.datatypes.reference
import com.projectronin.interop.fhir.generators.primitives.dateTime
import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class EncounterGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val encounter = encounter {}
        assertNull(encounter.id)
        assertNull(encounter.meta)
        assertNull(encounter.implicitRules)
        assertNull(encounter.language)
        assertNull(encounter.text)
        assertEquals(0, encounter.contained.size)
        assertEquals(0, encounter.extension.size)
        assertEquals(0, encounter.modifierExtension.size)
        assertTrue(encounter.identifier.isEmpty())
        assertNull(encounter.status)
        assertNotNull(encounter.period)
        assertNotNull(encounter.`class`)
        assertNotNull(encounter.subject)
        assertTrue(encounter.participant.isEmpty())
        assertTrue(encounter.type.isEmpty())
    }

    @Test
    fun `function works with parameters`() {
        val encounter = encounter {
            id of Id("id")
            identifier of listOf(
                identifier {}
            )
            status of "status"
            participant of listOf(
                encounterParticipant {}
            )

            `class` of coding { code of "coding" }
            period of period {
                start of dateTime { year of 1990 }
                end of dateTime { year of 1990 }
            }
            subject of reference("Patient", "123")
            type of listOf(
                codeableConcept { text of "type" }
            )
        }
        assertEquals("id", encounter.id?.value)
        assertEquals(1, encounter.identifier.size)
        assertEquals("status", encounter.status?.value)
        assertEquals(1, encounter.participant.size)
        assertEquals("coding", encounter.`class`?.code?.value)
        assertEquals("Patient/123", encounter.subject?.reference?.value)
        assertEquals("type", encounter.type.first().text?.value)
        assertTrue(encounter.period?.start?.value?.startsWith("1990")!!)
        assertTrue(encounter.period?.end?.value?.startsWith("1990")!!)
    }
}
