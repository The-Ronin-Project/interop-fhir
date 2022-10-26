package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class EncounterHospitalizationTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val encounterHospitalization = EncounterHospitalization(
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
            preAdmissionIdentifier = Identifier(
                use = Code("official"),
                system = Uri("http://www.bmc.nl/zorgportal/identifiers/pre-admissions"),
                value = "93042"
            ),
            origin = null,
            admitSource = CodeableConcept(
                coding = listOf(
                    Coding(
                        system = CodeSystem.SNOMED_CT.uri,
                        code = Code("305956004"),
                        display = "Referral by physician"
                    )
                )
            ),
            reAdmission = null,
            dietPreference = listOf(
                CodeableConcept(
                    coding = listOf(
                        Coding(
                            system = Uri("https://www.hl7.org/fhir/R4/valueset-encounter-diet.html"),
                            code = Code("vegetarian"),
                            display = "vegetarian"
                        )
                    )
                ),
                CodeableConcept(
                    coding = listOf(
                        Coding(
                            system = Uri("https://www.hl7.org/fhir/R4/valueset-encounter-diet.html"),
                            code = Code("kosher"),
                            display = "kosher"
                        )
                    )
                )
            ),
            specialCourtesy = emptyList(),
            specialArrangement = emptyList(),
            destination = Reference(reference = "Location/place"),
            dischargeDisposition = CodeableConcept(
                coding = listOf(
                    Coding(
                        system = CodeSystem.SNOMED_CT.uri,
                        code = Code("306689006"),
                        display = "Discharge to home"
                    )
                )
            )
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounterHospitalization)

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
              "preAdmissionIdentifier" : {
                "use" : "official",
                "system" : "http://www.bmc.nl/zorgportal/identifiers/pre-admissions",
                "value" : "93042"
              },
              "admitSource" : {
                "coding" : [ {
                  "system" : "http://snomed.info/sct",
                  "code" : "305956004",
                  "display" : "Referral by physician"
                } ]
              },
              "dietPreference" : [ {
                "coding" : [ {
                  "system" : "https://www.hl7.org/fhir/R4/valueset-encounter-diet.html",
                  "code" : "vegetarian",
                  "display" : "vegetarian"
                } ]
              }, {
                "coding" : [ {
                  "system" : "https://www.hl7.org/fhir/R4/valueset-encounter-diet.html",
                  "code" : "kosher",
                  "display" : "kosher"
                } ]
              } ],
              "destination" : {
                "reference" : "Location/place"
              },
              "dischargeDisposition" : {
                "coding" : [ {
                  "system" : "http://snomed.info/sct",
                  "code" : "306689006",
                  "display" : "Discharge to home"
                } ]
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedEncounterHospitalization = objectMapper.readValue<EncounterHospitalization>(json)
        assertEquals(encounterHospitalization, deserializedEncounterHospitalization)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val encounterHospitalization = EncounterHospitalization(
            id = "12345",
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            )
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounterHospitalization)

        val expectedJson = """
            {
              "id" : "12345",
              "extension" : [ {
                "url" : "http://localhost/extension",
                "valueString" : "Value"
              } ]
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
              "location" : {
                "reference" : "Location/f001"
              }
            }
        """.trimIndent()
        val encounterHospitalization = objectMapper.readValue<EncounterHospitalization>(json)

        assertEquals("12345", encounterHospitalization.id)
        assertEquals(
            listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            encounterHospitalization.extension
        )
        assertEquals(listOf<Extension>(), encounterHospitalization.modifierExtension)
        assertNull(encounterHospitalization.preAdmissionIdentifier)
        assertNull(encounterHospitalization.origin)
        assertNull(encounterHospitalization.admitSource)
        assertNull(encounterHospitalization.reAdmission)
        assertEquals(emptyList<CodeableConcept>(), encounterHospitalization.dietPreference)
        assertEquals(emptyList<CodeableConcept>(), encounterHospitalization.specialCourtesy)
        assertEquals(emptyList<CodeableConcept>(), encounterHospitalization.specialArrangement)
        assertNull(encounterHospitalization.destination)
        assertNull(encounterHospitalization.dischargeDisposition)
    }
}
