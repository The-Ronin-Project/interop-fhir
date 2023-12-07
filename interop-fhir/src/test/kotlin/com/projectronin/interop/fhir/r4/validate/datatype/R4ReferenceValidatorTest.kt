package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@Suppress("ktlint:standard:max-line-length")
class R4ReferenceValidatorTest {
    @Test
    fun `fails if at least one of reference, identifier and display is not be present`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val reference = Reference()
                R4ReferenceValidator.validate(reference).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_REF_001: At least one of reference, identifier and display SHALL be present (unless an extension is provided) @ Reference",
            exception.message,
        )
    }

    @Test
    fun `validates successfully`() {
        val reference = Reference(type = Uri("Patient"), display = FHIRString("any"))
        R4ReferenceValidator.validate(reference).alertIfErrors()
    }
}
