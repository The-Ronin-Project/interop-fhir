package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.RelatedArtifact
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.valueset.RelatedArtifactType
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4RelatedArtifactValidatorTest {
    @Test
    fun `fails if no type provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val relatedArtifact = RelatedArtifact(
                type = null
            )
            R4RelatedArtifactValidator.validate(relatedArtifact).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: type is a required element @ RelatedArtifact.type",
            exception.message
        )
    }

    @Test
    fun `fails if type is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val relatedArtifact = RelatedArtifact(
                type = Code("unsupported-type")
            )
            R4RelatedArtifactValidator.validate(relatedArtifact).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-type' is outside of required value set @ RelatedArtifact.type",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val relatedArtifact = RelatedArtifact(
            type = RelatedArtifactType.DOCUMENTATION.asCode()
        )
        R4RelatedArtifactValidator.validate(relatedArtifact).alertIfErrors()
    }
}
