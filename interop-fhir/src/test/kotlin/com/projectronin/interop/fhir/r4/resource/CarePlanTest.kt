package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.Annotation
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
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.CarePlanActivityStatus
import com.projectronin.interop.fhir.r4.valueset.CarePlanIntent
import com.projectronin.interop.fhir.r4.valueset.RequestStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class CarePlanTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val carePlanDetail =
            CarePlanDetail(
                id = FHIRString("12345"),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                modifierExtension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/modifier-extension"),
                            value = DynamicValue(DynamicValueType.INTEGER, FHIRInteger(1)),
                        ),
                    ),
                kind = Code("Appointment"),
                instantiatesCanonical =
                    listOf(
                        Canonical(
                            value = "canonical",
                        ),
                    ),
                instantiatesUri = listOf(Uri("uri")),
                code =
                    CodeableConcept(
                        coding =
                            listOf(
                                Coding(
                                    system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                                    code = Code("DD"),
                                    display = FHIRString("Discharge diagnosis"),
                                ),
                            ),
                    ),
                reasonCode =
                    listOf(
                        CodeableConcept(
                            coding =
                                listOf(
                                    Coding(
                                        system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                                        code = Code("DD"),
                                        display = FHIRString("Discharge diagnosis"),
                                    ),
                                ),
                        ),
                    ),
                goal =
                    listOf(
                        Reference(reference = FHIRString("ABC123")),
                    ),
                status = Code("scheduled"),
                statusReason =
                    CodeableConcept(
                        coding =
                            listOf(
                                Coding(
                                    system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                                    code = Code("DD"),
                                    display = FHIRString("Discharge diagnosis"),
                                ),
                            ),
                    ),
                doNotPerform = FHIRBoolean.TRUE,
                scheduled = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                location = Reference(reference = FHIRString("DEF123")),
                performer = listOf(Reference(reference = FHIRString("GHI123"))),
                product = DynamicValue(DynamicValueType.CODEABLE_CONCEPT, CodeableConcept(text = FHIRString("product"))),
                dailyAmount = SimpleQuantity(value = Decimal(1.1)),
                quantity = SimpleQuantity(value = Decimal(2.2)),
                description = FHIRString("Description"),
            )
        val carePlanActivity =
            CarePlanActivity(
                id = FHIRString("67890"),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                modifierExtension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/modifier-extension"),
                            value = DynamicValue(DynamicValueType.INTEGER, FHIRInteger(1)),
                        ),
                    ),
                outcomeCodeableConcept =
                    listOf(
                        CodeableConcept(
                            coding =
                                listOf(
                                    Coding(
                                        system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                                        code = Code("DD"),
                                        display = FHIRString("Discharge diagnosis"),
                                    ),
                                ),
                        ),
                    ),
                outcomeReference =
                    listOf(
                        Reference(reference = FHIRString("outcome")),
                    ),
                progress =
                    listOf(
                        Annotation(text = Markdown("123")),
                    ),
                detail = carePlanDetail,
            )
        val carePlan =
            CarePlan(
                id = Id("123"),
                meta =
                    Meta(
                        profile = listOf(Canonical("CarePlan")),
                    ),
                implicitRules = Uri("implicit-rules"),
                language = Code("en-US"),
                text =
                    Narrative(
                        status = com.projectronin.interop.fhir.r4.valueset.NarrativeStatus.GENERATED.asCode(),
                        div = FHIRString("div"),
                    ),
                contained = listOf(Location(id = Id("1234"), name = FHIRString("Contained Location"))),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                modifierExtension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/modifier-extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                identifier = listOf(Identifier(value = FHIRString("id"))),
                instantiatesCanonical =
                    listOf(
                        Canonical(
                            value = "canonical",
                        ),
                    ),
                instantiatesUri = listOf(Uri("uri")),
                basedOn = listOf(Reference(reference = FHIRString("reference"))),
                replaces = listOf(Reference(reference = FHIRString("reference"))),
                partOf = listOf(Reference(reference = FHIRString("reference"))),
                status = Code("active"),
                intent = Code("plan"),
                category =
                    listOf(
                        CodeableConcept(
                            coding =
                                listOf(
                                    Coding(
                                        system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                                        code = Code("DD"),
                                        display = FHIRString("Discharge diagnosis"),
                                    ),
                                ),
                        ),
                    ),
                title = FHIRString("care plan"),
                description = FHIRString("description"),
                subject = Reference(reference = FHIRString("XYZ123")),
                encounter = Reference(reference = FHIRString("12XYZ3")),
                period = Period(start = DateTime("2021"), end = DateTime("2022")),
                created = DateTime("2022"),
                author = Reference(reference = FHIRString("author123")),
                contributor =
                    listOf(
                        Reference(reference = FHIRString("contributor123")),
                    ),
                careTeam =
                    listOf(
                        Reference(reference = FHIRString("careteam123")),
                    ),
                addresses =
                    listOf(
                        Reference(reference = FHIRString("address123")),
                    ),
                supportingInfo =
                    listOf(
                        Reference(reference = FHIRString("supportingInfo123")),
                    ),
                goal =
                    listOf(
                        Reference(reference = FHIRString("goal123")),
                    ),
                activity = listOf(carePlanActivity),
                note =
                    listOf(
                        Annotation(
                            text = Markdown("potato"),
                        ),
                    ),
            )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(carePlan)
        val expectedJson =
            """
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
              "contained" : [ {
                "resourceType" : "Location",
                "id" : "1234",
                "name" : "Contained Location"
              } ],
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
                  "instantiatesUri" : [ "uri" ],
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
        val carePlan =
            CarePlan(
                status = Code("active"),
                intent = Code("plan"),
                subject = Reference(reference = FHIRString("XYZ123")),
            )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(carePlan)
        val expectedJson =
            """
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
        val json =
            """
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
        assertEquals(Reference(reference = FHIRString("XYZ123")), carePlan.subject)
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

class CarePlanActivityTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val carePlanDetail =
            CarePlanDetail(
                id = FHIRString("12345"),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                modifierExtension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/modifier-extension"),
                            value = DynamicValue(DynamicValueType.INTEGER, FHIRInteger(1)),
                        ),
                    ),
                kind = Code("Appointment"),
                instantiatesCanonical =
                    listOf(
                        Canonical(
                            value = "canonical",
                        ),
                    ),
                instantiatesUri = listOf(Uri("uri")),
                code =
                    CodeableConcept(
                        coding =
                            listOf(
                                Coding(
                                    system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                                    code = Code("DD"),
                                    display = FHIRString("Discharge diagnosis"),
                                ),
                            ),
                    ),
                reasonCode =
                    listOf(
                        CodeableConcept(
                            coding =
                                listOf(
                                    Coding(
                                        system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                                        code = Code("DD"),
                                        display = FHIRString("Discharge diagnosis"),
                                    ),
                                ),
                        ),
                    ),
                goal =
                    listOf(
                        Reference(reference = FHIRString("ABC123")),
                    ),
                status = Code("scheduled"),
                statusReason =
                    CodeableConcept(
                        coding =
                            listOf(
                                Coding(
                                    system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                                    code = Code("DD"),
                                    display = FHIRString("Discharge diagnosis"),
                                ),
                            ),
                    ),
                doNotPerform = FHIRBoolean.TRUE,
                scheduled = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                location = Reference(reference = FHIRString("DEF123")),
                performer = listOf(Reference(reference = FHIRString("GHI123"))),
                product = DynamicValue(DynamicValueType.CODEABLE_CONCEPT, CodeableConcept(text = FHIRString("product"))),
                dailyAmount = SimpleQuantity(value = Decimal(1.1)),
                quantity = SimpleQuantity(value = Decimal(2.2)),
                description = FHIRString("Description"),
            )
        val carePlanActivity =
            CarePlanActivity(
                id = FHIRString("67890"),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                modifierExtension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/modifier-extension"),
                            value = DynamicValue(DynamicValueType.INTEGER, FHIRInteger(1)),
                        ),
                    ),
                outcomeCodeableConcept =
                    listOf(
                        CodeableConcept(
                            coding =
                                listOf(
                                    Coding(
                                        system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                                        code = Code("DD"),
                                        display = FHIRString("Discharge diagnosis"),
                                    ),
                                ),
                        ),
                    ),
                outcomeReference =
                    listOf(
                        Reference(reference = FHIRString("outcome")),
                    ),
                progress =
                    listOf(
                        Annotation(text = Markdown("123")),
                    ),
                detail = carePlanDetail,
            )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(carePlanActivity)

        val expectedJson =
            """
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
                "instantiatesUri" : [ "uri" ],
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
        val carePlanActivity =
            CarePlanActivity(
                reference = Reference(reference = FHIRString("ABC123")),
            )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(carePlanActivity)

        val expectedJson =
            """
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
        val json =
            """
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
        assertEquals(Reference(reference = FHIRString("ABC123")), carePlanActivity.reference)
        assertNull(carePlanActivity.detail)
    }
}

class CarePlanDetailTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val carePlanDetail =
            CarePlanDetail(
                id = FHIRString("12345"),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                modifierExtension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/modifier-extension"),
                            value = DynamicValue(DynamicValueType.INTEGER, FHIRInteger(1)),
                        ),
                    ),
                kind = Code("Appointment"),
                instantiatesCanonical =
                    listOf(
                        Canonical(
                            value = "canonical",
                        ),
                    ),
                instantiatesUri = listOf(Uri("uri")),
                code =
                    CodeableConcept(
                        coding =
                            listOf(
                                Coding(
                                    system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                                    code = Code("DD"),
                                    display = FHIRString("Discharge diagnosis"),
                                ),
                            ),
                    ),
                reasonCode =
                    listOf(
                        CodeableConcept(
                            coding =
                                listOf(
                                    Coding(
                                        system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                                        code = Code("DD"),
                                        display = FHIRString("Discharge diagnosis"),
                                    ),
                                ),
                        ),
                    ),
                goal =
                    listOf(
                        Reference(reference = FHIRString("ABC123")),
                    ),
                status = Code("scheduled"),
                statusReason =
                    CodeableConcept(
                        coding =
                            listOf(
                                Coding(
                                    system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                                    code = Code("DD"),
                                    display = FHIRString("Discharge diagnosis"),
                                ),
                            ),
                    ),
                doNotPerform = FHIRBoolean.TRUE,
                scheduled = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                location = Reference(reference = FHIRString("DEF123")),
                performer = listOf(Reference(reference = FHIRString("GHI123"))),
                product = DynamicValue(DynamicValueType.CODEABLE_CONCEPT, CodeableConcept(text = FHIRString("product"))),
                dailyAmount = SimpleQuantity(value = Decimal(1.1)),
                quantity = SimpleQuantity(value = Decimal(2.2)),
                description = FHIRString("Description"),
            )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(carePlanDetail)
        val expectedJson =
            """
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
              "instantiatesUri" : [ "uri" ],
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
        val carePlanDetail =
            CarePlanDetail(
                status = Code("scheduled"),
            )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(carePlanDetail)

        val expectedJson =
            """
            {
              "status" : "scheduled"
            }
            """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
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
        assertEquals(listOf<Uri>(), carePlanDetail.instantiatesUri)
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
