package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
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
                "ERROR INV_VALUE_SET: 'unsupported-use' is outside of required value set @ Identifier.use",
            exception.message
        )
    }

    @Test
    fun `fails if assigner is not an Organization`() {
        val identifier = Identifier(
            use = IdentifierUse.OFFICIAL.asCode(),
            assigner = Reference(reference = FHIRString("Patient/reference"))
        )
        val validation = R4IdentifierValidator.validate(identifier)

        val issues = validation.issues()
        assertEquals(1, issues.size)
        assertEquals(
            "WARNING INV_REF_TYPE: reference can only be one of the following: Organization @ Identifier.assigner.reference",
            issues.first().toString()
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
