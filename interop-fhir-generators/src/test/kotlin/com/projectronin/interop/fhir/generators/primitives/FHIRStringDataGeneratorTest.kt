package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FHIRStringDataGeneratorTest {
    @Test
    fun `generic FHIRString String-based infix setter`() {
        val generator: DataGenerator<FHIRString?> = NullDataGenerator()
        generator of "value"
        val string = generator.generate()
        assertEquals(FHIRString("value"), string)
    }
}
