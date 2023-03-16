package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.Age
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.Condition
import com.projectronin.interop.fhir.r4.resource.ConditionEvidence
import com.projectronin.interop.fhir.r4.resource.ConditionStage
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4ConditionValidatorTest {
    @Test
    fun `subject is not provided`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val condition = Condition(
                    subject = null
                )
                R4ConditionValidator.validate(condition).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: subject is a required element @ Condition.subject",
            exception.message
        )
    }

    @Test
    fun `clinicalStatus must be valid code - bad clinicalStatus`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val condition = Condition(
                    identifier = listOf(
                        Identifier(
                            system = CodeSystem.HL7_IDENTIFIER_TYPE.uri,
                            value = FHIRString("MRN")
                        )
                    ),
                    clinicalStatus = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://terminology.hl7.org/CodeSystem/condition-clinical"),
                                code = Code("potato"),
                                display = FHIRString("Potato")
                            )
                        )
                    ),
                    category = listOf(
                        CodeableConcept(
                            coding = listOf(
                                Coding(
                                    code = Code("encounter-diagnosis")
                                )
                            ),
                            text = FHIRString("Encounter Diagnosis")
                        )
                    ),
                    code = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://snomed.info/sct"),
                                code = Code("254637007"),
                                display = FHIRString("Non-small cell lung cancer")
                            )
                        )
                    ),
                    subject = Reference(
                        reference = FHIRString("Patient/roninPatientExample01")
                    )
                )
                R4ConditionValidator.validate(condition).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'potato' is outside of required value set @ Condition.clinicalStatus",
            exception.message
        )
    }

    @Test
    fun `verificationStatus must be valid code if populated - bad verificationStatus`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val condition = Condition(
                    identifier = listOf(
                        Identifier(
                            system = CodeSystem.HL7_IDENTIFIER_TYPE.uri,
                            value = FHIRString("MRN")
                        )
                    ),
                    verificationStatus = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://terminology.hl7.org/CodeSystem/condition-ver-status"),
                                code = Code("potato"),
                                display = FHIRString("Potato")
                            )
                        )
                    ),
                    category = listOf(
                        CodeableConcept(
                            coding = listOf(
                                Coding(
                                    code = Code("encounter-diagnosis")
                                )
                            ),
                            text = FHIRString("Encounter Diagnosis")
                        )
                    ),
                    code = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://snomed.info/sct"),
                                code = Code("254637007"),
                                display = FHIRString("Non-small cell lung cancer")
                            )
                        )
                    ),
                    subject = Reference(
                        reference = FHIRString("Patient/roninPatientExample01")
                    )
                )
                R4ConditionValidator.validate(condition).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'potato' is outside of required value set @ Condition.verificationStatus",
            exception.message
        )
    }

    @Test
    fun `if condition is abated, clinicalStatus must be inactive, resolved, or remission - wrong clinicalStatus`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val condition = Condition(
                    identifier = listOf(
                        Identifier(
                            system = CodeSystem.HL7_IDENTIFIER_TYPE.uri,
                            value = FHIRString("MRN")
                        )
                    ),
                    clinicalStatus = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://terminology.hl7.org/CodeSystem/condition-clinical"),
                                code = Code("active"),
                                display = FHIRString("Active")
                            )
                        )
                    ),
                    category = listOf(
                        CodeableConcept(
                            coding = listOf(
                                Coding(
                                    code = Code("encounter-diagnosis")
                                )
                            ),
                            text = FHIRString("Encounter Diagnosis")
                        )
                    ),
                    code = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://snomed.info/sct"),
                                code = Code("254637007"),
                                display = FHIRString("Non-small cell lung cancer")
                            )
                        )
                    ),
                    subject = Reference(
                        reference = FHIRString("Patient/roninPatientExample01")
                    ),
                    abatement = DynamicValue(DynamicValueType.PERIOD, Period(start = DateTime("2020")))
                )
                R4ConditionValidator.validate(condition).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_CND_001: If condition is abated, then clinicalStatus must be one of the following: inactive, remission, resolved @ Condition",
            exception.message
        )
    }

    @Test
    fun `if condition is abated, clinicalStatus must be inactive, resolved, or remission - missing clinicalStatus`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val condition = Condition(
                    identifier = listOf(
                        Identifier(
                            system = CodeSystem.HL7_IDENTIFIER_TYPE.uri,
                            value = FHIRString("MRN")
                        )
                    ),
                    category = listOf(
                        CodeableConcept(
                            coding = listOf(
                                Coding(
                                    code = Code("encounter-diagnosis")
                                )
                            ),
                            text = FHIRString("Encounter Diagnosis")
                        )
                    ),
                    code = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://snomed.info/sct"),
                                code = Code("254637007"),
                                display = FHIRString("Non-small cell lung cancer")
                            )
                        )
                    ),
                    subject = Reference(
                        reference = FHIRString("Patient/roninPatientExample01")
                    ),
                    abatement = DynamicValue(DynamicValueType.PERIOD, Period(start = DateTime("2020")))
                )
                R4ConditionValidator.validate(condition).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_CND_001: If condition is abated, then clinicalStatus must be one of the following: inactive, remission, resolved @ Condition",
            exception.message
        )
    }

    @Test
    fun `clinicalStatus must be null if verificationStatus is entered-in-error`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val condition = Condition(
                    identifier = listOf(
                        Identifier(
                            system = CodeSystem.HL7_IDENTIFIER_TYPE.uri,
                            value = FHIRString("MRN")
                        )
                    ),
                    clinicalStatus = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://terminology.hl7.org/CodeSystem/condition-clinical"),
                                code = Code("active"),
                                display = FHIRString("Active")
                            )
                        )
                    ),
                    verificationStatus = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://terminology.hl7.org/CodeSystem/condition-ver-status"),
                                code = Code("entered-in-error"),
                                display = FHIRString("Entered in error")
                            )
                        )
                    ),
                    category = listOf(
                        CodeableConcept(
                            coding = listOf(
                                Coding(
                                    code = Code("encounter-diagnosis")
                                )
                            ),
                            text = FHIRString("Encounter Diagnosis")
                        )
                    ),
                    code = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://snomed.info/sct"),
                                code = Code("254637007"),
                                display = FHIRString("Non-small cell lung cancer")
                            )
                        )
                    ),
                    subject = Reference(
                        reference = FHIRString("Patient/roninPatientExample01")
                    )
                )
                R4ConditionValidator.validate(condition).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_CND_002: clinicalStatus SHALL NOT be present if verification Status is entered-in-error @ Condition",
            exception.message
        )
    }

    @Test
    fun `cannot create onset with unsupported dynamic value type`() {
        val exception = assertThrows<IllegalArgumentException> {
            val condition = Condition(
                subject = Reference(reference = FHIRString("subject")),
                onset = DynamicValue(type = DynamicValueType.BOOLEAN, value = false),
                clinicalStatus = CodeableConcept(
                    coding = listOf(
                        Coding(
                            system = Uri("http://terminology.hl7.org/CodeSystem/condition-clinical"),
                            code = Code("inactive"),
                            display = FHIRString("Inactive")
                        )
                    )
                ),
                abatement = DynamicValue(
                    DynamicValueType.AGE,
                    Age(value = Decimal(55.0), code = Code("a"), system = CodeSystem.UCUM.uri)
                )
            )
            R4ConditionValidator.validate(condition).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: onset can only be one of the following: DateTime, Age, Period, Range, String @ Condition.onset",
            exception.message
        )
    }

    @Test
    fun `cannot create abatement with unsupported dynamic value type`() {
        val exception = assertThrows<IllegalArgumentException> {
            val condition = Condition(
                subject = Reference(reference = FHIRString("subject")),
                onset = DynamicValue(
                    DynamicValueType.AGE,
                    Age(value = Decimal(55.0), code = Code("a"), system = CodeSystem.UCUM.uri)
                ),
                clinicalStatus = CodeableConcept(
                    coding = listOf(
                        Coding(
                            system = Uri("http://terminology.hl7.org/CodeSystem/condition-clinical"),
                            code = Code("inactive"),
                            display = FHIRString("Inactive")
                        )
                    )
                ),
                abatement = DynamicValue(type = DynamicValueType.BOOLEAN, value = false)
            )
            R4ConditionValidator.validate(condition).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: abatement can only be one of the following: DateTime, Age, Period, Range, String @ Condition.abatement",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val condition = Condition(
            subject = Reference(reference = FHIRString("subject"))
        )
        R4ConditionValidator.validate(condition).alertIfErrors()
    }
}

