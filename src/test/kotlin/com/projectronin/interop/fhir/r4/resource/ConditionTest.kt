package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.CodeableConcepts
import com.projectronin.interop.fhir.r4.datatype.Age
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.ConditionEvidence
import com.projectronin.interop.fhir.r4.datatype.ConditionStage
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.ConditionClinicalStatusCodes
import com.projectronin.interop.fhir.r4.valueset.ConditionVerificationStatus
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ConditionTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val onset = DynamicValue(DynamicValueType.STRING, "22")
        val abatement = DynamicValue(DynamicValueType.STRING, "29")
        val condition = Condition(
            id = Id("12345"),
            meta = Meta(
                profile = listOf(Canonical("http://projectronin.com/fhir/us/ronin/StructureDefinition/oncology-practitioner")),
            ),
            implicitRules = Uri("implicit-rules"),
            language = Code("en-US"),
            text = Narrative(
                status = NarrativeStatus.GENERATED,
                div = "div"
            ),
            contained = listOf(ContainedResource("""{"resourceType":"Banana","field":"24680"}""")),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            modifierExtension = listOf(
                Extension(
                    url = Uri("http://localhost/modifier-extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            identifier = listOf(Identifier(value = "id")),
            clinicalStatus = CodeableConcept(
                coding = listOf(
                    Coding(code = Code(value = ConditionClinicalStatusCodes.RESOLVED.code))
                )
            ),
            verificationStatus = CodeableConcept(
                coding = listOf(
                    Coding(code = Code(value = ConditionVerificationStatus.CONFIRMED.code))
                )
            ),
            category = listOf(CodeableConcept(text = "category")),
            severity = CodeableConcept(text = "severity"),
            code = CodeableConcept(text = "code"),
            bodySite = listOf(CodeableConcept(text = "body site")),
            subject = Reference(reference = "subject"),
            encounter = Reference(reference = "encounter"),
            onset = onset,
            abatement = abatement,
            recordedDate = DateTime("2003-06-29"),
            recorder = Reference(reference = "recorder"),
            asserter = Reference(reference = "asserter"),
            stage = listOf(ConditionStage(summary = CodeableConcept(text = "summary"))),
            evidence = listOf(ConditionEvidence(code = listOf(CodeableConcept(text = "code")))),
            note = listOf(Annotation(text = Markdown("note")))
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(condition)

        val expectedJson = """
            {
              "resourceType" : "Condition",
              "id" : "12345",
              "meta" : {
                "profile" : [ "http://projectronin.com/fhir/us/ronin/StructureDefinition/oncology-practitioner" ]
              },
              "implicitRules" : "implicit-rules",
              "language" : "en-US",
              "text" : {
                "status" : "generated",
                "div" : "div"
              },
              "contained" : [ {"resourceType":"Banana","field":"24680"} ],
              "extension" : [ {
                "url" : "http://localhost/extension",
                "valueString" : "Value"
              } ],
              "modifierExtension" : [ {
                "url" : "http://localhost/modifier-extension",
                "valueString" : "Value"
              } ],
              "identifier" : [ {
                "value" : "id"
              } ],
              "clinicalStatus" : {
                "coding" : [ {
                  "code" : "resolved"
                } ]
              },
              "verificationStatus" : {
                "coding" : [ {
                  "code" : "confirmed"
                } ]
              },
              "category" : [ {
                "text" : "category"
              } ],
              "severity" : {
                "text" : "severity"
              },
              "code" : {
                "text" : "code"
              },
              "bodySite" : [ {
                "text" : "body site"
              } ],
              "subject" : {
                "reference" : "subject"
              },
              "encounter" : {
                "reference" : "encounter"
              },
              "onsetString" : "22",
              "abatementString" : "29",
              "recordedDate" : "2003-06-29",
              "recorder" : {
                "reference" : "recorder"
              },
              "asserter" : {
                "reference" : "asserter"
              },
              "stage" : [ {
                "summary" : {
                  "text" : "summary"
                }
              } ],
              "evidence" : [ {
                "code" : [ {
                  "text" : "code"
                } ]
              } ],
              "note" : [ {
                "text" : "note"
              } ]
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedCondition = JacksonManager.objectMapper.readValue<Condition>(json)
        assertEquals(condition, deserializedCondition)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val condition = Condition(
            subject = Reference(reference = "subject"),
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(condition)

        val expectedJson = """
            {
              "resourceType" : "Condition",
              "subject" : {
                "reference" : "subject"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "resourceType" : "Condition",
              "subject" : {
                "reference" : "subject"
              }
            }
        """.trimIndent()
        val condition = JacksonManager.objectMapper.readValue<Condition>(json)

        assertNull(condition.id)
        assertNull(condition.meta)
        assertNull(condition.implicitRules)
        assertNull(condition.language)
        assertNull(condition.text)
        assertEquals(listOf<Resource>(), condition.contained)
        assertEquals(listOf<Extension>(), condition.extension)
        assertEquals(listOf<Extension>(), condition.modifierExtension)
        assertEquals(listOf<Identifier>(), condition.identifier)
        assertNull(condition.clinicalStatus)
        assertNull(condition.verificationStatus)
        assertEquals(listOf<CodeableConcept>(), condition.category)
        assertNull(condition.severity)
        assertNull(condition.code)
        assertEquals(listOf<CodeableConcept>(), condition.bodySite)
        assertEquals(Reference(reference = "subject"), condition.subject)
        assertNull(condition.encounter)
        assertNull(condition.onset)
        assertNull(condition.abatement)
        assertNull(condition.recordedDate)
        assertNull(condition.recorder)
        assertNull(condition.asserter)
        assertEquals(listOf<ConditionStage>(), condition.stage)
        assertEquals(listOf<ConditionEvidence>(), condition.evidence)
        assertEquals(listOf<Annotation>(), condition.note)
    }

    @Test
    fun `clinicalStatus must be valid code - bad clinicalStatus`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                Condition(
                    identifier = listOf(
                        Identifier(
                            system = CodeSystem.RONIN_TENANT.uri,
                            type = CodeableConcepts.RONIN_TENANT,
                            value = "tenantId"
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
            }
        assertEquals(
            "clinicalStatus can only be one of the following codes: active, recurrence, relapse, inactive, remission, resolved",
            exception.message
        )
    }

    @Test
    fun `clinicalStatus must be valid code - succeeds for null clinicalStatus`() {
        val condition = Condition(
            identifier = listOf(
                Identifier(
                    system = CodeSystem.RONIN_TENANT.uri,
                    type = CodeableConcepts.RONIN_TENANT,
                    value = "tenantId"
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
        assertNotNull(condition)
    }

    @Test
    fun `verificationStatus must be valid code if populated - bad verificationStatus`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                Condition(
                    identifier = listOf(
                        Identifier(
                            system = CodeSystem.RONIN_TENANT.uri,
                            type = CodeableConcepts.RONIN_TENANT,
                            value = "tenantId"
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
            }
        assertEquals(
            "verificationStatus can only be one of the following codes: confirmed, differential, entered-in-error, provisional, refuted, unconfirmed",
            exception.message
        )
    }

    @Test
    fun `verificationStatus must be valid code - succeeds for null verificationStatus`() {
        val condition = Condition(
            identifier = listOf(
                Identifier(
                    system = CodeSystem.RONIN_TENANT.uri,
                    type = CodeableConcepts.RONIN_TENANT,
                    value = "tenantId"
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
        assertNotNull(condition)
    }

    @Test
    fun `if condition is abated, clinicalStatus must be inactive, resolved, or remission - wrong clinicalStatus`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                Condition(
                    identifier = listOf(
                        Identifier(
                            system = CodeSystem.RONIN_TENANT.uri,
                            type = CodeableConcepts.RONIN_TENANT,
                            value = "tenantId"
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
            }
        assertEquals(
            "If condition is abated, then clinicalStatus must be one of the following: inactive, remission, resolved",
            exception.message
        )
    }

    @Test
    fun `if condition is abated, clinicalStatus must be inactive, resolved, or remission - missing clinicalStatus`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                Condition(
                    identifier = listOf(
                        Identifier(
                            system = CodeSystem.RONIN_TENANT.uri,
                            type = CodeableConcepts.RONIN_TENANT,
                            value = "tenantId"
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
            }
        assertEquals(
            "If condition is abated, then clinicalStatus must be one of the following: inactive, remission, resolved",
            exception.message
        )
    }

    @Test
    fun `clinicalStatus must be null if verificationStatus is entered-in-error`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                Condition(
                    identifier = listOf(
                        Identifier(
                            system = CodeSystem.RONIN_TENANT.uri,
                            type = CodeableConcepts.RONIN_TENANT,
                            value = "tenantId"
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
            }
        assertEquals(
            "clinicalStatus SHALL NOT be present if verification Status is entered-in-error",
            exception.message
        )
    }

    @Test
    fun `cannot create onset with unsupported dynamic value type`() {
        val exception = assertThrows<IllegalArgumentException> {
            Condition(
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
        }
        assertEquals(
            "onset can only be one of the following data types: DateTime, Age, Period, Range, String",
            exception.message
        )
    }

    @Test
    fun `cannot create abatement with unsupported dynamic value type`() {
        val exception = assertThrows<IllegalArgumentException> {
            Condition(
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
        }
        assertEquals(
            "abatement can only be one of the following data types: DateTime, Age, Period, Range, String",
            exception.message
        )
    }

    @Test
    fun `clinicalStatus must contain at least one expected code when populated`() {
        val condition =
            Condition(
                identifier = listOf(
                    Identifier(
                        system = CodeSystem.RONIN_TENANT.uri,
                        type = CodeableConcepts.RONIN_TENANT,
                        value = "tenantId"
                    )
                ),
                clinicalStatus = CodeableConcept(
                    coding = listOf(
                        Coding(
                            system = Uri("http://terminology.hl7.org/CodeSystem/condition-clinical"),
                            code = Code("active"),
                            display = "Active"
                        ),
                        Coding(
                            system = Uri("additionalcode"),
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
        assertNotNull(condition)
    }
}
