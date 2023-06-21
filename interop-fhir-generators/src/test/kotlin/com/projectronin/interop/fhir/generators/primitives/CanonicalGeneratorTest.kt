package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class CanonicalGeneratorTest {
    @Test
    fun `generator works`() {
        val generator = CanonicalGenerator()
        val canonical = generator.generate()
        assertNotNull(canonical)
        assertNotNull(canonical.value)
    }

    @Test
    fun `infix works`() {
        val canonical = CanonicalGenerator()
        canonical of Canonical("123")
        assertEquals("123", canonical.generate().value)
    }

    @Test
    fun `generic Canonical String-based infix setter`() {
        val generator: DataGenerator<Canonical?> = NullDataGenerator()
        generator of "value"
        val canonical = generator.generate()
        assertEquals(Canonical("value"), canonical)
    }
}
