package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.EncounterDiagnosis
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4EncounterDiagnosisValidatorTest {
    @Test
    fun `validate - succeeds with only required attributes`() {
        val encounterDiagnosis = EncounterDiagnosis(
            condition = Reference(reference = FHIRString("Condition/example"))
        )
        R4EncounterDiagnosisValidator.validate(encounterDiagnosis).alertIfErrors()
    }

    @Test
    fun `validate - succeeds with required and other attributes`() {
        val encounterDiagnosis = EncounterDiagnosis(
            condition = Reference(reference = FHIRString("Condition/example")),
            use = CodeableConcept(
                coding = listOf(
                    Coding(
                        system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                        code = Code("DD"),
                        display = FHIRString("Discharge diagnosis")
                    )
                )
            )
        )
        R4EncounterDiagnosisValidator.validate(encounterDiagnosis).alertIfErrors()
    }

    @Test
    fun `validate - fails for missing condition`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounterDiagnosis = EncounterDiagnosis(
                    condition = null,
                    use = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                                code = Code("DD"),
                                display = FHIRString("Discharge diagnosis")
                            )
                        )
                    )
                )
                R4EncounterDiagnosisValidator.validate(encounterDiagnosis).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: condition is a required element @ EncounterDiagnosis.condition",
            exception.message
        )
    }
}
