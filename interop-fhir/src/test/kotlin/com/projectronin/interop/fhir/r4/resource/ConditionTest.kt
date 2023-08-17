package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.ConditionClinicalStatusCodes
import com.projectronin.interop.fhir.r4.valueset.ConditionVerificationStatus
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ConditionTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val onset = DynamicValue(DynamicValueType.STRING, FHIRString("22"))
        val abatement = DynamicValue(DynamicValueType.STRING, FHIRString("29"))
        val condition = Condition(
            id = Id("12345"),
            meta = Meta(
                profile = listOf(Canonical("RoninConditionProblemsHealthConcerns"))
            ),
            implicitRules = Uri("implicit-rules"),
            language = Code("en-US"),
            text = Narrative(
                status = NarrativeStatus.GENERATED.asCode(),
                div = FHIRString("div")
            ),
            contained = listOf(Location(id = Id("1234"), name = FHIRString("Contained Location"))),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            modifierExtension = listOf(
                Extension(
                    url = Uri("http://localhost/modifier-extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            identifier = listOf(Identifier(value = FHIRString("id"))),
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
            category = listOf(CodeableConcept(text = FHIRString("category"))),
            severity = CodeableConcept(text = FHIRString("severity")),
            code = CodeableConcept(text = FHIRString("code")),
            bodySite = listOf(CodeableConcept(text = FHIRString("body site"))),
            subject = Reference(reference = FHIRString("subject")),
            encounter = Reference(reference = FHIRString("encounter")),
            onset = onset,
            abatement = abatement,
            recordedDate = DateTime("2003-06-29"),
            recorder = Reference(reference = FHIRString("recorder")),
            asserter = Reference(reference = FHIRString("asserter")),
            stage = listOf(ConditionStage(summary = CodeableConcept(text = FHIRString("summary")))),
            evidence = listOf(ConditionEvidence(code = listOf(CodeableConcept(text = FHIRString("code"))))),
            note = listOf(Annotation(text = Markdown("note")))
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(condition)

        val expectedJson = """
            {
              "resourceType" : "Condition",
              "id" : "12345",
              "meta" : {
                "profile" : [ "RoninConditionProblemsHealthConcerns" ]
              },
              "implicitRules" : "implicit-rules",
              "language" : "en-US",
              "text" : {
                "status" : "generated",
                "div" : "div"
              },
              "contained" : [ {
                "resourceType" : "Location",
                "id" : "1234",
                "name" : "Contained Location"
              } ],
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
            subject = Reference(reference = FHIRString("subject"))
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
        assertEquals(listOf<Resource<Nothing>>(), condition.contained)
        assertEquals(listOf<Extension>(), condition.extension)
        assertEquals(listOf<Extension>(), condition.modifierExtension)
        assertEquals(listOf<Identifier>(), condition.identifier)
        assertNull(condition.clinicalStatus)
        assertNull(condition.verificationStatus)
        assertEquals(listOf<CodeableConcept>(), condition.category)
        assertNull(condition.severity)
        assertNull(condition.code)
        assertEquals(listOf<CodeableConcept>(), condition.bodySite)
        assertEquals(Reference(reference = FHIRString("subject")), condition.subject)
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
}

class ConditionEvidenceTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val conditionEvidence = ConditionEvidence(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            modifierExtension = listOf(
                Extension(
                    url = Uri("http://localhost/modifier-extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            code = listOf(CodeableConcept(text = FHIRString("code"))),
            detail = listOf(Reference(display = FHIRString("detail")))
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(conditionEvidence)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "modifierExtension" : [ {
            |    "url" : "http://localhost/modifier-extension",
            |    "valueString" : "Value"
            |  } ],
            |  "code" : [ {
            |    "text" : "code"
            |  } ],
            |  "detail" : [ {
            |    "display" : "detail"
            |  } ]
            |}
        """.trimMargin()
        assertEquals(expectedJson, json)

        val deserializedConditionEvidence = JacksonManager.objectMapper.readValue<ConditionEvidence>(json)
        assertEquals(conditionEvidence, deserializedConditionEvidence)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val conditionEvidence = ConditionEvidence(
            code = listOf(CodeableConcept(text = FHIRString("code")))
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(conditionEvidence)

        val expectedJson = """
            |{
            |  "code" : [ {
            |    "text" : "code"
            |  } ]
            |}
        """.trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "detail" : [ {
            |    "display" : "detail"
            |  } ]
            |}
        """.trimMargin()
        val conditionEvidence = JacksonManager.objectMapper.readValue<ConditionEvidence>(json)

        assertNull(conditionEvidence.id)
        assertEquals(listOf<Extension>(), conditionEvidence.extension)
        assertEquals(listOf<Extension>(), conditionEvidence.modifierExtension)
        assertEquals(listOf<CodeableConcept>(), conditionEvidence.code)
        assertEquals(listOf(Reference(display = FHIRString("detail"))), conditionEvidence.detail)
    }
}

class ConditionStageTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val conditionStage = ConditionStage(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            modifierExtension = listOf(
                Extension(
                    url = Uri("http://localhost/modifier-extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            summary = CodeableConcept(text = FHIRString("summary")),
            assessment = listOf(Reference(display = FHIRString("assessment"))),
            type = CodeableConcept(text = FHIRString("type"))
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(conditionStage)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "modifierExtension" : [ {
            |    "url" : "http://localhost/modifier-extension",
            |    "valueString" : "Value"
            |  } ],
            |  "summary" : {
            |    "text" : "summary"
            |  },
            |  "assessment" : [ {
            |    "display" : "assessment"
            |  } ],
            |  "type" : {
            |    "text" : "type"
            |  }
            |}
        """.trimMargin()
        assertEquals(expectedJson, json)

        val deserializedConditionStage = JacksonManager.objectMapper.readValue<ConditionStage>(json)
        assertEquals(conditionStage, deserializedConditionStage)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val conditionStage = ConditionStage(
            assessment = listOf(Reference(display = FHIRString("assessment")))
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(conditionStage)

        val expectedJson = """
            |{
            |  "assessment" : [ {
            |    "display" : "assessment"
            |  } ]
            |}
        """.trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "summary" : {
            |    "text" : "summary"
            |  }
            |}
        """.trimMargin()
        val conditionStage = JacksonManager.objectMapper.readValue<ConditionStage>(json)

        assertNull(conditionStage.id)
        assertEquals(listOf<Extension>(), conditionStage.extension)
        assertEquals(listOf<Extension>(), conditionStage.modifierExtension)
        assertEquals(CodeableConcept(text = FHIRString("summary")), conditionStage.summary)
        assertEquals(listOf<Reference>(), conditionStage.assessment)
        assertNull(conditionStage.type)
    }
}
