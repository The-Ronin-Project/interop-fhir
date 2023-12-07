package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Date
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.ImmunizationStatus
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ImmunizatioNTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val immunization =
            Immunization(
                id = Id("12345"),
                meta =
                    Meta(
                        profile = listOf(Canonical("RoninImmunization")),
                    ),
                implicitRules = Uri("implicit-rules"),
                language = Code("en-US"),
                text =
                    Narrative(
                        status = NarrativeStatus.GENERATED.asCode(),
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
                status = ImmunizationStatus.COMPLETED.asCode(),
                statusReason = CodeableConcept(text = FHIRString("status reason")),
                vaccineCode = CodeableConcept(text = FHIRString("vaccine code")),
                patient = Reference(display = FHIRString("patient")),
                encounter = Reference(display = FHIRString("encounter")),
                occurrence = DynamicValue(DynamicValueType.STRING, FHIRString("occurrence")),
                recorded = DateTime("2023"),
                primarySource = FHIRBoolean.TRUE,
                reportOrigin = CodeableConcept(text = FHIRString("report origin")),
                location = Reference(display = FHIRString("location")),
                manufacturer = Reference(display = FHIRString("manufacturer")),
                lotNumber = FHIRString("lot number"),
                expirationDate = Date("2025"),
                site = CodeableConcept(text = FHIRString("site")),
                route = CodeableConcept(text = FHIRString("route")),
                doseQuantity = SimpleQuantity(value = Decimal(BigDecimal("1.2"))),
                performer = listOf(ImmunizationPerformer(actor = Reference(display = FHIRString("actor")))),
                note = listOf(Annotation(text = Markdown("note"))),
                reasonCode = listOf(CodeableConcept(text = FHIRString("reason code"))),
                reasonReference = listOf(Reference(display = FHIRString("reason reference"))),
                isSubpotent = FHIRBoolean.TRUE,
                subpotentReason = listOf(CodeableConcept(text = FHIRString("subpotent reason"))),
                education = listOf(ImmunizationEducation(documentType = FHIRString("document type"))),
                programEligibility = listOf(CodeableConcept(text = FHIRString("program eligibility"))),
                fundingSource = CodeableConcept(text = FHIRString("funding source")),
                reaction = listOf(ImmunizationReaction(date = DateTime("2023"))),
                protocolApplied =
                    listOf(
                        ImmunizationProtocolApplied(
                            doseNumber =
                                DynamicValue(
                                    DynamicValueType.POSITIVE_INT,
                                    PositiveInt(1),
                                ),
                        ),
                    ),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(immunization)

        val expectedJson =
            """
            {
              "resourceType" : "Immunization",
              "id" : "12345",
              "meta" : {
                "profile" : [ "RoninImmunization" ]
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
              "status" : "completed",
              "statusReason" : {
                "text" : "status reason"
              },
              "vaccineCode" : {
                "text" : "vaccine code"
              },
              "patient" : {
                "display" : "patient"
              },
              "encounter" : {
                "display" : "encounter"
              },
              "occurrenceString" : "occurrence",
              "recorded" : "2023",
              "primarySource" : true,
              "reportOrigin" : {
                "text" : "report origin"
              },
              "location" : {
                "display" : "location"
              },
              "manufacturer" : {
                "display" : "manufacturer"
              },
              "lotNumber" : "lot number",
              "expirationDate" : "2025",
              "site" : {
                "text" : "site"
              },
              "route" : {
                "text" : "route"
              },
              "doseQuantity" : {
                "value" : 1.2
              },
              "performer" : [ {
                "actor" : {
                  "display" : "actor"
                }
              } ],
              "note" : [ {
                "text" : "note"
              } ],
              "reasonCode" : [ {
                "text" : "reason code"
              } ],
              "reasonReference" : [ {
                "display" : "reason reference"
              } ],
              "isSubpotent" : true,
              "subpotentReason" : [ {
                "text" : "subpotent reason"
              } ],
              "education" : [ {
                "documentType" : "document type"
              } ],
              "programEligibility" : [ {
                "text" : "program eligibility"
              } ],
              "fundingSource" : {
                "text" : "funding source"
              },
              "reaction" : [ {
                "date" : "2023"
              } ],
              "protocolApplied" : [ {
                "doseNumberPositiveInt" : 1
              } ]
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedImmunization = objectMapper.readValue<Immunization>(json)
        assertEquals(immunization, deserializedImmunization)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val immunization =
            Immunization(
                status = ImmunizationStatus.COMPLETED.asCode(),
                vaccineCode = CodeableConcept(text = FHIRString("vaccine code")),
                patient = Reference(display = FHIRString("patient")),
                occurrence = DynamicValue(DynamicValueType.STRING, FHIRString("occurrence")),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(immunization)

        val expectedJson =
            """
            {
              "resourceType" : "Immunization",
              "status" : "completed",
              "vaccineCode" : {
                "text" : "vaccine code"
              },
              "patient" : {
                "display" : "patient"
              },
              "occurrenceString" : "occurrence"
            }
            """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            {
              "resourceType" : "Immunization",
              "status" : "completed",
              "vaccineCode" : {
                "text" : "vaccine code"
              },
              "patient" : {
                "display" : "patient"
              },
              "occurrenceString" : "occurrence"
            }
            """.trimIndent()
        val immunization = objectMapper.readValue<Immunization>(json)

        assertNull(immunization.id)
        assertNull(immunization.meta)
        assertNull(immunization.implicitRules)
        assertNull(immunization.language)
        assertNull(immunization.text)
        assertEquals(listOf<Resource<*>>(), immunization.contained)
        assertEquals(listOf<Extension>(), immunization.extension)
        assertEquals(listOf<Extension>(), immunization.modifierExtension)
        assertEquals(listOf<Identifier>(), immunization.identifier)
        assertEquals(ImmunizationStatus.COMPLETED.asCode(), immunization.status)
        assertNull(immunization.statusReason)
        assertEquals(CodeableConcept(text = FHIRString("vaccine code")), immunization.vaccineCode)
        assertEquals(Reference(display = FHIRString("patient")), immunization.patient)
        assertNull(immunization.encounter)
        assertEquals(DynamicValue(DynamicValueType.STRING, FHIRString("occurrence")), immunization.occurrence)
        assertNull(immunization.recorded)
        assertNull(immunization.primarySource)
        assertNull(immunization.reportOrigin)
        assertNull(immunization.location)
        assertNull(immunization.manufacturer)
        assertNull(immunization.lotNumber)
        assertNull(immunization.expirationDate)
        assertNull(immunization.site)
        assertNull(immunization.route)
        assertNull(immunization.doseQuantity)
        assertEquals(listOf<ImmunizationPerformer>(), immunization.performer)
        assertEquals(listOf<Annotation>(), immunization.note)
        assertEquals(listOf<CodeableConcept>(), immunization.reasonCode)
        assertEquals(listOf<Reference>(), immunization.reasonReference)
        assertNull(immunization.isSubpotent)
        assertEquals(listOf<CodeableConcept>(), immunization.subpotentReason)
        assertEquals(listOf<ImmunizationEducation>(), immunization.education)
        assertEquals(listOf<CodeableConcept>(), immunization.programEligibility)
        assertNull(immunization.fundingSource)
        assertEquals(listOf<ImmunizationReaction>(), immunization.reaction)
        assertEquals(listOf<ImmunizationProtocolApplied>(), immunization.protocolApplied)
    }
}

class ImmunizationPerformerTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val performer =
            ImmunizationPerformer(
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
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                function = CodeableConcept(text = FHIRString("function")),
                actor = Reference(display = FHIRString("actor")),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(performer)

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
                "valueString" : "Value"
              } ],
              "function" : {
                "text" : "function"
              },
              "actor" : {
                "display" : "actor"
              }
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedPerformer = objectMapper.readValue<ImmunizationPerformer>(json)
        assertEquals(performer, deserializedPerformer)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val performer =
            ImmunizationPerformer(
                actor = Reference(display = FHIRString("actor")),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(performer)

        val expectedJson =
            """
            {
              "actor" : {
                "display" : "actor"
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
              "actor" : {
                "display" : "actor"
              }
            }
            """.trimIndent()
        val performer = objectMapper.readValue<ImmunizationPerformer>(json)

        assertNull(performer.id)
        assertEquals(listOf<Extension>(), performer.extension)
        assertEquals(listOf<Extension>(), performer.modifierExtension)
        assertNull(performer.function)
        assertEquals(Reference(display = FHIRString("actor")), performer.actor)
    }
}

class ImmunizationEducationTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val education =
            ImmunizationEducation(
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
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                documentType = FHIRString("document type"),
                reference = Uri("reference"),
                publicationDate = DateTime("2020"),
                presentationDate = DateTime("2022"),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(education)

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
                "valueString" : "Value"
              } ],
              "documentType" : "document type",
              "reference" : "reference",
              "publicationDate" : "2020",
              "presentationDate" : "2022"
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedEducation = objectMapper.readValue<ImmunizationEducation>(json)
        assertEquals(education, deserializedEducation)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val education =
            ImmunizationEducation(
                documentType = FHIRString("document type"),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(education)

        val expectedJson =
            """
            {
              "documentType" : "document type"
            }
            """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            {
              "reference" : "reference"
            }
            """.trimIndent()
        val education = objectMapper.readValue<ImmunizationEducation>(json)

        assertNull(education.id)
        assertEquals(listOf<Extension>(), education.extension)
        assertEquals(listOf<Extension>(), education.modifierExtension)
        assertNull(education.documentType)
        assertEquals(Uri("reference"), education.reference)
        assertNull(education.publicationDate)
        assertNull(education.presentationDate)
    }
}

class ImmunizationReactionTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val reaction =
            ImmunizationReaction(
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
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                date = DateTime("2023"),
                detail = Reference(display = FHIRString("detail")),
                reported = FHIRBoolean.TRUE,
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(reaction)

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
                "valueString" : "Value"
              } ],
              "date" : "2023",
              "detail" : {
                "display" : "detail"
              },
              "reported" : true
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedReaction = objectMapper.readValue<ImmunizationReaction>(json)
        assertEquals(reaction, deserializedReaction)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val reaction =
            ImmunizationReaction(
                date = DateTime("2023-06-20"),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(reaction)

        val expectedJson =
            """
            {
              "date" : "2023-06-20"
            }
            """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            {
              "reported" : false
            }
            """.trimIndent()
        val reaction = objectMapper.readValue<ImmunizationReaction>(json)

        assertNull(reaction.id)
        assertEquals(listOf<Extension>(), reaction.extension)
        assertEquals(listOf<Extension>(), reaction.modifierExtension)
        assertNull(reaction.date)
        assertNull(reaction.detail)
        assertEquals(FHIRBoolean.FALSE, reaction.reported)
    }
}

class ImmunizationProtocolAppliedTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val protocolApplied =
            ImmunizationProtocolApplied(
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
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                series = FHIRString("series"),
                authority = Reference(display = FHIRString("authority")),
                targetDisease = listOf(CodeableConcept(text = FHIRString("target disease"))),
                doseNumber = DynamicValue(DynamicValueType.POSITIVE_INT, PositiveInt(1)),
                seriesDoses = DynamicValue(DynamicValueType.POSITIVE_INT, PositiveInt(2)),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(protocolApplied)

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
                "valueString" : "Value"
              } ],
              "series" : "series",
              "authority" : {
                "display" : "authority"
              },
              "targetDisease" : [ {
                "text" : "target disease"
              } ],
              "doseNumberPositiveInt" : 1,
              "seriesDosesPositiveInt" : 2
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedProtocolApplied = objectMapper.readValue<ImmunizationProtocolApplied>(json)
        assertEquals(protocolApplied, deserializedProtocolApplied)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val protocolApplied =
            ImmunizationProtocolApplied(
                doseNumber = DynamicValue(DynamicValueType.POSITIVE_INT, PositiveInt(3)),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(protocolApplied)

        val expectedJson =
            """
            {
              "doseNumberPositiveInt" : 3
            }
            """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            {
              "doseNumberPositiveInt" : 4
            }
            """.trimIndent()
        val protocolApplied = objectMapper.readValue<ImmunizationProtocolApplied>(json)

        assertNull(protocolApplied.id)
        assertEquals(listOf<Extension>(), protocolApplied.extension)
        assertEquals(listOf<Extension>(), protocolApplied.modifierExtension)
        assertNull(protocolApplied.series)
        assertNull(protocolApplied.authority)
        assertEquals(listOf<CodeableConcept>(), protocolApplied.targetDisease)
        assertEquals(DynamicValue(DynamicValueType.POSITIVE_INT, PositiveInt(4)), protocolApplied.doseNumber)
        assertNull(protocolApplied.seriesDoses)
    }
}
