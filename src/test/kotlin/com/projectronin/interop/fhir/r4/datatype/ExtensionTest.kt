package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ExtensionTest {
    @Test
    fun `can serialize and deserialize JSON - extension`() {
        val extension = Extension(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            url = Uri("http://hl7.org/fhir/StructureDefinition/iso-21090-EN-use"),
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(extension)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "url" : "http://hl7.org/fhir/StructureDefinition/iso-21090-EN-use"
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedExtension = objectMapper.readValue<Extension>(json)
        assertEquals(extension, deserializedExtension)
    }

    @Test
    fun `can serialize and deserialize JSON - value`() {
        val extension = Extension(
            id = FHIRString("12345"),
            url = Uri("http://hl7.org/fhir/StructureDefinition/iso-21090-EN-use"),
            value = DynamicValue(DynamicValueType.CODE, Code("I"))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(extension)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "url" : "http://hl7.org/fhir/StructureDefinition/iso-21090-EN-use",
            |  "valueCode" : "I"
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedExtension = objectMapper.readValue<Extension>(json)
        assertEquals(extension, deserializedExtension)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val extension = Extension(
            url = Uri("http://hl7.org/fhir/StructureDefinition/iso-21090-EN-use"),
            value = DynamicValue(DynamicValueType.CODE, Code("I"))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(extension)

        val expectedJson = """
            |{
            |  "url" : "http://hl7.org/fhir/StructureDefinition/iso-21090-EN-use",
            |  "valueCode" : "I"
            |}""".trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "url" : "http://hl7.org/fhir/StructureDefinition/iso-21090-EN-use",
            |  "valueCode" : "I"
            |}""".trimMargin()
        val extension = objectMapper.readValue<Extension>(json)

        assertNull(extension.id)
        assertEquals(listOf<Extension>(), extension.extension)
        assertEquals(Uri("http://hl7.org/fhir/StructureDefinition/iso-21090-EN-use"), extension.url)
        assertEquals(DynamicValue(DynamicValueType.CODE, Code("I")), extension.value)
    }

    @Test
    fun `can deserialize complex extension set whose children are a mix of extension and value cases`() {
        val json = """
            {
              "url": "complex",
              "extension": [
                {
                  "url": "http://hl7.org/fhir/us/core/StructureDefinition/us-core-race",
                  "extension": [
                    {
                      "url": "ombCategory",
                      "valueCoding": {
                        "system": "http://terminology.hl7.org/CodeSystem/v3-NullFlavor",
                        "code": "UNK",
                        "display": "Unknown"
                      }
                    },
                    {
                      "url": "citizenship",
                      "extension": [
                        {
                          "url": "http://hl7.org/fhir/StructureDefinition/patient-citizenship",
                          "extension": [
                            {
                              "url": "code",
                              "valueCodeableConcept": {
                                "coding": [
                                  {
                                    "system": "urn:iso:std:iso:3166",
                                    "code": "DE"
                                  }
                                ]
                              }
                            },
                            {
                              "url": "period",
                              "valuePeriod": {
                                "start": "2009-03-14"
                              }
                            },
                            {
                              "url": "http://acme.org/fhir/StructureDefinition/passport-number",
                              "valueString": "12345ABC"
                            }
                          ]
                        }
                      ]
                    }
                  ]
                },
                {
                  "url": "http://hl7.org/fhir/us/core/StructureDefinition/us-core-ethnicity",
                  "extension": [
                    {
                      "url": "ombCategory",
                      "valueCoding": {
                        "system": "urn:oid:2.16.840.1.113883.6.238",
                        "code": "2186-5",
                        "display": "Not Hispanic or Latino"
                      }
                    },
                    {
                      "url": "text",
                      "valueString": "Not Hispanic or Latino"
                    }
                  ]
                },
                {
                  "url": "http://open.epic.com/FHIR/StructureDefinition/extension/legal-sex",
                  "valueCodeableConcept": {
                    "coding": [
                      {
                        "system": "urn:oid:1.2.840.114350.1.13.0.1.7.10.698084.130.657370.19999000",
                        "code": "female",
                        "display": "female"
                      }
                    ]
                  }
                },
                {
                  "url": "http://hl7.org/fhir/StructureDefinition/patient-genderIdentity",
                  "valueCodeableConcept": {
                    "coding": [
                      {
                        "system": "http://hl7.org/fhir/gender-identity",
                        "code": "transgender-female",
                        "display": "transgender-female"
                      }
                    ]
                  }
                },
                {
                  "url": "http://hl7.org/fhir/us/core/StructureDefinition/us-core-birthsex",
                  "valueCode": "F"
                }
              ]
            }
        """.trimIndent()

        val deserializedExtension = objectMapper.readValue<Extension>(json)
        assertEquals(5, deserializedExtension.extension.size)
        assertEquals(2, deserializedExtension.extension.get(0).extension.size)
        assertEquals(2, deserializedExtension.extension.get(1).extension.size)
        assertEquals(3, deserializedExtension.extension.get(0).extension.get(1).extension.get(0).extension.size)
        assertEquals(
            "http://hl7.org/fhir/StructureDefinition/patient-genderIdentity",
            deserializedExtension.extension.get(3).url!!.value
        )
    }
}
