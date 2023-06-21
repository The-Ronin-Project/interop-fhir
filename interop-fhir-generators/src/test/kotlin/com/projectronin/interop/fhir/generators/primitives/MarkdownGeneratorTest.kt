package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class MarkdownGeneratorTest {
    @Test
    fun `function works with default`() {
        val markdown = markdown {}
        assertNotNull(markdown)
        assertNotNull(markdown.value)
        assertNull(markdown.id)
        assertEquals(0, markdown.extension.size)
    }

    @Test
    fun `function works with parameters`() {
        val markdown = markdown {
            value of "# header"
        }
        assertEquals("# header", markdown.value)
    }

    @Test
    fun `generic Markdown String-based infix setter`() {
        val generator: DataGenerator<Markdown?> = NullDataGenerator()
        generator of "value"
        val markdown = generator.generate()
        assertEquals(Markdown("value"), markdown)
    }
}
