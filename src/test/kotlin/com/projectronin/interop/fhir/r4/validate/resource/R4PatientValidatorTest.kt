package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.resource.Patient
import com.projectronin.interop.fhir.r4.valueset.AdministrativeGender
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4PatientValidatorTest {
    @Test
    fun `gender is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val patient = Patient(
                gender = Code("unsupported-gender")
            )
            R4PatientValidator.validate(patient).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-gender' is outside of required value set @ Patient.gender",
            exception.message
        )
    }

    @Test
    fun `deceased can only be one of the following data types`() {
        val exception = assertThrows<IllegalArgumentException> {
            val patient = Patient(
                deceased = DynamicValue(type = DynamicValueType.BASE_64_BINARY, value = false)
            )
            R4PatientValidator.validate(patient).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: deceased can only be one of the following: Boolean, DateTime @ Patient.deceased",
            exception.message
        )
    }

    @Test
    fun `multipleBirth can only be one of the following data types`() {
        val exception = assertThrows<IllegalArgumentException> {
            val patient = Patient(
                multipleBirth = DynamicValue(type = DynamicValueType.BASE_64_BINARY, value = 2)
            )
            R4PatientValidator.validate(patient).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: multipleBirth can only be one of the following: Boolean, Integer @ Patient.multipleBirth",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val patient = Patient(
            gender = AdministrativeGender.FEMALE.asCode()
        )
        R4PatientValidator.validate(patient).alertIfErrors()
    }
}
