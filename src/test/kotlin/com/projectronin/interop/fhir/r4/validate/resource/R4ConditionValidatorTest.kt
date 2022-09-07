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
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.Condition
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4ConditionValidatorTest {
    @Test
    fun `subject is not provided`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val condition = Condition(
                    subject = null,
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
                            value = "MRN"
                        )
                    ),
                    clinicalStatus = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://terminology.hl7.org/CodeSystem/condition-clinical"),
                                code = Code("potato"),
                                display = "Potato"
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
                            text = "Encounter Diagnosis"
                        )
                    ),
                    code = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://snomed.info/sct"),
                                code = Code("254637007"),
                                display = "Non-small cell lung cancer"
                            )
                        )
                    ),
                    subject = Reference(
                        reference = "Patient/roninPatientExample01"
                    )
                )
                R4ConditionValidator.validate(condition).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: clinicalStatus is outside of required value set @ Condition.clinicalStatus",
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
                            value = "MRN"
                        )
                    ),
                    verificationStatus = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://terminology.hl7.org/CodeSystem/condition-ver-status"),
                                code = Code("potato"),
                                display = "Potato"
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
                            text = "Encounter Diagnosis"
                        )
                    ),
                    code = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://snomed.info/sct"),
                                code = Code("254637007"),
                                display = "Non-small cell lung cancer"
                            )
                        )
                    ),
                    subject = Reference(
                        reference = "Patient/roninPatientExample01"
                    )
                )
                R4ConditionValidator.validate(condition).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: verificationStatus is outside of required value set @ Condition.verificationStatus",
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
                            value = "MRN"
                        )
                    ),
                    clinicalStatus = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://terminology.hl7.org/CodeSystem/condition-clinical"),
                                code = Code("active"),
                                display = "Active"
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
                            text = "Encounter Diagnosis"
                        )
                    ),
                    code = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://snomed.info/sct"),
                                code = Code("254637007"),
                                display = "Non-small cell lung cancer"
                            )
                        )
                    ),
                    subject = Reference(
                        reference = "Patient/roninPatientExample01"
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
                            value = "MRN"
                        )
                    ),
                    category = listOf(
                        CodeableConcept(
                            coding = listOf(
                                Coding(
                                    code = Code("encounter-diagnosis")
                                )
                            ),
                            text = "Encounter Diagnosis"
                        )
                    ),
                    code = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://snomed.info/sct"),
                                code = Code("254637007"),
                                display = "Non-small cell lung cancer"
                            )
                        )
                    ),
                    subject = Reference(
                        reference = "Patient/roninPatientExample01"
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
                            value = "MRN"
                        )
                    ),
                    clinicalStatus = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://terminology.hl7.org/CodeSystem/condition-clinical"),
                                code = Code("active"),
                                display = "Active"
                            )
                        )
                    ),
                    verificationStatus = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://terminology.hl7.org/CodeSystem/condition-ver-status"),
                                code = Code("entered-in-error"),
                                display = "Entered in error"
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
                            text = "Encounter Diagnosis"
                        )
                    ),
                    code = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://snomed.info/sct"),
                                code = Code("254637007"),
                                display = "Non-small cell lung cancer"
                            )
                        )
                    ),
                    subject = Reference(
                        reference = "Patient/roninPatientExample01"
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
                subject = Reference(reference = "subject"),
                onset = DynamicValue(type = DynamicValueType.BOOLEAN, value = false),
                clinicalStatus = CodeableConcept(
                    coding = listOf(
                        Coding(
                            system = Uri("http://terminology.hl7.org/CodeSystem/condition-clinical"),
                            code = Code("inactive"),
                            display = "Inactive"
                        )
                    )
                ),
                abatement = DynamicValue(
                    DynamicValueType.AGE,
                    Age(value = 55.0, code = Code("a"), system = CodeSystem.UCUM.uri)
                ),
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
                subject = Reference(reference = "subject"),
                onset = DynamicValue(
                    DynamicValueType.AGE,
                    Age(value = 55.0, code = Code("a"), system = CodeSystem.UCUM.uri)
                ),
                clinicalStatus = CodeableConcept(
                    coding = listOf(
                        Coding(
                            system = Uri("http://terminology.hl7.org/CodeSystem/condition-clinical"),
                            code = Code("inactive"),
                            display = "Inactive"
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
            subject = Reference(reference = "subject")
        )
        R4ConditionValidator.validate(condition).alertIfErrors()
    }
}