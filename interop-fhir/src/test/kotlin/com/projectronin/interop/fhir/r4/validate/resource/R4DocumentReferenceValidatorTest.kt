package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.Attachment
import com.projectronin.interop.fhir.r4.datatype.primitive.Base64Binary
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.resource.DocumentReference
import com.projectronin.interop.fhir.r4.resource.DocumentReferenceContent
import com.projectronin.interop.fhir.r4.valueset.CompositionStatus
import com.projectronin.interop.fhir.r4.valueset.DocumentReferenceStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4DocumentReferenceValidatorTest {
    @Test
    fun `fails if no status`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4DocumentReferenceValidator.validate(
                DocumentReference(
                    status = null,
                    content = listOf(
                        DocumentReferenceContent(
                            attachment = Attachment(data = Base64Binary("c3po"), contentType = Code("plain/text"))
                        )
                    )
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ DocumentReference.status",
            exception.message
        )
    }

    @Test
    fun `fails if status outside value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4DocumentReferenceValidator.validate(
                DocumentReference(
                    status = Code("unknown-value"),
                    content = listOf(
                        DocumentReferenceContent(
                            attachment = Attachment(data = Base64Binary("c3po"), contentType = Code("plain/text"))
                        )
                    )
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unknown-value' is outside of required value set @ DocumentReference.status",
            exception.message
        )
    }

    @Test
    fun `fails if docStatus outside value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4DocumentReferenceValidator.validate(
                DocumentReference(
                    status = DocumentReferenceStatus.CURRENT.asCode(),
                    docStatus = Code("unknown-value"),
                    content = listOf(
                        DocumentReferenceContent(
                            attachment = Attachment(data = Base64Binary("c3po"), contentType = Code("plain/text"))
                        )
                    )
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unknown-value' is outside of required value set @ DocumentReference.docStatus",
            exception.message
        )
    }

    @Test
    fun `fails if no content`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4DocumentReferenceValidator.validate(
                DocumentReference(
                    status = DocumentReferenceStatus.CURRENT.asCode(),
                    content = listOf()
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: content is a required element @ DocumentReference.content",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        R4DocumentReferenceValidator.validate(
            DocumentReference(
                status = DocumentReferenceStatus.CURRENT.asCode(),
                docStatus = CompositionStatus.AMENDED.asCode(),
                content = listOf(
                    DocumentReferenceContent(
                        attachment = Attachment(data = Base64Binary("c3po"), contentType = Code("plain/text"))
                    )
                )
            )
        ).alertIfErrors()
    }
}
