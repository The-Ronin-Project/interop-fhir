package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4AnnotationValidatorTest {
    @Test
    fun `fails if no status provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val annotation = Annotation(text = null)
            R4AnnotationValidator.validate(annotation).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: text is a required element @ Annotation.text",
            exception.message
        )
    }

    @Test
    fun `fails for unsupported author dynamic value type`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val annotation = Annotation(
                    author = DynamicValue(DynamicValueType.INTEGER, 1),
                    text = Markdown("text")
                )
                R4AnnotationValidator.validate(annotation).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: author can only be one of the following: Reference, String @ Annotation.author",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val annotation = Annotation(text = Markdown("Markdown text"))
        R4AnnotationValidator.validate(annotation).alertIfErrors()
    }
}
