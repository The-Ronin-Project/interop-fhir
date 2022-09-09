package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class IngredientTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val ingredient = Ingredient(
            id = "12345",
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
            item = DynamicValue(DynamicValueType.CODEABLE_CONCEPT, CodeableConcept(text = "test")),
            isActive = true,
            strength = Ratio(
                numerator = Quantity(value = 1.0),
                denominator = Quantity(value = 1.0)
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
        val ingredient = Ingredient(item = DynamicValue(DynamicValueType.CODEABLE_CONCEPT, CodeableConcept(text = "test")))
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
        assertEquals(DynamicValue(DynamicValueType.CODEABLE_CONCEPT, CodeableConcept(text = "test")), ingredient.item)
        assertNull(ingredient.isActive)
        assertNull(ingredient.strength)
    }
}
