package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Date
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.resource.Practitioner
import com.projectronin.interop.fhir.r4.resource.Qualification
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4PractitionerValidatorTest {
    @Test
    fun `gender is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val practitioner = Practitioner(
                gender = Code("unsupported-gender")
            )
            R4PractitionerValidator.validate(practitioner).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-gender' is outside of required value set @ Practitioner.gender",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val practitioner = Practitioner(
            birthDate = Date("1936-12-25")
        )
        R4PractitionerValidator.validate(practitioner).alertIfErrors()
    }
}

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
