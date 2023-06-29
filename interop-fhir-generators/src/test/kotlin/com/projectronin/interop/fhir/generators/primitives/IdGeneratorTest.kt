package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class IdGeneratorTest {
    @Test
    fun `function works with default`() {
        val id = id {}
        assertNotNull(id)
        assertNotNull(id.value)
        assertNull(id.id)
        assertEquals(0, id.extension.size)
    }

    @Test
    fun `function works with parameters`() {
        val id = id {
            value of "abc-123"
        }
        assertEquals("abc-123", id.value)
    }

    @Test
    fun `nullable Id using String-based infix setter`() {
        val generator: DataGenerator<Id?> = NullDataGenerator()
        generator of "id"
        val string = generator.generate()
        assertEquals(Id("id"), string)
    }

    @Test
    fun `non-nullable Id`() {
        val generator: DataGenerator<Id> = IdGenerator()
        generator of Id("id")
        val string = generator.generate()
        assertEquals(Id("id"), string)
    }
}
