package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class IdentifierGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val identifier = identifier { }
        assertNull(identifier.use)
        assertNotNull(identifier.type)
        assertNotNull(identifier.system)
        assertNotNull(identifier.value)
        assertNull(identifier.period)
        assertNull(identifier.assigner)
    }

    @Test
    fun `function works with parameters`() {
        val identifier = identifier {
            use of Code("code")
            type of codeableConcept { }
            system of "system"
            value of "value"
            period of period { }
            assigner of reference("Patient", "123")
        }
        assertNotNull("code", identifier.use?.value)
        assertNotNull(identifier.type)
        assertEquals("system", identifier.system?.value)
        assertNotNull(identifier.value)
        assertEquals("value", identifier.value?.value)
        assertNotNull(identifier.period)
        assertEquals("Patient/123", identifier.assigner?.reference?.value)
    }

    @Test
    fun `external identifier works`() {
        val identifier = externalIdentifier {
            type of codeableConcept { text of "overridden" }
        }
        assertEquals("External", identifier.type?.text?.value)
    }

    @Test
    fun `internal identifier works`() {
        val identifier = internalIdentifier {
            type of codeableConcept { text of "overridden" }
        }
        assertEquals("Internal", identifier.type?.text?.value)
    }
}
