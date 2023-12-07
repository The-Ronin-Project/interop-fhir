package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class AttachmentGeneratorTest {
    @Test
    fun `function works with default`() {
        val attachment = attachment { }
        assertNull(attachment.id)
        assertEquals(0, attachment.extension.size)
        assertNull(attachment.contentType)
        assertNull(attachment.language)
        assertNull(attachment.data)
        assertNull(attachment.url)
        assertNull(attachment.size)
        assertNull(attachment.hash)
        assertNull(attachment.title)
        assertNull(attachment.creation)
    }

    @Test
    fun `function works with parameters`() {
        val attachment =
            attachment {
                language of "english"
            }
        assertEquals(Code("english"), attachment.language)
    }
}
