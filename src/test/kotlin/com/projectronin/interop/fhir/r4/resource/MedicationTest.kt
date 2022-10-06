package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.Batch
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Ingredient
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.Ratio
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.MedicationStatus
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class MedicationTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val medication = Medication(
            id = Id("12345"),
            meta = Meta(
                profile = listOf(Canonical("RoninMedication"))
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
            code = CodeableConcept(text = "Med code"),
            status = MedicationStatus.ACTIVE.asCode(),
            manufacturer = Reference(display = "Manufacturer reference"),
            form = CodeableConcept(text = "Med form"),
            amount = Ratio(numerator = Quantity(value = 1.0), denominator = Quantity(value = 1.0)),
            ingredient = listOf(
                Ingredient(
                    item = DynamicValue(
                        DynamicValueType.CODEABLE_CONCEPT,
                        CodeableConcept(text = "Med ingredient")
                    )
                )
            ),
            batch = Batch(lotNumber = "Batch log")
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(medication)

        val expectedJson = """
            {
              "resourceType" : "Medication",
              "id" : "12345",
              "meta" : {
                "profile" : [ "RoninMedication" ]
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
              "code" : {
                "text" : "Med code"
              },
              "status" : "active",
              "manufacturer" : {
                "display" : "Manufacturer reference"
              },
              "form" : {
                "text" : "Med form"
              },
              "amount" : {
                "numerator" : {
                  "value" : 1.0
                },
                "denominator" : {
                  "value" : 1.0
                }
              },
              "ingredient" : [ {
                "itemCodeableConcept" : {
                  "text" : "Med ingredient"
                }
              } ],
              "batch" : {
                "lotNumber" : "Batch log"
              }
            }
        """.trimIndent()

        assertEquals(expectedJson, json)

        val deserializedMedication = JacksonManager.objectMapper.readValue<Medication>(json)
        assertEquals(medication, deserializedMedication)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val medication = Medication()
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(medication)

        val expectedJson = """
            {
              "resourceType" : "Medication"
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "resourceType" : "Medication"
            }
        """.trimIndent()
        val medication = JacksonManager.objectMapper.readValue<Medication>(json)

        assertEquals("Medication", medication.resourceType)
        assertNull(medication.id)
        assertNull(medication.meta)
        assertNull(medication.implicitRules)
        assertNull(medication.language)
        assertNull(medication.text)
        assertEquals(listOf<ContainedResource>(), medication.contained)
        assertEquals(listOf<Extension>(), medication.extension)
        assertEquals(listOf<Extension>(), medication.modifierExtension)
        assertEquals(listOf<Identifier>(), medication.identifier)
        assertNull(medication.code)
        assertNull(medication.status)
        assertNull(medication.manufacturer)
        assertNull(medication.form)
        assertNull(medication.amount)
        assertEquals(listOf<Ingredient>(), medication.ingredient)
        assertNull(medication.batch)
    }
}
