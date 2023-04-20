package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.codeableConcept
import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.generators.datatypes.reference
import com.projectronin.interop.fhir.generators.primitives.dateTime
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ObservationGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val observation = observation {}
        assertNull(observation.id)
        assertNull(observation.meta)
        assertNull(observation.implicitRules)
        assertNull(observation.language)
        assertNull(observation.text)
        assertEquals(0, observation.contained.size)
        assertEquals(0, observation.extension.size)
        assertEquals(0, observation.modifierExtension.size)
        assertTrue(observation.identifier.isEmpty())
        assertNull(observation.status)
        assertTrue(observation.category.isEmpty())
        assertNotNull(observation.code)
        assertNotNull(observation.subject)
        assertNotNull(observation.effective)
        println(observation.effective)
        assertEquals(observation.effective?.type, DynamicValueType.DATE_TIME)
    }

    @Test
    fun `function works with parameters`() {
        val observation = observation {
            id of Id("id")
            identifier of listOf(
                identifier {}
            )
            status of "status"
            category of listOf(codeableConcept {})
            code of codeableConcept { text of "code" }
            subject of reference("Patient", "123")
            effective of dateTime { year of 1990 }
        }
        assertEquals("id", observation.id?.value)
        assertEquals(1, observation.identifier.size)
        assertEquals("status", observation.status?.value)
        assertEquals(1, observation.category.size)
        assertEquals("code", observation.code?.text?.value)
        assertNotNull(observation.code)
        val effective = observation.effective?.value as String
        assertTrue(effective.startsWith("1990"))
    }
}