class R4ConditionEvidenceValidatorTest {
    @Test
    fun `fails if value provided without code or detail`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val conditionEvidence = ConditionEvidence(id = FHIRString("id"))
                R4ConditionEvidenceValidator.validate(conditionEvidence).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_CNDEV_001: evidence SHALL have code or details @ ConditionEvidence",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val conditionEvidence = ConditionEvidence(
            code = listOf(CodeableConcept(text = FHIRString("code")))
        )
        R4ConditionEvidenceValidator.validate(conditionEvidence).alertIfErrors()
    }
}

class R4ConditionStageValidatorTest {
    @Test
    fun `fails if value provided without summary`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val conditionStage = ConditionStage(id = FHIRString("id"))
                R4ConditionStageValidator.validate(conditionStage).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_CNDSTG_001: stage SHALL have summary or assessment @ ConditionStage",
            exception.message
        )
    }

    @Test
    fun `validates successfully with summary`() {
        val conditionStage = ConditionStage(
            summary = CodeableConcept(id = FHIRString("1234"))
        )
        R4ConditionStageValidator.validate(conditionStage).alertIfErrors()
    }

    @Test
    fun `validates successfully with assessment`() {
        val conditionStage = ConditionStage(
            assessment = listOf(Reference(display = FHIRString("assessment")))
        )
        R4ConditionStageValidator.validate(conditionStage).alertIfErrors()
    }
}
