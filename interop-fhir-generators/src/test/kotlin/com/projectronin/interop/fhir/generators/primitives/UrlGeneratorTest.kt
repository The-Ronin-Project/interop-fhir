package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.Url
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class UrlGeneratorTest {
    @Test
    fun `generator works`() {
        val generator = UrlGenerator()
        val url = generator.generate()
        assertNotNull(url)
        assertNotNull(url.value)
    }

    @Test
    fun `infix works`() {
        val url = UrlGenerator()
        url of Url("123")
        assertEquals("123", url.generate().value)
    }

    @Test
    fun `generic Url String-based infix setter`() {
        val generator: DataGenerator<Url?> = NullDataGenerator()
        generator of "value"
        val url = generator.generate()
        assertEquals(Url("value"), url)
    }
}
