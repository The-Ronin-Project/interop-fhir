package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class FHIRIntegerDataGeneratorTest {
    @Test
    fun `generates null by default`() {
        val generator = FHIRIntegerDataGenerator()
        val boolean = generator.generate()
        assertNull(boolean)
    }

    @Test
    fun `supports infix extension for Int`() {
        val generator = FHIRIntegerDataGenerator()
        generator of 5
        val integer = generator.generate()
        assertEquals(FHIRInteger(5), integer)
    }

    @Test
    fun `supports infix setter for FHIRInteger`() {
        val generator = FHIRIntegerDataGenerator()
        generator of FHIRInteger(5)
        val integer = generator.generate()
        assertEquals(FHIRInteger(5), integer)
    }
}
