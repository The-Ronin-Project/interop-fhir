package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.Attachment
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Base64Binary
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.resource.DocumentReference
import com.projectronin.interop.fhir.r4.resource.DocumentReferenceContent
import com.projectronin.interop.fhir.r4.resource.DocumentReferenceRelatesTo
import com.projectronin.interop.fhir.r4.valueset.CompositionStatus
import com.projectronin.interop.fhir.r4.valueset.DocumentReferenceStatus
import com.projectronin.interop.fhir.r4.valueset.DocumentRelationshipType
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

class R4DocumentReferenceRelatesToValidatorTest {
    @Test
    fun `fails if no code`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4DocumentReferenceRelatesToValidator.validate(
                DocumentReferenceRelatesTo(
                    code = null,
                    target = Reference(display = FHIRString("target"))
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: code is a required element @ DocumentReferenceRelatesTo.code",
            exception.message
        )
    }

    @Test
    fun `fails if code outside value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4DocumentReferenceRelatesToValidator.validate(
                DocumentReferenceRelatesTo(
                    code = Code("unknown-value"),
                    target = Reference(display = FHIRString("target"))
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unknown-value' is outside of required value set @ DocumentReferenceRelatesTo.code",
            exception.message
        )
    }

    @Test
    fun `fails if no target`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4DocumentReferenceRelatesToValidator.validate(
                DocumentReferenceRelatesTo(
                    code = DocumentRelationshipType.APPENDS.asCode(),
                    target = null
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: target is a required element @ DocumentReferenceRelatesTo.target",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        R4DocumentReferenceRelatesToValidator.validate(
            DocumentReferenceRelatesTo(
                code = DocumentRelationshipType.APPENDS.asCode(),
                target = Reference(display = FHIRString("target"))
            )
        ).alertIfErrors()
    }
}
