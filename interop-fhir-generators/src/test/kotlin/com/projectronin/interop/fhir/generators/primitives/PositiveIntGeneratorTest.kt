package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PositiveIntGeneratorTest {
    @Test
    fun `generic PositiveInt String-based infix setter works`() {
        val generator: DataGenerator<PositiveInt?> = NullDataGenerator()
        generator of 12
        val positiveInt = generator.generate()
        assertEquals(PositiveInt(12), positiveInt)
    }

    @Test
    fun `generic PositiveInt String-based infix setter does not allow 0 value`() {
        val generator: DataGenerator<PositiveInt?> = NullDataGenerator()
        val exception = assertThrows<IllegalArgumentException> { generator of 0 }
        assertEquals("0 is not positive", exception.message)
    }

    @Test
    fun `generic PositiveInt String-based infix setter does not allow negative value`() {
        val generator: DataGenerator<PositiveInt?> = NullDataGenerator()
        val exception = assertThrows<IllegalArgumentException> { generator of -1 }
        assertEquals("-1 is not positive", exception.message)
    }
}
