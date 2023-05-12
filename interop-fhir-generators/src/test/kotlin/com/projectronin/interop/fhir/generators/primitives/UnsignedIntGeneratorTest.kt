package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UnsignedIntGeneratorTest {
    @Test
    fun `generates null by default`() {
        val generator = UnsignedIntGenerator()
        val string = generator.generate()
        assertNull(string)
    }

    @Test
    fun `supports infix setter for Int`() {
        val generator = UnsignedIntGenerator()
        generator of 10
        val value = generator.generate()
        assertEquals(UnsignedInt(10), value)
    }

    @Test
    fun `supports infix setter for UnsignedInt`() {
        val generator = UnsignedIntGenerator()
        generator of UnsignedInt(25)
        val value = generator.generate()
        assertEquals(UnsignedInt(25), value)
    }

    @Test
    fun `allows 0 value`() {
        val generator = UnsignedIntGenerator()
        generator of 0
        val value = generator.generate()
        assertEquals(UnsignedInt(0), value)
    }

    @Test
    fun `does not allow negative value`() {
        val generator = UnsignedIntGenerator()
        val exception = assertThrows<IllegalArgumentException> { generator of -1 }
        assertEquals("value cannot be negative", exception.message)
    }
}
