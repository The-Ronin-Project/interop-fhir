package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.resource.Medication
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class R4MedicationValidatorTest {
    @Test
    fun `validate - succeeds with empty object`() {
        val medication = Medication()
        R4MedicationValidator.validate(medication).alertIfErrors()
    }

    @Test
    fun `validate - fails on bad medication status`() {
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

    @Test
    fun `validate - succeeds with empty ingredient list`() {
        R4MedicationValidator.validate(
            Medication(
                code = CodeableConcept(
                    text = FHIRString("b"),
                    coding = listOf(
                        Coding(
                            system = CodeSystem.RXNORM.uri,
                            code = Code("b"),
                            version = FHIRString("1.0.0"),
                            display = FHIRString("b")
                        )
                    )
                ),
                ingredient = listOf()
            )
        ).alertIfErrors()
    }

    @Test
    fun `validate - succeeds with no ingredient list`() {
        R4MedicationValidator.validate(
            Medication(
                code = CodeableConcept(
                    text = FHIRString("b"),
                    coding = listOf(
                        Coding(
                            system = CodeSystem.RXNORM.uri,
                            code = Code("b"),
                            version = FHIRString("1.0.0"),
                            display = FHIRString("b")
                        )
                    )
                )
            )
        ).alertIfErrors()
    }
}
