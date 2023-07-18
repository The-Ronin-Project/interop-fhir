package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.of
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class CodeableConceptGeneratorTest {
    @Test
    fun `function works with default`() {
        val codeableConcept = codeableConcept {}
        val coding = codeableConcept.coding
        assertNotNull(codeableConcept.coding)
        assertEquals(0, coding.size)
        assertNull(codeableConcept.text)
    }

    @Test
    fun `function works with parameters`() {
        val codeableConcept = codeableConcept {
            coding of listOf(
                coding { }
            )
            text of "text"
        }
        val coding = codeableConcept.coding
        assertNotNull(codeableConcept.coding)
        assertEquals(1, coding.size)
        assertEquals("text", codeableConcept.text?.value)
    }
}
