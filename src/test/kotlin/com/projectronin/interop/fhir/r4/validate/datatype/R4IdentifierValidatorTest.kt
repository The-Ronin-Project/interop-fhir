package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.IdentifierUse
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4IdentifierValidatorTest {
    @Test
    fun `fails if use is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val identifier = Identifier(
                use = Code("unsupported-use")
            )
            R4IdentifierValidator.validate(identifier).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: use is outside of required value set @ Identifier.use",
            exception.message
        )
    }

    @Test
    fun `fails if assigner is not an Organization`() {
        val exception = assertThrows<IllegalArgumentException> {
            val identifier = Identifier(
                use = IdentifierUse.OFFICIAL.asCode(),
                assigner = Reference(type = Uri("Patient"), reference = "reference")
            )
            R4IdentifierValidator.validate(identifier).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_IDENT_001: Identifier assigner reference must be to an Organization @ Identifier.assigner.type",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val identifier = Identifier(
            use = IdentifierUse.OFFICIAL.asCode()
        )
        R4IdentifierValidator.validate(identifier).alertIfErrors()
    }
}
