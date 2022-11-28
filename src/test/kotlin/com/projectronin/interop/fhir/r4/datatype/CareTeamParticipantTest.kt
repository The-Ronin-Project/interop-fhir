package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CareTeamParticipantTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val careTeamParticipant = CareTeamParticipant(
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
            role = listOf(
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
            member = Reference(
                reference = FHIRString("Practitioner/f001"),
                display = FHIRString("Dr. Van Nostrand")
            ),
            onBehalfOf = Reference(
                reference = FHIRString("Organization/orgid"),
                display = FHIRString("Juilliard, Hoffermanndale Clinic")
            ),
            period = Period(
                start = DateTime(value = "2021-11-17T08:00:00Z"),
                end = DateTime(value = "2021-11-17T09:00:00Z")
            ),

        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(careTeamParticipant)

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
              "role" : [ {
                "coding" : [ {
                  "system" : "https://www.hl7.org/fhir/R4/valueset-encounter-participant-type.html",
                  "code" : "PPRT",
                  "display" : "Primary performer"
                } ]
              } ],
              "member" : {
                "reference" : "Practitioner/f001",
                "display" : "Dr. Van Nostrand"
              },
              "onBehalfOf" : {
                "reference" : "Organization/orgid",
                "display" : "Juilliard, Hoffermanndale Clinic"
              },
              "period" : {
                "start" : "2021-11-17T08:00:00Z",
                "end" : "2021-11-17T09:00:00Z"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedCareTeamParticipant = JacksonManager.objectMapper.readValue<CareTeamParticipant>(json)
        assertEquals(careTeamParticipant, deserializedCareTeamParticipant)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val careTeamParticipant = CareTeamParticipant()
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(careTeamParticipant)

        val expectedJson = """
            { }
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
        val careTeamParticipant = JacksonManager.objectMapper.readValue<CareTeamParticipant>(json)

        assertEquals(FHIRString("12345"), careTeamParticipant.id)
    }
}
