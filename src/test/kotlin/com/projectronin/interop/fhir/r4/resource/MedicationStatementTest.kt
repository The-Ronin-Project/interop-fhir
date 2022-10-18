package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Dosage
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
import com.projectronin.interop.fhir.r4.valueset.MedicationStatementStatus
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MedicationStatementTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val medication = DynamicValue(type = DynamicValueType.CODEABLE_CONCEPT, value = CodeableConcept(text = "Medication Category"))
        val effective = DynamicValue(type = DynamicValueType.DATE_TIME, value = DateTime("2022"))
        val medicationStatement = MedicationStatement(
            id = Id("123"),
            meta = Meta(
                profile = listOf(Canonical("RoninMedicationStatement")),
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
            basedOn = listOf(Reference(display = "basedOn Reference")),
            partOf = listOf(Reference(display = "partOf Reference")),
            status = MedicationStatementStatus.ACTIVE.asCode(),
            statusReason = listOf(CodeableConcept(text = "statusReason category")),
            category = CodeableConcept(text = "category category"),
            medication = medication,
            subject = Reference(display = "subject Reference"),
            context = Reference(display = "context Reference"),
            effective = effective,
            dateAsserted = DateTime("2021"),
            informationSource = Reference(display = "informationSource Reference"),
            derivedFrom = listOf(Reference(display = "derivedFrom Reference")),
            reasonCode = listOf(CodeableConcept(text = "reasonCode category")),
            reasonReference = listOf(Reference(display = "reasonReference Reference")),
            note = listOf(Annotation(text = Markdown("note"))),
            dosage = listOf(Dosage(text = "Dosage"))
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(medicationStatement)
        val expectedJson = """
            {
              "resourceType" : "MedicationStatement",
              "id" : "123",
              "meta" : {
                "profile" : [ "RoninMedicationStatement" ]
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
              "basedOn" : [ {
                "display" : "basedOn Reference"
              } ],
              "partOf" : [ {
                "display" : "partOf Reference"
              } ],
              "status" : "active",
              "statusReason" : [ {
                "text" : "statusReason category"
              } ],
              "category" : {
                "text" : "category category"
              },
              "medicationCodeableConcept" : {
                "text" : "Medication Category"
              },
              "subject" : {
                "display" : "subject Reference"
              },
              "context" : {
                "display" : "context Reference"
              },
              "effectiveDateTime" : "2022",
              "dateAsserted" : "2021",
              "informationSource" : {
                "display" : "informationSource Reference"
              },
              "derivedFrom" : [ {
                "display" : "derivedFrom Reference"
              } ],
              "reasonCode" : [ {
                "text" : "reasonCode category"
              } ],
              "reasonReference" : [ {
                "display" : "reasonReference Reference"
              } ],
              "note" : [ {
                "text" : "note"
              } ],
              "dosage" : [ {
                "text" : "Dosage"
              } ]
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedStatement = JacksonManager.objectMapper.readValue<MedicationStatement>(json)
        assertEquals(medicationStatement, deserializedStatement)
    }

    @Test
    fun `serialization ignores nulls`() {
        val medication = DynamicValue(type = DynamicValueType.CODEABLE_CONCEPT, value = CodeableConcept(text = "Medication Category"))
        val effective = DynamicValue(type = DynamicValueType.DATE_TIME, value = DateTime("2022"))
        val medicationStatement = MedicationStatement(
            status = MedicationStatementStatus.COMPLETED.asCode(),
            medication = medication,
            subject = Reference(display = "reference"),
            effective = effective
        )

        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(medicationStatement)

        val expectedJson = """
            {
              "resourceType" : "MedicationStatement",
              "status" : "completed",
              "medicationCodeableConcept" : {
                "text" : "Medication Category"
              },
              "subject" : {
                "display" : "reference"
              },
              "effectiveDateTime" : "2022"
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }
}