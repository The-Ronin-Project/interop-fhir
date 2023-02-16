package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.Signature
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4SignatureValidatorTest {
    @Test
    fun `fails if when is not provided`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val signature = Signature(
                    type = listOf(Coding(display = FHIRString("coding-type"))),
                    `when` = null,
                    who = Reference(display = FHIRString("Reference"))
                )
                R4SignatureValidator.validate(signature).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: when is a required element @ Signature.when",
            exception.message
        )
    }

    @Test
    fun `fails if who is not provided`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val signature = Signature(
                    type = listOf(Coding(display = FHIRString("coding-type"))),
                    `when` = Instant("2017-01-01T00:00:00Z"),
                    who = null
                )
                R4SignatureValidator.validate(signature).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: who is a required element @ Signature.who",
            exception.message
        )
    }

    @Test
    fun `fails if no types provided`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val signature = Signature(
                    type = listOf(),
                    `when` = Instant("2017-01-01T00:00:00Z"),
                    who = Reference(display = FHIRString("Reference"))
                )
                R4SignatureValidator.validate(signature).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_SIG_001: The Signature SHALL include a CommitmentTypeIndication for the Purpose(s) of Signature @ Signature.type",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val signature = Signature(
            type = listOf(Coding(display = FHIRString("coding-type"))),
            `when` = Instant("2017-01-01T00:00:00Z"),
            who = Reference(display = FHIRString("Reference"))
        )
        R4SignatureValidator.validate(signature).alertIfErrors()
    }
}
