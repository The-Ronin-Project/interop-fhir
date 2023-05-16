package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ConceptMapGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val conceptMap = conceptMap {}
        assertNull(conceptMap.id)
        assertNull(conceptMap.meta)
        assertNull(conceptMap.implicitRules)
        assertNull(conceptMap.language)
        assertNull(conceptMap.text)
        assertEquals(0, conceptMap.contained.size)
        assertEquals(0, conceptMap.extension.size)
        assertEquals(0, conceptMap.modifierExtension.size)
        assertNull(conceptMap.identifier)
        assertNull(conceptMap.status)
    }

    @Test
    fun `function works with parameters`() {
        val conceptMap = conceptMap {
            id of Id("id")
            identifier of listOf(
                identifier {
                    value of "identifier"
                }
            )
            status of "status"
        }

        assertEquals("id", conceptMap.id?.value)
        assertEquals("identifier", conceptMap.identifier?.value?.value)
        assertEquals("status", conceptMap.status?.value)
    }
}
