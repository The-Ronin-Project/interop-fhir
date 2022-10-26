package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.Duration
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.EncounterClassHistory
import com.projectronin.interop.fhir.r4.datatype.EncounterDiagnosis
import com.projectronin.interop.fhir.r4.datatype.EncounterHospitalization
import com.projectronin.interop.fhir.r4.datatype.EncounterLocation
import com.projectronin.interop.fhir.r4.datatype.EncounterParticipant
import com.projectronin.interop.fhir.r4.datatype.EncounterStatusHistory
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
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
                profile = listOf(Canonical("RoninEncounter")),
            ),
            implicitRules = Uri("implicit-rules"),
            language = Code("en-US"),
            text = Narrative(
                status = NarrativeStatus.GENERATED.asCode(),
                div = "div"
            ),
            contained = listOf(ContainedResource("""{"resourceType":"Banana","field":"24680"}""")),
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
                display = "ambulatory"
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
                            display = "Patient-initiated encounter"
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
                        display = "Non-urgent ear, nose and throat admission"
                    )
                )
            ),
            subject = Reference(
                reference = "Patient/f001",
                display = "P. van de Heuvel"
            ),
            episodeOfCare = emptyList(),
            basedOn = emptyList(),
            participant = listOf(
                EncounterParticipant(
                    individual = Reference(
                        reference = "Practitioner/f001",
                        display = "E.M. van den Broek"
                    )
                )
            ),
            appointment = emptyList(),
            period = null,
            length = Duration(
                value = 90.0,
                unit = "min",
                system = CodeSystem.UCUM.uri,
                code = Code("min")
            ),
            reasonCode = listOf(
                CodeableConcept(
                    coding = listOf(
                        Coding(
                            system = CodeSystem.SNOMED_CT.uri,
                            code = Code("18099001"),
                            display = "Retropharyngeal abscess"
                        )
                    )
                )
            ),
            reasonReference = listOf(
                Reference(
                    reference = "Condition/f001",
                    display = "Test Condition"
                )
            ),
            diagnosis = listOf(
                EncounterDiagnosis(
                    condition = Reference(reference = "Condition/stroke"),
                    use = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                                code = Code("AD"),
                                display = "Admission diagnosis"
                            )
                        )
                    ),
                    rank = PositiveInt(1)
                ),
                EncounterDiagnosis(
                    condition = Reference(reference = "Condition/f201"),
                    use = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                                code = Code("DD"),
                                display = "Discharge diagnosis"
                            )
                        )
                    )
                )
            ),
            account = listOf(Reference(reference = "Account/f001")),
            hospitalization = EncounterHospitalization(
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
            ),
            location = listOf(
                EncounterLocation(
                    location = Reference(reference = "Location/f001"),
                    status = EncounterLocationStatus.RESERVED.asCode(),
                    physicalType = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://terminology.hl7.org/CodeSystem/location-physical-type"),
                                code = Code("area"),
                                display = "Area"
                            )
                        )
                    )
                )
            ),
            serviceProvider = Reference(
                reference = "Organization/f001",
                display = "Community Hospital"
            ),
            partOf = Reference(reference = "Encounter/super")
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
                display = "observation"
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
                display = "observation"
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
