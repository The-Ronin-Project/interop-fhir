package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.resource.Binary
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4BinaryValidatorTest {
    @Test
    fun `fails if no contentType`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                R4BinaryValidator.validate(
                    Binary(
                        contentType = null,
                    ),
                ).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: contentType is a required element @ Binary.contentType",
            exception.message,
        )
    }

    @Test
    fun `validates successfully`() {
        R4BinaryValidator.validate(
            Binary(
                contentType = Code("text/html"),
            ),
        ).alertIfErrors()
    }
}
