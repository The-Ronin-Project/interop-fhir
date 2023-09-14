package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.MedicationAdministrationStatus
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MedicationAdministrationTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val medication = DynamicValue(
            type = DynamicValueType.CODEABLE_CONCEPT,
            value = CodeableConcept(text = FHIRString("Medication Category"))
        )
        val effective = DynamicValue(type = DynamicValueType.DATE_TIME, value = DateTime("2022"))
        val medicationAdministration = MedicationAdministration(
            id = Id("123"),
            meta = Meta(
                profile = listOf(Canonical("RoninMedicationAdministration"))
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
            partOf = listOf(Reference(display = FHIRString("partOf Reference"))),
            status = MedicationAdministrationStatus.COMPLETED.asCode(),
            statusReason = listOf(CodeableConcept(text = FHIRString("statusReason category"))),
            category = CodeableConcept(text = FHIRString("category category")),
            medication = medication,
            subject = Reference(display = FHIRString("subject Reference")),
            context = Reference(display = FHIRString("context Reference")),
            effective = effective,
            reasonCode = listOf(CodeableConcept(text = FHIRString("reasonCode category"))),
            reasonReference = listOf(Reference(display = FHIRString("reasonReference Reference"))),
            note = listOf(Annotation(text = Markdown("note"))),
            dosage = MedicationAdministrationDosage(
                text = FHIRString("Dosage"),
                rate = DynamicValue(type = DynamicValueType.QUANTITY, value = Quantity(value = Decimal(1)))
            )
        )
        val json =
            JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(medicationAdministration)
        val expectedJson = """
            {
              "resourceType" : "MedicationAdministration",
              "id" : "123",
              "meta" : {
                "profile" : [ "RoninMedicationAdministration" ]
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
              "partOf" : [ {
                "display" : "partOf Reference"
              } ],
              "status" : "completed",
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
              "reasonCode" : [ {
                "text" : "reasonCode category"
              } ],
              "reasonReference" : [ {
                "display" : "reasonReference Reference"
              } ],
              "note" : [ {
                "text" : "note"
              } ],
              "dosage" : {
                "text" : "Dosage",
                "rateQuantity" : {
                  "value" : 1.0
                }
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedAdministration = JacksonManager.objectMapper.readValue<MedicationAdministration>(json)
        assertEquals(medicationAdministration, deserializedAdministration)
    }
}
