package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class CarePlanActivityTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val carePlanDetail = CarePlanDetail(
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
            kind = Code("Appointment"),
            instantiatesCanonical = listOf<Canonical>(
                Canonical(
                    value = "canonical"
                )
            ),
            instantiatesUri = Uri("uri"),
            code = CodeableConcept(
                coding = listOf(
                    Coding(
                        system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                        code = Code("DD"),
                        display = "Discharge diagnosis"
                    )
                )
            ),
            reasonCode = listOf(
                CodeableConcept(
                    coding = listOf(
                        Coding(
                            system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                            code = Code("DD"),
                            display = "Discharge diagnosis"
                        )
                    )
                )
            ),
            goal = listOf(
                Reference(reference = "ABC123")
            ),
            status = Code("scheduled"),
            statusReason = CodeableConcept(
                coding = listOf(
                    Coding(
                        system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                        code = Code("DD"),
                        display = "Discharge diagnosis"
                    )
                )
            ),
            doNotPerform = true,
            scheduled = DynamicValue(DynamicValueType.STRING, "Value"),
            location = Reference(reference = "DEF123"),
            performer = listOf(Reference(reference = "GHI123")),
            product = DynamicValue(DynamicValueType.CODEABLE_CONCEPT, CodeableConcept(text = "product")),
            dailyAmount = SimpleQuantity(value = 1.1),
            quantity = SimpleQuantity(value = 2.2),
            description = "Description"
        )
        val carePlanActivity = CarePlanActivity(
            id = "67890",
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
            outcomeCodeableConcept = listOf(
                CodeableConcept(
                    coding = listOf(
                        Coding(
                            system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                            code = Code("DD"),
                            display = "Discharge diagnosis"
                        )
                    )
                )
            ),
            outcomeReference = listOf(
                Reference(reference = "outcome")
            ),
            progress = listOf(
                Annotation(text = Markdown("123"))
            ),
            detail = carePlanDetail
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(carePlanActivity)

        val expectedJson = """
            {
              "id" : "67890",
              "extension" : [ {
                "url" : "http://localhost/extension",
                "valueString" : "Value"
              } ],
              "modifierExtension" : [ {
                "url" : "http://localhost/modifier-extension",
                "valueInteger" : 1
              } ],
              "outcomeCodeableConcept" : [ {
                "coding" : [ {
                  "system" : "http://terminology.hl7.org/CodeSystem/diagnosis-role",
                  "code" : "DD",
                  "display" : "Discharge diagnosis"
                } ]
              } ],
              "outcomeReference" : [ {
                "reference" : "outcome"
              } ],
              "progress" : [ {
                "text" : "123"
              } ],
              "detail" : {
                "id" : "12345",
                "extension" : [ {
                  "url" : "http://localhost/extension",
                  "valueString" : "Value"
                } ],
                "modifierExtension" : [ {
                  "url" : "http://localhost/modifier-extension",
                  "valueInteger" : 1
                } ],
                "kind" : "Appointment",
                "instantiatesCanonical" : [ "canonical" ],
                "instantiatesUri" : "uri",
                "code" : {
                  "coding" : [ {
                    "system" : "http://terminology.hl7.org/CodeSystem/diagnosis-role",
                    "code" : "DD",
                    "display" : "Discharge diagnosis"
                  } ]
                },
                "reasonCode" : [ {
                  "coding" : [ {
                    "system" : "http://terminology.hl7.org/CodeSystem/diagnosis-role",
                    "code" : "DD",
                    "display" : "Discharge diagnosis"
                  } ]
                } ],
                "goal" : [ {
                  "reference" : "ABC123"
                } ],
                "status" : "scheduled",
                "statusReason" : {
                  "coding" : [ {
                    "system" : "http://terminology.hl7.org/CodeSystem/diagnosis-role",
                    "code" : "DD",
                    "display" : "Discharge diagnosis"
                  } ]
                },
                "doNotPerform" : true,
                "scheduledString" : "Value",
                "location" : {
                  "reference" : "DEF123"
                },
                "performer" : [ {
                  "reference" : "GHI123"
                } ],
                "productCodeableConcept" : {
                  "text" : "product"
                },
                "dailyAmount" : {
                  "value" : 1.1
                },
                "quantity" : {
                  "value" : 2.2
                },
                "description" : "Description"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedCarePlanActivity = JacksonManager.objectMapper.readValue<CarePlanActivity>(json)
        assertEquals(carePlanActivity, deserializedCarePlanActivity)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val carePlanActivity = CarePlanActivity(
            reference = Reference(reference = "ABC123")
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(carePlanActivity)

        val expectedJson = """
            {
              "reference" : {
                "reference" : "ABC123"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "reference" : {
                "reference" : "ABC123"
              }
            }
        """.trimIndent()
        val carePlanActivity = JacksonManager.objectMapper.readValue<CarePlanActivity>(json)
        assertNull(carePlanActivity.id)
        assertEquals(listOf<Extension>(), carePlanActivity.extension)
        assertEquals(listOf<Extension>(), carePlanActivity.modifierExtension)
        assertEquals(listOf<CodeableConcept>(), carePlanActivity.outcomeCodeableConcept)
        assertEquals(listOf<Reference>(), carePlanActivity.outcomeReference)
        assertEquals(listOf<Annotation>(), carePlanActivity.progress)
        assertEquals(Reference(reference = "ABC123"), carePlanActivity.reference)
        assertNull(carePlanActivity.detail)
    }
}
