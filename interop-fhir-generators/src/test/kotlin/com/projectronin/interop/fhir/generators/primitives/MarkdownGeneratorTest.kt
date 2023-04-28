package com.projectronin.interop.fhir.generators.primitives

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
}
