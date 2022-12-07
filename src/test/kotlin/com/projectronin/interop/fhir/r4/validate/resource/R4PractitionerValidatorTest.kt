package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Date
import com.projectronin.interop.fhir.r4.resource.Practitioner
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
