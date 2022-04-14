package com.projectronin.interop.fhir.r4.ronin.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.CodeableConcepts
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
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.ContainedResource
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OncologyConditionTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val oncologyCondition = OncologyCondition(
            id = Id("12345"),
            meta = Meta(
                profile = listOf(Canonical("http://projectronin.com/fhir/us/ronin/StructureDefinition/oncology-condition"))
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
                        code = Code("confirmed"),
                        display = "Confirmed"
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
            bodySite = listOf(
                CodeableConcept(
                    coding = listOf(
                        Coding(
                            system = Uri("http://snomed.info/sct"),
                            code = Code("39607008"),
                            display = "Lung structure (body structure)"
                        )
                    )
                )
            ),
            subject = Reference(
                reference = "Patient/roninPatientExample01"
            ),
            onset = DynamicValue(DynamicValueType.DATE_TIME, DateTime("2019-04-01")),
            asserter = Reference(
                reference = "Practitioner/roninPractitionerExample01"
            ),
            stage = listOf(
                ConditionStage(
                    summary = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://cancerstaging.org"),
                                code = Code("3C"),
                                display = "IIIC"
                            )
                        )
                    )
                )
            )
        )

        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(oncologyCondition)
        val expectedJson = """
            |{
            |  "resourceType" : "Condition",
            |  "id" : "12345",
            |  "meta" : {
            |    "profile" : [ "http://projectronin.com/fhir/us/ronin/StructureDefinition/oncology-condition" ]
            |  },
            |  "implicitRules" : "implicit-rules",
            |  "language" : "en-US",
            |  "text" : {
            |    "status" : "generated",
            |    "div" : "div"
            |  },
            |  "contained" : [ {"resourceType":"Banana","field":"24680"} ],
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "modifierExtension" : [ {
            |    "url" : "http://localhost/modifier-extension",
            |    "valueString" : "Value"
            |  } ],
            |  "identifier" : [ {
            |    "type" : {
            |      "coding" : [ {
            |        "system" : "http://projectronin.com/id/tenantId",
            |        "code" : "TID",
            |        "display" : "Ronin-specified Tenant Identifier"
            |      } ],
            |      "text" : "Tenant ID"
            |    },
            |    "system" : "http://projectronin.com/id/tenantId",
            |    "value" : "tenantId"
            |  } ],
            |  "clinicalStatus" : {
            |    "coding" : [ {
            |      "system" : "http://terminology.hl7.org/CodeSystem/condition-clinical",
            |      "code" : "active",
            |      "display" : "Active"
            |    } ]
            |  },
            |  "verificationStatus" : {
            |    "coding" : [ {
            |      "system" : "http://terminology.hl7.org/CodeSystem/condition-ver-status",
            |      "code" : "confirmed",
            |      "display" : "Confirmed"
            |    } ]
            |  },
            |  "category" : [ {
            |    "coding" : [ {
            |      "code" : "encounter-diagnosis"
            |    } ],
            |    "text" : "Encounter Diagnosis"
            |  } ],
            |  "code" : {
            |    "coding" : [ {
            |      "system" : "http://snomed.info/sct",
            |      "code" : "254637007",
            |      "display" : "Non-small cell lung cancer"
            |    } ]
            |  },
            |  "bodySite" : [ {
            |    "coding" : [ {
            |      "system" : "http://snomed.info/sct",
            |      "code" : "39607008",
            |      "display" : "Lung structure (body structure)"
            |    } ]
            |  } ],
            |  "subject" : {
            |    "reference" : "Patient/roninPatientExample01"
            |  },
            |  "onsetDateTime" : "2019-04-01",
            |  "asserter" : {
            |    "reference" : "Practitioner/roninPractitionerExample01"
            |  },
            |  "stage" : [ {
            |    "summary" : {
            |      "coding" : [ {
            |        "system" : "http://cancerstaging.org",
            |        "code" : "3C",
            |        "display" : "IIIC"
            |      } ]
            |    }
            |  } ]
            |}
        """.trimMargin()
        assertEquals(expectedJson, json)
        val deserializedOncologyAppointment = JacksonManager.objectMapper.readValue<OncologyCondition>(json)
        assertEquals(oncologyCondition, deserializedOncologyAppointment)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val oncologyCondition = OncologyCondition(
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
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(oncologyCondition)
        val expectedJson = """
            |{
            |  "resourceType" : "Condition",
            |  "identifier" : [ {
            |    "type" : {
            |      "coding" : [ {
            |        "system" : "http://projectronin.com/id/tenantId",
            |        "code" : "TID",
            |        "display" : "Ronin-specified Tenant Identifier"
            |      } ],
            |      "text" : "Tenant ID"
            |    },
            |    "system" : "http://projectronin.com/id/tenantId",
            |    "value" : "tenantId"
            |  } ],
            |  "category" : [ {
            |    "coding" : [ {
            |      "code" : "encounter-diagnosis"
            |    } ],
            |    "text" : "Encounter Diagnosis"
            |  } ],
            |  "code" : {
            |    "coding" : [ {
            |      "system" : "http://snomed.info/sct",
            |      "code" : "254637007",
            |      "display" : "Non-small cell lung cancer"
            |    } ]
            |  },
            |  "subject" : {
            |    "reference" : "Patient/roninPatientExample01"
            |  }
            |}
        """.trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "resourceType" : "Condition",
            |  "identifier" : [ {
            |    "type" : {
            |      "coding" : [ {
            |        "system" : "http://projectronin.com/id/tenantId",
            |        "code" : "TID",
            |        "display" : "Ronin-specified Tenant Identifier"
            |      } ],
            |      "text" : "Tenant ID"
            |    },
            |    "system" : "http://projectronin.com/id/tenantId",
            |    "value" : "tenantId"
            |  } ],
            |  "category" : [ {
            |    "coding" : [ {
            |      "display" : "encounter-diagnosis"
            |    } ],
            |    "text" : "Encounter Diagnosis"
            |  } ],
            |  "code" : {
            |    "coding" : [ {
            |      "system" : "http://snomed.info/sct",
            |      "code" : "254637007",
            |      "display" : "Non-small cell lung cancer"
            |    } ]
            |  },
            |  "subject" : {
            |    "reference" : "Patient/roninPatientExample01"
            |  }
            |}
        """.trimMargin()
        val oncologyCondition = JacksonManager.objectMapper.readValue<OncologyCondition>(json)
        assertNull(oncologyCondition.id)
        assertNull(oncologyCondition.meta)
        assertNull(oncologyCondition.implicitRules)
        assertNull(oncologyCondition.language)
        assertNull(oncologyCondition.text)
        assertEquals(listOf<RoninResource>(), oncologyCondition.contained)
        assertEquals(listOf<Extension>(), oncologyCondition.extension)
        assertEquals(listOf<Extension>(), oncologyCondition.modifierExtension)
        assertNull(oncologyCondition.clinicalStatus)
        assertNull(oncologyCondition.verificationStatus)
        assertNull(oncologyCondition.severity)
        assertEquals(listOf<CodeableConcept>(), oncologyCondition.bodySite)
        assertNull(oncologyCondition.encounter)
        assertNull(oncologyCondition.onset)
        assertNull(oncologyCondition.abatement)
        assertNull(oncologyCondition.recordedDate)
        assertNull(oncologyCondition.recorder)
        assertNull(oncologyCondition.asserter)
        assertEquals(listOf<ConditionStage>(), oncologyCondition.stage)
        assertEquals(listOf<ConditionEvidence>(), oncologyCondition.evidence)
        assertEquals(listOf<Annotation>(), oncologyCondition.note)
    }

    @Test
    fun `fails if no tenant identifier provided`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                OncologyCondition(
                    identifier = listOf(
                        Identifier(
                            system = CodeSystem.MRN.uri,
                            type = CodeableConcepts.MRN,
                            value = "MRN"
                        ),
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
        assertEquals("Tenant identifier is required", exception.message)
    }

    @Test
    fun `category cannot be empty`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                OncologyCondition(
                    identifier = listOf(
                        Identifier(
                            system = CodeSystem.RONIN_TENANT.uri,
                            type = CodeableConcepts.RONIN_TENANT,
                            value = "tenantId"
                        )
                    ),
                    category = listOf(),
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
        assertEquals("At least one category must be provided", exception.message)
    }

    @Test
    fun `cannot create dynamic values with bad type for onset`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                OncologyCondition(
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
                    onset = DynamicValue(DynamicValueType.STRING, "2019-04-01")
                )
            }
        assertEquals("Invalid dynamic type for condition onset", exception.message)
    }

    @Test
    fun `cannot create dynamic values with bad type for abatement`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                OncologyCondition(
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
                    onset = DynamicValue(DynamicValueType.DATE_TIME, DateTime("2019-04-01")),
                    abatement = DynamicValue(DynamicValueType.STRING, "potato")
                )
            }
        assertEquals("Invalid dynamic type for condition abatement", exception.message)
    }

    @Test
    fun `clinicalStatus must be valid code if populated`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                OncologyCondition(
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
        assertEquals("Invalid Clinical Status Code", exception.message)
    }

    @Test
    fun `clinicalStatus can be evaluated with no coding`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                OncologyCondition(
                    identifier = listOf(
                        Identifier(
                            system = CodeSystem.RONIN_TENANT.uri,
                            type = CodeableConcepts.RONIN_TENANT,
                            value = "tenantId"
                        )
                    ),
                    clinicalStatus = CodeableConcept(),
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
        assertEquals("Invalid Clinical Status Code", exception.message)
    }

    @Test
    fun `verificationStatus must be valid code if populated`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                OncologyCondition(
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
        assertEquals("Invalid Verification Status Code", exception.message)
    }

    @Test
    fun `verificationStatus can be evaluated with no coding`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                OncologyCondition(
                    identifier = listOf(
                        Identifier(
                            system = CodeSystem.RONIN_TENANT.uri,
                            type = CodeableConcepts.RONIN_TENANT,
                            value = "tenantId"
                        )
                    ),
                    verificationStatus = CodeableConcept(),
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
        assertEquals("Invalid Verification Status Code", exception.message)
    }

    @Test
    fun `con-3 clinicalStatus must not be null if verificationStatus is not entered-in-error and category is problem-list-item`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                OncologyCondition(
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
                                code = Code("confirmed"),
                                display = "Confirmed"
                            )
                        )
                    ),
                    category = listOf(
                        CodeableConcept(
                            coding = listOf(
                                Coding(
                                    code = Code("problem-list-item")
                                )
                            ),
                            text = "Problem List Item"
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
            "Condition.clinicalStatus SHALL be present if verificationStatus is not entered-in-error and category is problem-list-item",
            exception.message
        )
    }

    @Test
    fun `con-4 if condition is abated, clinicalStatus must be inactive, resolved, or remission`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                OncologyCondition(
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
            "If condition is abated, then clinicalStatus must be either inactive, resolved, or remission",
            exception.message
        )
    }

    @Test
    fun `con-5 clinicalStatus must be null if verificationStatus is entered-in-error`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                OncologyCondition(
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
            "Condition.clinicalStatus SHALL NOT be present if verification Status is entered-in-error",
            exception.message
        )
    }
}
