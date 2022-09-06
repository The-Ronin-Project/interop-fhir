package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Dosage
import com.projectronin.interop.fhir.r4.datatype.DoseAndRate
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4DosageValidatorTest {
    @Test
    fun `fails for unsupported as needed dynamic value type`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val dosage = Dosage(asNeeded = DynamicValue(DynamicValueType.INTEGER, 1))
                R4DosageValidator.validate(dosage).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: asNeeded can only be one of the following: Boolean, CodeableConcept @ Dosage.asNeeded",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val dosage = Dosage(
            doseAndRate = listOf(
                DoseAndRate(
                    type = CodeableConcept(text = "dose and rate type")
                )
            )
        )
        R4DosageValidator.validate(dosage).alertIfErrors()
    }
}
