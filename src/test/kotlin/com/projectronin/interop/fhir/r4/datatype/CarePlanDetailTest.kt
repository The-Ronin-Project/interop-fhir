package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.CarePlanActivityStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class CarePlanDetailTest {
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
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(carePlanDetail)
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
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedCarePlanDetail = JacksonManager.objectMapper.readValue<CarePlanDetail>(json)
        assertEquals(carePlanDetail, deserializedCarePlanDetail)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val carePlanDetail = CarePlanDetail(
            status = Code("scheduled")
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(carePlanDetail)

        val expectedJson = """
            {
              "status" : "scheduled"
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "status" : "scheduled"
            }
        """.trimIndent()
        val carePlanDetail = JacksonManager.objectMapper.readValue<CarePlanDetail>(json)
        assertNull(carePlanDetail.id)
        assertEquals(listOf<Extension>(), carePlanDetail.extension)
        assertEquals(listOf<Extension>(), carePlanDetail.modifierExtension)
        assertNull(carePlanDetail.kind)
        assertEquals(listOf<Canonical>(), carePlanDetail.instantiatesCanonical)
        assertNull(carePlanDetail.instantiatesUri)
        assertNull(carePlanDetail.code)
        assertEquals(listOf<CodeableConcept>(), carePlanDetail.reasonCode)
        assertEquals(listOf<Reference>(), carePlanDetail.reasonReference)
        assertEquals(listOf<Reference>(), carePlanDetail.goal)
        assertEquals(CarePlanActivityStatus.SCHEDULED.asCode(), carePlanDetail.status)
        assertNull(carePlanDetail.statusReason)
        assertNull(carePlanDetail.doNotPerform)
        assertNull(carePlanDetail.scheduled)
        assertNull(carePlanDetail.location)
        assertEquals(listOf<Reference>(), carePlanDetail.performer)
        assertNull(carePlanDetail.product)
        assertNull(carePlanDetail.dailyAmount)
        assertNull(carePlanDetail.quantity)
        assertNull(carePlanDetail.description)
    }
}
