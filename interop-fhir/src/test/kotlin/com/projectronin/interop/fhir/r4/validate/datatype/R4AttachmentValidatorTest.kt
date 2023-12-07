package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Attachment
import com.projectronin.interop.fhir.r4.datatype.primitive.Base64Binary
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4AttachmentValidatorTest {
    @Test
    fun `fails if data provided without content type`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val attachment = Attachment(data = Base64Binary("abcd"))
                R4AttachmentValidator.validate(attachment).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_ATTACH_001: If the Attachment has data, it SHALL have a contentType @ Attachment",
            exception.message,
        )
    }

    @Test
    fun `validates successfully`() {
        val attachment =
            Attachment(
                title = FHIRString("Empty"),
            )
        R4AttachmentValidator.validate(attachment).alertIfErrors()
    }
}
