package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.Base64Binary
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.util.Base64

class Base64BinaryDataGeneratorTest {
    @Test
    fun `generates non-null value by default`() {
        val generator = Base64BinaryDataGenerator()
        val boolean = generator.generate()
        assertNotNull(boolean)
    }

    @Test
    fun `supports infix extension for String`() {
        val generator = Base64BinaryDataGenerator()
        generator of "Hello World"
        val string = generator.generate()
        assertEquals(Base64Binary(Base64.getEncoder().encodeToString("Hello World".toByteArray())), string)
    }

    @Test
    fun `supports infix setter for Base64Binary with given string`() {
        val generator = Base64BinaryDataGenerator()
        generator of Base64Binary("Goodbye World")
        val string = generator.generate()
        assertEquals(Base64Binary("Goodbye World"), string)
    }

    @Test
    fun `supports infix setter for Base64Binary with given size`() {
        val generator: DataGenerator<Base64Binary?> = NullDataGenerator()
        generator ofLength 123
        val base64 = generator.generate()
        val stringLen = base64?.value?.length
        assertEquals(123, stringLen)
    }
}
