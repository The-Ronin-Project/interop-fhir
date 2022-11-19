package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CarePlanActivity
import com.projectronin.interop.fhir.r4.datatype.CarePlanDetail
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.CarePlanIntent
import com.projectronin.interop.fhir.r4.valueset.RequestStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class CarePlanTest {
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
        val carePlan = CarePlan(
            id = Id("123"),
            meta = Meta(
                profile = listOf(Canonical("CarePlan")),
            ),
            implicitRules = Uri("implicit-rules"),
            language = Code("en-US"),
            text = Narrative(
                status = com.projectronin.interop.fhir.r4.valueset.NarrativeStatus.GENERATED.asCode(),
                div = "div"
            ),
            contained = listOf(ContainedResource("""{"resourceType":"CarePlan","field":"24680"}""")),
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
            identifier = listOf(Identifier(value = "id")),
            instantiatesCanonical = listOf<Canonical>(
                Canonical(
                    value = "canonical"
                )
            ),
            instantiatesUri = listOf(Uri("uri")),
            basedOn = listOf(Reference(reference = "reference")),
            replaces = listOf(Reference(reference = "reference")),
            partOf = listOf(Reference(reference = "reference")),
            status = Code("active"),
            intent = Code("plan"),
            category = listOf(
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
            title = "care plan",
            description = "description",
            subject = Reference(reference = "XYZ123"),
            encounter = Reference(reference = "12XYZ3"),
            period = Period(start = DateTime("2021"), end = DateTime("2022")),
            created = DateTime("2022"),
            author = Reference(reference = "author123"),
            contributor = listOf(
                Reference(reference = "contributor123")
            ),
            careTeam = listOf(
                Reference(reference = "careteam123")
            ),
            addresses = listOf(
                Reference(reference = "address123")
            ),
            supportingInfo = listOf(
                Reference(reference = "supportingInfo123")
            ),
            goal = listOf(
                Reference(reference = "goal123")
            ),
            activity = listOf(carePlanActivity),
            note = listOf(
                Annotation(
                    text = Markdown("potato")
                )
            )
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(carePlan)
        val expectedJson = """
            {
              "resourceType" : "CarePlan",
              "id" : "123",
              "meta" : {
                "profile" : [ "CarePlan" ]
              },
              "implicitRules" : "implicit-rules",
              "language" : "en-US",
              "text" : {
                "status" : "generated",
                "div" : "div"
              },
              "contained" : [ {"resourceType":"CarePlan","field":"24680"} ],
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
              "instantiatesCanonical" : [ "canonical" ],
              "instantiatesUri" : [ "uri" ],
              "basedOn" : [ {
                "reference" : "reference"
              } ],
              "replaces" : [ {
                "reference" : "reference"
              } ],
              "partOf" : [ {
                "reference" : "reference"
              } ],
              "status" : "active",
              "intent" : "plan",
              "category" : [ {
                "coding" : [ {
                  "system" : "http://terminology.hl7.org/CodeSystem/diagnosis-role",
                  "code" : "DD",
                  "display" : "Discharge diagnosis"
                } ]
              } ],
              "title" : "care plan",
              "description" : "description",
              "subject" : {
                "reference" : "XYZ123"
              },
              "encounter" : {
                "reference" : "12XYZ3"
              },
              "period" : {
                "start" : "2021",
                "end" : "2022"
              },
              "created" : "2022",
              "author" : {
                "reference" : "author123"
              },
              "contributor" : [ {
                "reference" : "contributor123"
              } ],
              "careTeam" : [ {
                "reference" : "careteam123"
              } ],
              "addresses" : [ {
                "reference" : "address123"
              } ],
              "supportingInfo" : [ {
                "reference" : "supportingInfo123"
              } ],
              "goal" : [ {
                "reference" : "goal123"
              } ],
              "activity" : [ {
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
              } ],
              "note" : [ {
                "text" : "potato"
              } ]
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedCarePlan = JacksonManager.objectMapper.readValue<CarePlan>(json)
        assertEquals(carePlan, deserializedCarePlan)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val carePlan = CarePlan(
            status = Code("active"),
            intent = Code("plan"),
            subject = Reference(reference = "XYZ123")
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(carePlan)
        val expectedJson = """
            {
              "resourceType" : "CarePlan",
              "status" : "active",
              "intent" : "plan",
              "subject" : {
                "reference" : "XYZ123"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "resourceType" : "CarePlan",
              "status" : "active",
              "intent" : "plan",
              "subject" : {
                "reference" : "XYZ123"
              }
            }
        """.trimIndent()
        val carePlan = JacksonManager.objectMapper.readValue<CarePlan>(json)
        assertNull(carePlan.id)
        assertNull(carePlan.meta)
        assertNull(carePlan.implicitRules)
        assertNull(carePlan.language)
        assertNull(carePlan.text)
        assertEquals(listOf<Resource<Nothing>>(), carePlan.contained)
        assertEquals(listOf<Extension>(), carePlan.extension)
        assertEquals(listOf<Extension>(), carePlan.modifierExtension)
        assertEquals(listOf<Identifier>(), carePlan.identifier)
        assertEquals(listOf<Canonical>(), carePlan.instantiatesCanonical)
        assertEquals(listOf<Uri>(), carePlan.instantiatesUri)
        assertEquals(listOf<Reference>(), carePlan.basedOn)
        assertEquals(listOf<Reference>(), carePlan.replaces)
        assertEquals(listOf<Reference>(), carePlan.partOf)
        assertEquals(RequestStatus.ACTIVE.asCode(), carePlan.status)
        assertEquals(CarePlanIntent.PLAN.asCode(), carePlan.intent)
        assertEquals(listOf<CodeableConcept>(), carePlan.category)
        assertNull(carePlan.title)
        assertNull(carePlan.description)
        assertEquals(Reference(reference = "XYZ123"), carePlan.subject)
        assertNull(carePlan.encounter)
        assertNull(carePlan.period)
        assertNull(carePlan.created)
        assertNull(carePlan.author)
        assertEquals(listOf<Reference>(), carePlan.contributor)
        assertEquals(listOf<Reference>(), carePlan.careTeam)
        assertEquals(listOf<Reference>(), carePlan.addresses)
        assertEquals(listOf<Reference>(), carePlan.supportingInfo)
        assertEquals(listOf<Reference>(), carePlan.goal)
        assertEquals(listOf<CarePlanActivity>(), carePlan.activity)
        assertEquals(listOf<Annotation>(), carePlan.note)
    }
}
