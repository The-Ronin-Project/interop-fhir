package com.projectronin.interop.fhir.generators.primitives

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class IdGeneratorTest {
    @Test
    fun `function works with default`() {
        val id = id {}
        assertNotNull(id)
        assertNotNull(id.value)
        assertNull(id.id)
        assertEquals(0, id.extension.size)
    }

    @Test
    fun `function works with parameters`() {
        val id = id {
            value of "abc-123"
        }
        assertEquals("abc-123", id.value)
    }
}
