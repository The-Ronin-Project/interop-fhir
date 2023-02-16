package com.projectronin.interop.fhir.generators.datatypes

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CodingGeneratorTest {
    @Test
    fun `function works with default`() {
        val coding = coding {}
        assertNotNull(coding.system?.value)
        assertNull(coding.code)
        assertNull(coding.code?.value)
        assertNull(coding.version)
        assertNull(coding.display)
        assertNull(coding.userSelected)
    }

    @Test
    fun `function works with parameters`() {
        val coding = coding {
            system of "system"
            code of "code"
            version of "v1"
            display of "display"
            userSelected of true
        }
        assertNotNull(coding)
        assertEquals("system", coding.system?.value)
        assertEquals("code", coding.code?.value)
        assertEquals("v1", coding.version?.value)
        assertEquals("display", coding.display?.value)
        assertTrue(coding.userSelected?.value!!)
    }
}
