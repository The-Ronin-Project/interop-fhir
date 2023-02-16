package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.Ratio
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
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
                div = FHIRString("div")
            ),
            contained = listOf(ContainedResource("""{"resourceType":"Banana","field":"24680"}""")),
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
            code = CodeableConcept(text = FHIRString("Med code")),
            status = MedicationStatus.ACTIVE.asCode(),
            manufacturer = Reference(display = FHIRString("Manufacturer reference")),
            form = CodeableConcept(text = FHIRString("Med form")),
            amount = Ratio(numerator = Quantity(value = Decimal(1.0)), denominator = Quantity(value = Decimal(1.0))),
            ingredient = listOf(
                Ingredient(
                    item = DynamicValue(
                        DynamicValueType.CODEABLE_CONCEPT,
                        CodeableConcept(text = FHIRString("Med ingredient"))
                    )
                )
            ),
            batch = Batch(lotNumber = FHIRString("Batch log"))
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

class BatchTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val batch = Batch(
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
            lotNumber = FHIRString("123ABC"),
            expirationDate = DateTime("2022-08-31")
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(batch)

        val expectedJson = """
            {
              "id" : "12345",
              "extension" : [ {
                "url" : "http://localhost/extension",
                "valueString" : "Value"
              } ],
              "modifierExtension" : [ {
                "url" : "http://localhost/modifier-extension",
                "valueString" : "Value"
              } ],
              "lotNumber" : "123ABC",
              "expirationDate" : "2022-08-31"
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedBatch = JacksonManager.objectMapper.readValue<Batch>(json)
        assertEquals(batch, deserializedBatch)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val batch = Batch()
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(batch)

        val expectedJson = "{ }"
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = "{ }"
        val batch = JacksonManager.objectMapper.readValue<Batch>(json)

        assertNull(batch.id)
        assertEquals(listOf<Extension>(), batch.extension)
        assertEquals(listOf<Extension>(), batch.modifierExtension)
        assertNull(batch.lotNumber)
        assertNull(batch.expirationDate)
    }
}

class IngredientTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val ingredient = Ingredient(
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
            item = DynamicValue(DynamicValueType.CODEABLE_CONCEPT, CodeableConcept(text = FHIRString("test"))),
            isActive = FHIRBoolean.TRUE,
            strength = Ratio(
                numerator = Quantity(value = Decimal(1.0)),
                denominator = Quantity(value = Decimal(1.0))
            )
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ingredient)

        val expectedJson = """
            {
              "id" : "12345",
              "extension" : [ {
                "url" : "http://localhost/extension",
                "valueString" : "Value"
              } ],
              "modifierExtension" : [ {
                "url" : "http://localhost/modifier-extension",
                "valueString" : "Value"
              } ],
              "itemCodeableConcept" : {
                "text" : "test"
              },
              "isActive" : true,
              "strength" : {
                "numerator" : {
                  "value" : 1.0
                },
                "denominator" : {
                  "value" : 1.0
                }
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedIngredient = JacksonManager.objectMapper.readValue<Ingredient>(json)
        assertEquals(ingredient, deserializedIngredient)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val ingredient =
            Ingredient(
                item = DynamicValue(
                    DynamicValueType.CODEABLE_CONCEPT,
                    CodeableConcept(text = FHIRString("test"))
                )
            )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ingredient)

        val expectedJson = """
            {
              "itemCodeableConcept" : {
                "text" : "test"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "itemCodeableConcept" : {
                "text" : "test"
              }
            }
        """.trimIndent()
        val ingredient = JacksonManager.objectMapper.readValue<Ingredient>(json)

        assertNull(ingredient.id)
        assertEquals(listOf<Extension>(), ingredient.extension)
        assertEquals(listOf<Extension>(), ingredient.modifierExtension)
        assertEquals(
            DynamicValue(DynamicValueType.CODEABLE_CONCEPT, CodeableConcept(text = FHIRString("test"))),
            ingredient.item
        )
        assertNull(ingredient.isActive)
        assertNull(ingredient.strength)
    }
}
