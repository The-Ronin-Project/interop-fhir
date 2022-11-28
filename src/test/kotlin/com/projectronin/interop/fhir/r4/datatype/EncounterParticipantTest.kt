package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class EncounterParticipantTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val encounterParticipant = EncounterParticipant(
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
                    value = DynamicValue(DynamicValueType.INTEGER, FHIRInteger(1))
                )
            ),
            type = listOf(
                CodeableConcept(
                    coding = listOf(
                        Coding(
                            system = Uri("https://www.hl7.org/fhir/R4/valueset-encounter-participant-type.html"),
                            code = Code("PPRT"),
                            display = FHIRString("Primary performer")
                        )
                    )
                )
            ),
            period = Period(
                start = DateTime(value = "2021-11-17T08:00:00Z"),
                end = DateTime(value = "2021-11-17T09:00:00Z")
            ),
            individual = Reference(
                reference = FHIRString("Practitioner/f001"),
                display = FHIRString("E.M. van den Broek")
            )
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounterParticipant)

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
              "type" : [ {
                "coding" : [ {
                  "system" : "https://www.hl7.org/fhir/R4/valueset-encounter-participant-type.html",
                  "code" : "PPRT",
                  "display" : "Primary performer"
                } ]
              } ],
              "period" : {
                "start" : "2021-11-17T08:00:00Z",
                "end" : "2021-11-17T09:00:00Z"
              },
              "individual" : {
                "reference" : "Practitioner/f001",
                "display" : "E.M. van den Broek"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedEncounterParticipant = objectMapper.readValue<EncounterParticipant>(json)
        assertEquals(encounterParticipant, deserializedEncounterParticipant)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val encounterParticipant = EncounterParticipant(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            individual = Reference(reference = FHIRString("Practitioner/f001"))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounterParticipant)

        val expectedJson = """
            {
              "id" : "12345",
              "extension" : [ {
                "url" : "http://localhost/extension",
                "valueString" : "Value"
              } ],
              "individual" : {
                "reference" : "Practitioner/f001"
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
              "individual" : {
                "reference" : "Practitioner/f001"
              }
            }
        """.trimIndent()
        val encounterParticipant = objectMapper.readValue<EncounterParticipant>(json)

        assertEquals(FHIRString("12345"), encounterParticipant.id)
        assertEquals(
            listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            encounterParticipant.extension
        )
        assertEquals(listOf<Extension>(), encounterParticipant.modifierExtension)
        assertEquals(listOf<CodeableConcept>(), encounterParticipant.type)
        assertNull(encounterParticipant.period)
        assertEquals(Reference(reference = FHIRString("Practitioner/f001")), encounterParticipant.individual)
    }
}
