package com.projectronin.interop.fhir.r4.validate.datatype.medication

import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.medication.Substitution
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4SubstitutionValidatorTest {
    @Test
    fun `fails if no allowed provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val substitution = Substitution(allowed = null)
            R4SubstitutionValidator.validate(substitution).alertIfErrors()
        }
        Assertions.assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: allowed is a required element @ Substitution.allowed",
            exception.message
        )
    }

    @Test
    fun `fails for unsupported allowed dynamic value type`() {
        val exception = assertThrows<IllegalArgumentException> {
            val substitution = Substitution(allowed = DynamicValue(DynamicValueType.STRING, "Not Allowed"))
            R4SubstitutionValidator.validate(substitution).alertIfErrors()
        }
        Assertions.assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: allowed can only be one of the following: Boolean, CodeableConcept @ Substitution.allowed",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val substitution = Substitution(allowed = DynamicValue(DynamicValueType.BOOLEAN, true))
        R4SubstitutionValidator.validate(substitution).alertIfErrors()
    }
}
