package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Qualification
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4QualificationValidatorTest {
    @Test
    fun `fails if no code provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val qualification = Qualification(
                code = null
            )
            R4QualificationValidator.validate(qualification).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: code is a required element @ Qualification.code",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val qualification = Qualification(
            code = CodeableConcept(text = FHIRString("code"))
        )
        R4QualificationValidator.validate(qualification).alertIfErrors()
    }
}
