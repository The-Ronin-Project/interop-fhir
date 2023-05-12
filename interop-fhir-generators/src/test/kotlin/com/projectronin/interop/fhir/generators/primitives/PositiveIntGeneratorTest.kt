package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PositiveIntGeneratorTest {
    @Test
    fun `generates null by default`() {
        val generator = PositiveIntGenerator()
        val string = generator.generate()
        assertNull(string)
    }

    @Test
    fun `supports infix setter for Int`() {
        val generator = PositiveIntGenerator()
        generator of 10
        val value = generator.generate()
        assertEquals(PositiveInt(10), value)
    }

    @Test
    fun `supports infix setter for PositiveInt`() {
        val generator = PositiveIntGenerator()
        generator of PositiveInt(25)
        val value = generator.generate()
        assertEquals(PositiveInt(25), value)
    }

    @Test
    fun `does not allow 0 value`() {
        val generator = PositiveIntGenerator()
        val exception = assertThrows<IllegalArgumentException> { generator of 0 }
        assertEquals("0 is not positive", exception.message)
    }

    @Test
    fun `does not allow negative value`() {
        val generator = PositiveIntGenerator()
        val exception = assertThrows<IllegalArgumentException> { generator of -1 }
        assertEquals("-1 is not positive", exception.message)
    }
}
