package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class EncounterDiagnosisTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val encounterDiagnosis = EncounterDiagnosis(
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
                    value = DynamicValue(DynamicValueType.INTEGER, 1)
                )
            ),
            condition = Reference(reference = "Condition/f201"),
            use = CodeableConcept(
                coding = listOf(
                    Coding(
                        system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                        code = Code("DD"),
                        display = "Discharge diagnosis"
                    )
                )
            ),
            rank = PositiveInt(1)
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounterDiagnosis)

        val expectedJson = """
            {
              "id" : "12345",
              "extension" : [ {
                "url" : "http://localhost/extension",
                "valueString" : "Value"
              } ],
              "modifierExtension" : [ {
                "url" : "http://localhost/modifier-extension",
                "valueInteger" : 1
              } ],
              "condition" : {
                "reference" : "Condition/f201"
              },
              "use" : {
                "coding" : [ {
                  "system" : "http://terminology.hl7.org/CodeSystem/diagnosis-role",
                  "code" : "DD",
                  "display" : "Discharge diagnosis"
                } ]
              },
              "rank" : 1
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedEncounterDiagnosis = objectMapper.readValue<EncounterDiagnosis>(json)
        assertEquals(encounterDiagnosis, deserializedEncounterDiagnosis)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val encounterDiagnosis = EncounterDiagnosis(
            id = "12345",
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            condition = Reference(reference = "Condition/example"),
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounterDiagnosis)

        val expectedJson = """
            {
              "id" : "12345",
              "extension" : [ {
                "url" : "http://localhost/extension",
                "valueString" : "Value"
              } ],
              "condition" : {
                "reference" : "Condition/example"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "id" : "12345",
              "extension" : [ {
                "url" : "http://localhost/extension",
                "valueString" : "Value"
              } ],
              "condition" : {
                "reference" : "Condition/example"
              }
            }
        """.trimIndent()
        val encounterDiagnosis = objectMapper.readValue<EncounterDiagnosis>(json)

        assertEquals("12345", encounterDiagnosis.id)
        assertEquals(
            listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            encounterDiagnosis.extension
        )
        assertEquals(listOf<Extension>(), encounterDiagnosis.modifierExtension)
        assertEquals(Reference(reference = "Condition/example"), encounterDiagnosis.condition)
        assertNull(encounterDiagnosis.use)
        assertNull(encounterDiagnosis.rank)
    }
}
