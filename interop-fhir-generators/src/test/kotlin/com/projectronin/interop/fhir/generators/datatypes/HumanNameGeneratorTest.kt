package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class HumanNameGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val name = name { }
        assertNull(name.use)
        assertNull(name.text)
        assertNotNull(name.family)
        assertNotNull(name.given)
        assertEquals(1, name.given.size)
        assertEquals(0, name.prefix.size)
        assertEquals(0, name.suffix.size)
        assertNull(name.period)
    }

    @Test
    fun `function works with parameters`() {
        val name = name {
            use of Code("use")
            text of "text"
            family of "Felt"
            given of listOf("Sam")
            prefix of listOf("Dr")
            suffix of listOf("IV")
            period of Period()
        }
        assertEquals("use", name.use?.value)
        assertEquals("text", name.text?.value)
        assertEquals("Felt", name.family?.value)
        assertEquals(1, name.given.size)
        assertEquals("Sam", name.given.first().value)
        assertEquals(1, name.prefix.size)
        assertEquals("Dr", name.prefix.first().value)
        assertEquals(1, name.suffix.size)
        assertEquals("IV", name.suffix.first().value)
        assertNotNull(name.period)
    }
}
