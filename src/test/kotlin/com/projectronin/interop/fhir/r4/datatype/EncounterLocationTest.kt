package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.EncounterLocationStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class EncounterLocationTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val encounterLocation = EncounterLocation(
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
            location = Reference(reference = FHIRString("Location/f001")),
            status = EncounterLocationStatus.RESERVED.asCode(),
            physicalType = CodeableConcept(
                coding = listOf(
                    Coding(
                        system = Uri("http://terminology.hl7.org/CodeSystem/location-physical-type"),
                        code = Code("area"),
                        display = FHIRString("Area")
                    )
                )
            ),
            period = Period(
                start = DateTime(value = "2021-11-17T08:00:00Z"),
                end = DateTime(value = "2021-11-17T09:00:00Z")
            )
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounterLocation)

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
              "location" : {
                "reference" : "Location/f001"
              },
              "status" : "reserved",
              "physicalType" : {
                "coding" : [ {
                  "system" : "http://terminology.hl7.org/CodeSystem/location-physical-type",
                  "code" : "area",
                  "display" : "Area"
                } ]
              },
              "period" : {
                "start" : "2021-11-17T08:00:00Z",
                "end" : "2021-11-17T09:00:00Z"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedEncounterLocation = objectMapper.readValue<EncounterLocation>(json)
        assertEquals(encounterLocation, deserializedEncounterLocation)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val encounterLocation = EncounterLocation(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            location = Reference(reference = FHIRString("Location/f001")),
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounterLocation)

        val expectedJson = """
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
        val encounterLocation = objectMapper.readValue<EncounterLocation>(json)

        assertEquals(FHIRString("12345"), encounterLocation.id)
        assertEquals(
            listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            encounterLocation.extension
        )
        assertEquals(listOf<Extension>(), encounterLocation.modifierExtension)
        assertEquals(Reference(reference = FHIRString("Location/f001")), encounterLocation.location)
        assertNull(encounterLocation.status)
        assertNull(encounterLocation.physicalType)
        assertNull(encounterLocation.period)
    }
}
