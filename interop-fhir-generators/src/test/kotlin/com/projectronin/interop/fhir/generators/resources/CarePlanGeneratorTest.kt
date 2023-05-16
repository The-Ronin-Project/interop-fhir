package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.codeableConcept
import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.generators.datatypes.reference
import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CarePlanGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val carePlan = carePlan {}
        assertNull(carePlan.id)
        assertNull(carePlan.meta)
        assertNull(carePlan.implicitRules)
        assertNull(carePlan.language)
        assertNull(carePlan.text)
        assertEquals(0, carePlan.contained.size)
        assertEquals(0, carePlan.extension.size)
        assertEquals(0, carePlan.modifierExtension.size)
        assertTrue(carePlan.identifier.isEmpty())
        assertNull(carePlan.status)
        assertNull(carePlan.intent)
        assertTrue(carePlan.category.isEmpty())
        assertNotNull(carePlan.subject)
    }

    @Test
    fun `function works with parameters`() {
        val carePlan = carePlan {
            id of Id("id")
            identifier of listOf(
                identifier {
                    value of "identifier"
                }
            )
            status of "status"
            intent of "intent"
            subject of reference("Patient", "123")
            category of listOf(codeableConcept {})
        }

        assertEquals("id", carePlan.id?.value)
        assertEquals(1, carePlan.identifier.size)
        assertEquals("identifier", carePlan.identifier.first().value?.value)
        assertEquals("status", carePlan.status?.value)
        assertEquals("intent", carePlan.intent?.value)
        assertEquals("Patient/123", carePlan.subject?.reference?.value)
        assertEquals(1, carePlan.category.size)
    }
}
