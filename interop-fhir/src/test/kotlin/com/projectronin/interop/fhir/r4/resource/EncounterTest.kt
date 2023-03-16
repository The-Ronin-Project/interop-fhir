package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.Duration
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.EncounterLocationStatus
import com.projectronin.interop.fhir.r4.valueset.EncounterStatus
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class EncounterTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val encounter = Encounter(
            id = Id("12345"),
            meta = Meta(
                profile = listOf(Canonical("RoninEncounter"))
            ),
            implicitRules = Uri("implicit-rules"),
            language = Code("en-US"),
            text = Narrative(
                status = NarrativeStatus.GENERATED.asCode(),
                div = FHIRString("div")
            ),
            contained = listOf(ContainedResource("""{"resourceType":"Banana","field":"24680"}""")),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            modifierExtension = listOf(
                Extension(
                    url = Uri("http://localhost/modifier-extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            identifier = listOf(Identifier(value = FHIRString("id"))),
            status = EncounterStatus.FINISHED.asCode(),
            statusHistory = listOf(
                EncounterStatusHistory(
                    status = EncounterStatus.PLANNED.asCode(),
                    period = Period(
                        start = DateTime(value = "2021-11-16"),
                        end = DateTime(value = "2021-11-17T08:00:00Z")
                    )
                ),
                EncounterStatusHistory(
                    status = EncounterStatus.ARRIVED.asCode(),
                    period = Period(
                        start = DateTime(value = "2021-11-17T08:00:00Z"),
                        end = DateTime(value = "2021-11-17T09:00:00Z")
                    )
                ),
                EncounterStatusHistory(
                    status = EncounterStatus.IN_PROGRESS.asCode(),
                    period = Period(
                        start = DateTime(value = "2021-11-17T09:00:00Z"),
                        end = DateTime(value = "2021-11-17T10:00:00Z")
                    )
                ),
                EncounterStatusHistory(
                    status = EncounterStatus.FINISHED.asCode(),
                    period = Period(
                        start = DateTime(value = "2021-11-17T10:00:00Z")
                    )
                )
            ),
            `class` = Coding(
                system = Uri("http://terminology.hl7.org/CodeSystem/v3-ActCode"),
                code = Code("AMB"),
                display = FHIRString("ambulatory")
            ),
            classHistory = listOf(
                EncounterClassHistory(
                    `class` = Code("AMB"),
                    period = Period(
                        start = DateTime(value = "2021-11-16")
                    )
                )
            ),
            type = listOf(
                CodeableConcept(
                    coding = listOf(
                        Coding(
                            system = CodeSystem.SNOMED_CT.uri,
                            code = Code("270427003"),
                            display = FHIRString("Patient-initiated encounter")
                        )
                    )
                )
            ),
            serviceType = null,
            priority = CodeableConcept(
                coding = listOf(
                    Coding(
                        system = CodeSystem.SNOMED_CT.uri,
                        code = Code("103391001"),
                        display = FHIRString("Non-urgent ear, nose and throat admission")
                    )
                )
            ),
            subject = Reference(
                reference = FHIRString("Patient/f001"),
                display = FHIRString("P. van de Heuvel")
            ),
            episodeOfCare = emptyList(),
            basedOn = emptyList(),
            participant = listOf(
                EncounterParticipant(
                    individual = Reference(
                        reference = FHIRString("Practitioner/f001"),
                        display = FHIRString("E.M. van den Broek")
                    )
                )
            ),
            appointment = emptyList(),
            period = null,
            length = Duration(
                value = Decimal(90.0),
                unit = FHIRString("min"),
                system = CodeSystem.UCUM.uri,
                code = Code("min")
            ),
            reasonCode = listOf(
                CodeableConcept(
                    coding = listOf(
                        Coding(
                            system = CodeSystem.SNOMED_CT.uri,
                            code = Code("18099001"),
                            display = FHIRString("Retropharyngeal abscess")
                        )
                    )
                )
            ),
            reasonReference = listOf(
                Reference(
                    reference = FHIRString("Condition/f001"),
                    display = FHIRString("Test Condition")
                )
            ),
            diagnosis = listOf(
                EncounterDiagnosis(
                    condition = Reference(reference = FHIRString("Condition/stroke")),
                    use = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                                code = Code("AD"),
                                display = FHIRString("Admission diagnosis")
                            )
                        )
                    ),
                    rank = PositiveInt(1)
                ),
                EncounterDiagnosis(
                    condition = Reference(reference = FHIRString("Condition/f201")),
                    use = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                                code = Code("DD"),
                                display = FHIRString("Discharge diagnosis")
                            )
                        )
                    )
                )
            ),
            account = listOf(Reference(reference = FHIRString("Account/f001"))),
            hospitalization = EncounterHospitalization(
                preAdmissionIdentifier = Identifier(
                    use = Code("official"),
                    system = Uri("http://www.bmc.nl/zorgportal/identifiers/pre-admissions"),
                    value = FHIRString("93042")
                ),
                origin = null,
                admitSource = CodeableConcept(
                    coding = listOf(
                        Coding(
                            system = CodeSystem.SNOMED_CT.uri,
                            code = Code("305956004"),
                            display = FHIRString("Referral by physician")
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
                                display = FHIRString("vegetarian")
                            )
                        )
                    ),
                    CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("https://www.hl7.org/fhir/R4/valueset-encounter-diet.html"),
                                code = Code("kosher"),
                                display = FHIRString("kosher")
                            )
                        )
                    )
                ),
                specialCourtesy = emptyList(),
                specialArrangement = emptyList(),
                destination = Reference(reference = FHIRString("Location/place")),
                dischargeDisposition = CodeableConcept(
                    coding = listOf(
                        Coding(
                            system = CodeSystem.SNOMED_CT.uri,
                            code = Code("306689006"),
                            display = FHIRString("Discharge to home")
                        )
                    )
                )
            ),
            location = listOf(
                EncounterLocation(
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
                    )
                )
            ),
            serviceProvider = Reference(
                reference = FHIRString("Organization/f001"),
                display = FHIRString("Community Hospital")
            ),
            partOf = Reference(reference = FHIRString("Encounter/super"))
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounter)

        val expectedJson = """
            {
              "resourceType" : "Encounter",
              "id" : "12345",
              "meta" : {
                "profile" : [ "RoninEncounter" ]
              },
              "implicitRules" : "implicit-rules",
              "language" : "en-US",
              "text" : {
                "status" : "generated",
                "div" : "div"
              },
              "contained" : [ {"resourceType":"Banana","field":"24680"} ],
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
              "status" : "finished",
              "statusHistory" : [ {
                "status" : "planned",
                "period" : {
                  "start" : "2021-11-16",
                  "end" : "2021-11-17T08:00:00Z"
                }
              }, {
                "status" : "arrived",
                "period" : {
                  "start" : "2021-11-17T08:00:00Z",
                  "end" : "2021-11-17T09:00:00Z"
                }
              }, {
                "status" : "in-progress",
                "period" : {
                  "start" : "2021-11-17T09:00:00Z",
                  "end" : "2021-11-17T10:00:00Z"
                }
              }, {
                "status" : "finished",
                "period" : {
                  "start" : "2021-11-17T10:00:00Z"
                }
              } ],
              "class" : {
                "system" : "http://terminology.hl7.org/CodeSystem/v3-ActCode",
                "code" : "AMB",
                "display" : "ambulatory"
              },
              "classHistory" : [ {
                "class" : "AMB",
                "period" : {
                  "start" : "2021-11-16"
                }
              } ],
              "type" : [ {
                "coding" : [ {
                  "system" : "http://snomed.info/sct",
                  "code" : "270427003",
                  "display" : "Patient-initiated encounter"
                } ]
              } ],
              "priority" : {
                "coding" : [ {
                  "system" : "http://snomed.info/sct",
                  "code" : "103391001",
                  "display" : "Non-urgent ear, nose and throat admission"
                } ]
              },
              "subject" : {
                "reference" : "Patient/f001",
                "display" : "P. van de Heuvel"
              },
              "participant" : [ {
                "individual" : {
                  "reference" : "Practitioner/f001",
                  "display" : "E.M. van den Broek"
                }
              } ],
              "length" : {
                "value" : 90.0,
                "unit" : "min",
                "system" : "http://unitsofmeasure.org",
                "code" : "min"
              },
              "reasonCode" : [ {
                "coding" : [ {
                  "system" : "http://snomed.info/sct",
                  "code" : "18099001",
                  "display" : "Retropharyngeal abscess"
                } ]
              } ],
              "reasonReference" : [ {
                "reference" : "Condition/f001",
                "display" : "Test Condition"
              } ],
              "diagnosis" : [ {
                "condition" : {
                  "reference" : "Condition/stroke"
                },
                "use" : {
                  "coding" : [ {
                    "system" : "http://terminology.hl7.org/CodeSystem/diagnosis-role",
                    "code" : "AD",
                    "display" : "Admission diagnosis"
                  } ]
                },
                "rank" : 1
              }, {
                "condition" : {
                  "reference" : "Condition/f201"
                },
                "use" : {
                  "coding" : [ {
                    "system" : "http://terminology.hl7.org/CodeSystem/diagnosis-role",
                    "code" : "DD",
                    "display" : "Discharge diagnosis"
                  } ]
                }
              } ],
              "account" : [ {
                "reference" : "Account/f001"
              } ],
              "hospitalization" : {
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
              },
              "location" : [ {
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
                }
              } ],
              "serviceProvider" : {
                "reference" : "Organization/f001",
                "display" : "Community Hospital"
              },
              "partOf" : {
                "reference" : "Encounter/super"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedEncounter = JacksonManager.objectMapper.readValue<Encounter>(json)
        assertEquals(encounter, deserializedEncounter)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val encounter = Encounter(
            status = EncounterStatus.CANCELLED.asCode(),
            `class` = Coding(
                system = Uri("http://terminology.hl7.org/CodeSystem/v3-ActCode"),
                code = Code("OBSENC"),
                display = FHIRString("observation")
            )
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounter)

        val expectedJson = """
            {
              "resourceType" : "Encounter",
              "status" : "cancelled",
              "class" : {
                "system" : "http://terminology.hl7.org/CodeSystem/v3-ActCode",
                "code" : "OBSENC",
                "display" : "observation"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "resourceType" : "Encounter",
              "status" : "cancelled",
              "class" : {
                "system" : "http://terminology.hl7.org/CodeSystem/v3-ActCode",
                "code" : "OBSENC",
                "display" : "observation"
              }
            }
        """.trimIndent()
        val encounter = JacksonManager.objectMapper.readValue<Encounter>(json)

        assertNull(encounter.id)
        assertNull(encounter.meta)
        assertNull(encounter.implicitRules)
        assertNull(encounter.language)
        assertNull(encounter.text)
        assertEquals(listOf<Resource<Nothing>>(), encounter.contained)
        assertEquals(listOf<Extension>(), encounter.extension)
        assertEquals(listOf<Extension>(), encounter.modifierExtension)
        assertEquals(listOf<Identifier>(), encounter.identifier)
        assertEquals(EncounterStatus.CANCELLED.asCode(), encounter.status)
        assertEquals(listOf<EncounterStatusHistory>(), encounter.statusHistory)
        assertEquals(
            Coding(
                system = Uri("http://terminology.hl7.org/CodeSystem/v3-ActCode"),
                code = Code("OBSENC"),
                display = FHIRString("observation")
            ),
            encounter.`class`
        )
        assertEquals(listOf<EncounterClassHistory>(), encounter.classHistory)
        assertEquals(listOf<CodeableConcept>(), encounter.type)
        assertNull(encounter.serviceType)
        assertNull(encounter.priority)
        assertNull(encounter.subject)
        assertEquals(listOf<Reference>(), encounter.episodeOfCare)
        assertEquals(listOf<Reference>(), encounter.basedOn)
        assertEquals(listOf<EncounterParticipant>(), encounter.participant)
        assertEquals(listOf<Reference>(), encounter.appointment)
        assertNull(encounter.period)
        assertNull(encounter.length)
        assertEquals(listOf<CodeableConcept>(), encounter.reasonCode)
        assertEquals(listOf<Reference>(), encounter.reasonReference)
        assertEquals(listOf<EncounterDiagnosis>(), encounter.diagnosis)
        assertEquals(listOf<Reference>(), encounter.account)
        assertNull(encounter.hospitalization)
        assertEquals(listOf<EncounterLocation>(), encounter.location)
        assertNull(encounter.serviceProvider)
        assertNull(encounter.partOf)
    }
}

class EncounterClassHistoryTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val encounterClassHistory = EncounterClassHistory(
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
            `class` = Code("PRENC"),
            period = Period(
                start = DateTime(value = "2021-11-17T08:00:00Z"),
                end = DateTime(value = "2021-11-17T09:00:00Z")
            )
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounterClassHistory)

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
              "class" : "PRENC",
              "period" : {
                "start" : "2021-11-17T08:00:00Z",
                "end" : "2021-11-17T09:00:00Z"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedEncounterClassHistory = JacksonManager.objectMapper.readValue<EncounterClassHistory>(json)
        assertEquals(encounterClassHistory, deserializedEncounterClassHistory)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val encounterClassHistory = EncounterClassHistory(
            `class` = Code("PRENC"),
            period = Period(
                start = DateTime(value = "2021-11-17T08:00:00Z"),
                end = DateTime(value = "2021-11-17T09:00:00Z")
            )
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounterClassHistory)

        val expectedJson = """
            {
              "class" : "PRENC",
              "period" : {
                "start" : "2021-11-17T08:00:00Z",
                "end" : "2021-11-17T09:00:00Z"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "class" : "PRENC",
              "period" : {
                "start" : "2021-11-17T08:00:00Z",
                "end" : "2021-11-17T09:00:00Z"
              }
            }
        """.trimIndent()
        val encounterClassHistory = JacksonManager.objectMapper.readValue<EncounterClassHistory>(json)

        assertNull(encounterClassHistory.id)
        assertEquals(listOf<Extension>(), encounterClassHistory.extension)
        assertEquals(listOf<Extension>(), encounterClassHistory.modifierExtension)
        assertEquals(Code("PRENC"), encounterClassHistory.`class`)
        assertEquals(
            Period(
                start = DateTime(value = "2021-11-17T08:00:00Z"),
                end = DateTime(value = "2021-11-17T09:00:00Z")
            ),
            encounterClassHistory.period
        )
    }
}

class EncounterDiagnosisTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val encounterDiagnosis = EncounterDiagnosis(
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
            condition = Reference(reference = FHIRString("Condition/f201")),
            use = CodeableConcept(
                coding = listOf(
                    Coding(
                        system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                        code = Code("DD"),
                        display = FHIRString("Discharge diagnosis")
                    )
                )
            ),
            rank = PositiveInt(1)
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounterDiagnosis)

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

        val deserializedEncounterDiagnosis = JacksonManager.objectMapper.readValue<EncounterDiagnosis>(json)
        assertEquals(encounterDiagnosis, deserializedEncounterDiagnosis)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val encounterDiagnosis = EncounterDiagnosis(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            condition = Reference(reference = FHIRString("Condition/example"))
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounterDiagnosis)

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
        val encounterDiagnosis = JacksonManager.objectMapper.readValue<EncounterDiagnosis>(json)

        assertEquals(FHIRString("12345"), encounterDiagnosis.id)
        assertEquals(
            listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            encounterDiagnosis.extension
        )
        assertEquals(listOf<Extension>(), encounterDiagnosis.modifierExtension)
        assertEquals(Reference(reference = FHIRString("Condition/example")), encounterDiagnosis.condition)
        assertNull(encounterDiagnosis.use)
        assertNull(encounterDiagnosis.rank)
    }
}

class EncounterHospitalizationTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val encounterHospitalization = EncounterHospitalization(
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
            preAdmissionIdentifier = Identifier(
                use = Code("official"),
                system = Uri("http://www.bmc.nl/zorgportal/identifiers/pre-admissions"),
                value = FHIRString("93042")
            ),
            origin = null,
            admitSource = CodeableConcept(
                coding = listOf(
                    Coding(
                        system = CodeSystem.SNOMED_CT.uri,
                        code = Code("305956004"),
                        display = FHIRString("Referral by physician")
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
                            display = FHIRString("vegetarian")
                        )
                    )
                ),
                CodeableConcept(
                    coding = listOf(
                        Coding(
                            system = Uri("https://www.hl7.org/fhir/R4/valueset-encounter-diet.html"),
                            code = Code("kosher"),
                            display = FHIRString("kosher")
                        )
                    )
                )
            ),
            specialCourtesy = emptyList(),
            specialArrangement = emptyList(),
            destination = Reference(reference = FHIRString("Location/place")),
            dischargeDisposition = CodeableConcept(
                coding = listOf(
                    Coding(
                        system = CodeSystem.SNOMED_CT.uri,
                        code = Code("306689006"),
                        display = FHIRString("Discharge to home")
                    )
                )
            )
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounterHospitalization)

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

        val deserializedEncounterHospitalization = JacksonManager.objectMapper.readValue<EncounterHospitalization>(json)
        assertEquals(encounterHospitalization, deserializedEncounterHospitalization)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val encounterHospitalization = EncounterHospitalization(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            )
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounterHospitalization)

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
        val encounterHospitalization = JacksonManager.objectMapper.readValue<EncounterHospitalization>(json)

        assertEquals(FHIRString("12345"), encounterHospitalization.id)
        assertEquals(
            listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
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
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounterLocation)

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

        val deserializedEncounterLocation = JacksonManager.objectMapper.readValue<EncounterLocation>(json)
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
            location = Reference(reference = FHIRString("Location/f001"))
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounterLocation)

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
        val encounterLocation = JacksonManager.objectMapper.readValue<EncounterLocation>(json)

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
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounterParticipant)

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

        val deserializedEncounterParticipant = JacksonManager.objectMapper.readValue<EncounterParticipant>(json)
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
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounterParticipant)

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
        val encounterParticipant = JacksonManager.objectMapper.readValue<EncounterParticipant>(json)

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

class EncounterStatusHistoryTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val encounterStatusHistory = EncounterStatusHistory(
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
            status = EncounterStatus.PLANNED.asCode(),
            period = Period(
                start = DateTime(value = "2021-11-17T08:00:00Z"),
                end = DateTime(value = "2021-11-17T09:00:00Z")
            )
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounterStatusHistory)

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
              "status" : "planned",
              "period" : {
                "start" : "2021-11-17T08:00:00Z",
                "end" : "2021-11-17T09:00:00Z"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedEncounterStatusHistory = JacksonManager.objectMapper.readValue<EncounterStatusHistory>(json)
        assertEquals(encounterStatusHistory, deserializedEncounterStatusHistory)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val encounterStatusHistory = EncounterStatusHistory(
            status = EncounterStatus.ARRIVED.asCode(),
            period = Period(
                start = DateTime(value = "2021-11-17T08:00:00Z"),
                end = DateTime(value = "2021-11-17T09:00:00Z")
            )
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounterStatusHistory)

        val expectedJson = """
            {
              "status" : "arrived",
              "period" : {
                "start" : "2021-11-17T08:00:00Z",
                "end" : "2021-11-17T09:00:00Z"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "status" : "in-progress",
              "period" : {
                "start" : "2021-11-17T08:00:00Z",
                "end" : "2021-11-17T09:00:00Z"
              }
            }
        """.trimIndent()
        val encounterStatusHistory = JacksonManager.objectMapper.readValue<EncounterStatusHistory>(json)

        assertNull(encounterStatusHistory.id)
        assertEquals(listOf<Extension>(), encounterStatusHistory.extension)
        assertEquals(listOf<Extension>(), encounterStatusHistory.modifierExtension)
        assertEquals(EncounterStatus.IN_PROGRESS.asCode(), encounterStatusHistory.status)
        assertEquals(
            Period(
                start = DateTime(value = "2021-11-17T08:00:00Z"),
                end = DateTime(value = "2021-11-17T09:00:00Z")
            ),
            encounterStatusHistory.period
        )
    }
}
