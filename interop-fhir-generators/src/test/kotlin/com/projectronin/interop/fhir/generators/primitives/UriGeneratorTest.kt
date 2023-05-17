package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class UriGeneratorTest {
    @Test
    fun `generator works`() {
        val generator = UriGenerator()
        val uri = generator.generate()
        assertNotNull(uri)
        assertNotNull(uri.value)
    }

    @Test
    fun `infix works`() {
        val uri = UriGenerator()
        uri of "123"
        assertEquals("123", uri.generate().value)
    }

    @Test
    fun `generic Uri String-based infix setter`() {
        val generator: DataGenerator<Uri?> = NullDataGenerator()
        generator of "value"
        val uri = generator.generate()
        assertEquals(Uri("value"), uri)
    }
}
