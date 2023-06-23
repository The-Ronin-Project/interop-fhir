package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.DynamicValues
import com.projectronin.interop.fhir.generators.datatypes.codeableConcept
import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.generators.datatypes.reference
import com.projectronin.interop.fhir.generators.primitives.dateTime
import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
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
        assertEquals(0, observation.identifier.size)
        assertEquals(0, observation.basedOn.size)
        assertEquals(0, observation.partOf.size)
        assertNotNull(observation.status)
        assertEquals(0, observation.category.size)
        assertNotNull(observation.code)
        assertNotNull(observation.subject)
        assertEquals(0, observation.focus.size)
        assertNull(observation.encounter)
        assertNull(observation.effective)
        assertNull(observation.issued)
        assertEquals(0, observation.performer.size)
        assertEquals(0, observation.note.size)
        assertNull(observation.value)
        assertNull(observation.dataAbsentReason)
        assertEquals(0, observation.interpretation.size)
        assertNull(observation.bodySite)
        assertNull(observation.method)
        assertNull(observation.specimen)
        assertNull(observation.device)
        assertEquals(0, observation.referenceRange.size)
        assertEquals(0, observation.hasMember.size)
        assertEquals(0, observation.derivedFrom.size)
        assertEquals(0, observation.component.size)
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
            effective of DynamicValues.dateTime(dateTime { year of 1990 })
        }
        assertEquals("id", observation.id?.value)
        assertEquals(1, observation.identifier.size)
        assertEquals("status", observation.status?.value)
        assertEquals(1, observation.category.size)
        assertEquals("code", observation.code?.text?.value)
        assertNotNull(observation.code)

        assertEquals(DynamicValueType.DATE_TIME, observation.effective?.type)
        val effective = observation.effective?.value as DateTime
        assertTrue(effective.value?.startsWith("1990") ?: false)
    }
}

class ObservationComponentGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val observationComponent = observationComponent {}
        assertNull(observationComponent.id)
        assertEquals(0, observationComponent.extension.size)
        assertEquals(0, observationComponent.modifierExtension.size)
        assertNotNull(observationComponent.code)
        assertNull(observationComponent.value)
        assertNull(observationComponent.dataAbsentReason)
        assertEquals(0, observationComponent.interpretation.size)
        assertEquals(0, observationComponent.referenceRange.size)
    }

    @Test
    fun `function works with parameters`() {
        val observationComponent = observationComponent {
            id of "id"
        }
        assertEquals("id", observationComponent.id?.value)
    }
}

class ObservationReferenceRangeGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val observationReferenceRange = observationReferenceRange {}
        assertNull(observationReferenceRange.id)
        assertEquals(0, observationReferenceRange.extension.size)
        assertEquals(0, observationReferenceRange.modifierExtension.size)
        assertNull(observationReferenceRange.low)
        assertNull(observationReferenceRange.high)
        assertNull(observationReferenceRange.text)
        assertEquals(0, observationReferenceRange.appliesTo.size)
        assertNull(observationReferenceRange.age)
        assertNull(observationReferenceRange.text)
    }

    @Test
    fun `function works with parameters`() {
        val observationReferenceRange = observationReferenceRange {
            text of "range"
        }
        assertEquals("range", observationReferenceRange.text?.value)
    }
}
