package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
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
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

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
                status = NarrativeStatus.GENERATED.asCode(),
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
}
