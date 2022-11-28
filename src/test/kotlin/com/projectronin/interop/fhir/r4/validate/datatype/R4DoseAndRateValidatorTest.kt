package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DoseAndRate
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4DoseAndRateValidatorTest {
    @Test
    fun `fails for unsupported dose dynamic value type`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val doseAndRate = DoseAndRate(dose = DynamicValue(DynamicValueType.INTEGER, FHIRInteger(1)))
                R4DoseAndRateValidator.validate(doseAndRate).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: dose can only be one of the following: Range, Quantity @ DoseAndRate.dose",
            exception.message
        )
    }

    @Test
    fun `fails for unsupported rate dynamic value type`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val doseAndRate = DoseAndRate(rate = DynamicValue(DynamicValueType.INTEGER, FHIRInteger(1)))
                R4DoseAndRateValidator.validate(doseAndRate).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: rate can only be one of the following: Ratio, Range, Quantity @ DoseAndRate.rate",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val doseAndRate = DoseAndRate(
            type = CodeableConcept(text = FHIRString("dose and rate type"))
        )
        R4DoseAndRateValidator.validate(doseAndRate).alertIfErrors()
    }
}
