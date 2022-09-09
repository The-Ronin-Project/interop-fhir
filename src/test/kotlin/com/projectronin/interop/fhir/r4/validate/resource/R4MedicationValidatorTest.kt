package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.resource.Medication
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class R4MedicationValidatorTest {
    @Test
    fun `passes validation`() {
        val medication = Medication()
        R4MedicationValidator.validate(medication).alertIfErrors()
    }

    @Test
    fun `fails on bad medication status`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4MedicationValidator.validate(
                Medication(
                    status = Code("unsupported-value")
                )
            ).alertIfErrors()
        }

        Assertions.assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: status is outside of required value set @ Medication.status",
            exception.message
        )
    }
}
