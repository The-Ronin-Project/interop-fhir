package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class FHIRBooleanDataGeneratorTest {
    @Test
    fun `generates null by default`() {
        val generator = FHIRBooleanDataGenerator()
        val boolean = generator.generate()
        assertNull(boolean)
    }

    @Test
    fun `supports infix extension for Boolean`() {
        val generator = FHIRBooleanDataGenerator()
        generator of true
        val boolean = generator.generate()
        assertEquals(FHIRBoolean.TRUE, boolean)
    }

    @Test
    fun `supports infix setter for FHIRBoolean`() {
        val generator = FHIRBooleanDataGenerator()
        generator of FHIRBoolean.FALSE
        val boolean = generator.generate()
        assertEquals(FHIRBoolean.FALSE, boolean)
    }
}
