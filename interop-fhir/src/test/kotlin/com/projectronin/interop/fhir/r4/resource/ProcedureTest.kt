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
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.EventStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ProcedureTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val procedure =
            Procedure(
                id = Id("12345"),
                meta =
                    Meta(
                        profile = listOf(Canonical("RoninProcedure")),
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
                instantiatesCanonical = listOf(Canonical("instantiates-canonical")),
                instantiatesUri = listOf(Uri("instantiates-uri")),
                basedOn = listOf(Reference(reference = FHIRString("CarePlan/1234"))),
                partOf = listOf(Reference(reference = FHIRString("Observation/1234"))),
                status = EventStatus.COMPLETED.asCode(),
                statusReason = CodeableConcept(text = FHIRString("status-reason")),
                category = CodeableConcept(text = FHIRString("category")),
                code = CodeableConcept(text = FHIRString("code")),
                subject = Reference(reference = FHIRString("Patient/1234")),
                encounter = Reference(reference = FHIRString("Encounter/1234")),
                performed = DynamicValue(DynamicValueType.STRING, FHIRString("performed")),
                recorder = Reference(reference = FHIRString("Patient/1234")),
                asserter = Reference(reference = FHIRString("Patient/5678")),
                performer = listOf(ProcedurePerformer(actor = Reference(reference = FHIRString("Practitioner/1234")))),
                location = Reference(reference = FHIRString("Location/1234")),
                reasonCode = listOf(CodeableConcept(text = FHIRString("reason-code"))),
                reasonReference = listOf(Reference(reference = FHIRString("Condition/1234"))),
                bodySite = listOf(CodeableConcept(text = FHIRString("body-site"))),
                outcome = CodeableConcept(text = FHIRString("outcome")),
                report = listOf(Reference(reference = FHIRString("DiagnosticReport/1234"))),
                complication = listOf(CodeableConcept(text = FHIRString("complication"))),
                complicationDetail = listOf(Reference(reference = FHIRString("Condition/5678"))),
                followUp = listOf(CodeableConcept(text = FHIRString("follow-up"))),
                note = listOf(Annotation(text = Markdown("note"))),
                focalDevice = listOf(ProcedureFocalDevice(manipulated = Reference(reference = FHIRString("Device/1234")))),
                usedReference = listOf(Reference(reference = FHIRString("Device/5678"))),
                usedCode = listOf(CodeableConcept(text = FHIRString("used-code"))),
            )
        val json =
            objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(procedure)

        val expectedJson =
            """
            {
              "resourceType" : "Procedure",
              "id" : "12345",
              "meta" : {
                "profile" : [ "RoninProcedure" ]
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
              "instantiatesCanonical" : [ "instantiates-canonical" ],
              "instantiatesUri" : [ "instantiates-uri" ],
              "basedOn" : [ {
                "reference" : "CarePlan/1234"
              } ],
              "partOf" : [ {
                "reference" : "Observation/1234"
              } ],
              "status" : "completed",
              "statusReason" : {
                "text" : "status-reason"
              },
              "category" : {
                "text" : "category"
              },
              "code" : {
                "text" : "code"
              },
              "subject" : {
                "reference" : "Patient/1234"
              },
              "encounter" : {
                "reference" : "Encounter/1234"
              },
              "performedString" : "performed",
              "recorder" : {
                "reference" : "Patient/1234"
              },
              "asserter" : {
                "reference" : "Patient/5678"
              },
              "performer" : [ {
                "actor" : {
                  "reference" : "Practitioner/1234"
                }
              } ],
              "location" : {
                "reference" : "Location/1234"
              },
              "reasonCode" : [ {
                "text" : "reason-code"
              } ],
              "reasonReference" : [ {
                "reference" : "Condition/1234"
              } ],
              "bodySite" : [ {
                "text" : "body-site"
              } ],
              "outcome" : {
                "text" : "outcome"
              },
              "report" : [ {
                "reference" : "DiagnosticReport/1234"
              } ],
              "complication" : [ {
                "text" : "complication"
              } ],
              "complicationDetail" : [ {
                "reference" : "Condition/5678"
              } ],
              "followUp" : [ {
                "text" : "follow-up"
              } ],
              "note" : [ {
                "text" : "note"
              } ],
              "focalDevice" : [ {
                "manipulated" : {
                  "reference" : "Device/1234"
                }
              } ],
              "usedReference" : [ {
                "reference" : "Device/5678"
              } ],
              "usedCode" : [ {
                "text" : "used-code"
              } ]
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedProcedure = objectMapper.readValue<Procedure>(json)
        assertEquals(procedure, deserializedProcedure)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val procedure =
            Procedure(
                status = EventStatus.ENTERED_IN_ERROR.asCode(),
                subject = Reference(reference = FHIRString("Patient/5678")),
            )
        val json =
            objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(procedure)

        val expectedJson =
            """
            {
              "resourceType" : "Procedure",
              "status" : "entered-in-error",
              "subject" : {
                "reference" : "Patient/5678"
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
              "resourceType" : "Procedure",
              "status" : "in-progress",
              "subject" : {
                "reference" : "Patient/1234"
              }
            }
            """.trimIndent()
        val procedure = objectMapper.readValue<Procedure>(json)

        assertNull(procedure.id)
        assertNull(procedure.meta)
        assertNull(procedure.implicitRules)
        assertNull(procedure.language)
        assertNull(procedure.text)
        assertEquals(listOf<Resource<Nothing>>(), procedure.contained)
        assertEquals(listOf<Extension>(), procedure.extension)
        assertEquals(listOf<Extension>(), procedure.modifierExtension)
        assertEquals(listOf<Identifier>(), procedure.identifier)
        assertEquals(listOf<Canonical>(), procedure.instantiatesCanonical)
        assertEquals(listOf<Uri>(), procedure.instantiatesUri)
        assertEquals(listOf<Reference>(), procedure.basedOn)
        assertEquals(listOf<Reference>(), procedure.partOf)
        assertEquals(EventStatus.IN_PROGRESS.asCode(), procedure.status)
        assertNull(procedure.statusReason)
        assertNull(procedure.category)
        assertNull(procedure.code)
        assertEquals(Reference(reference = FHIRString("Patient/1234")), procedure.subject)
        assertNull(procedure.encounter)
        assertNull(procedure.performed)
        assertNull(procedure.recorder)
        assertNull(procedure.asserter)
        assertEquals(listOf<ProcedurePerformer>(), procedure.performer)
        assertNull(procedure.location)
        assertEquals(listOf<CodeableConcept>(), procedure.reasonCode)
        assertEquals(listOf<Reference>(), procedure.reasonReference)
        assertEquals(listOf<CodeableConcept>(), procedure.bodySite)
        assertNull(procedure.outcome)
        assertEquals(listOf<Reference>(), procedure.report)
        assertEquals(listOf<CodeableConcept>(), procedure.complication)
        assertEquals(listOf<Reference>(), procedure.complicationDetail)
        assertEquals(listOf<CodeableConcept>(), procedure.followUp)
        assertEquals(listOf<Annotation>(), procedure.note)
        assertEquals(listOf<ProcedureFocalDevice>(), procedure.focalDevice)
        assertEquals(listOf<Reference>(), procedure.usedReference)
        assertEquals(listOf<CodeableConcept>(), procedure.usedCode)
    }
}

class ProcedurePerformerTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val performer =
            ProcedurePerformer(
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
                actor = Reference(reference = FHIRString("Practitioner/1234")),
                onBehalfOf = Reference(reference = FHIRString("Organization/1234")),
            )
        val json =
            objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(performer)

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
                "reference" : "Practitioner/1234"
              },
              "onBehalfOf" : {
                "reference" : "Organization/1234"
              }
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedProcedurePerformer = objectMapper.readValue<ProcedurePerformer>(json)
        assertEquals(performer, deserializedProcedurePerformer)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val performer = ProcedurePerformer(actor = Reference(reference = FHIRString("Practitioner/1234")))
        val json =
            objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(performer)

        val expectedJson =
            """
            {
              "actor" : {
                "reference" : "Practitioner/1234"
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
                "reference" : "Practitioner/5678"
              }
            }
            """.trimIndent()
        val performer = objectMapper.readValue<ProcedurePerformer>(json)

        assertNull(performer.id)
        assertEquals(listOf<Extension>(), performer.extension)
        assertEquals(listOf<Extension>(), performer.modifierExtension)
        assertNull(performer.function)
        assertEquals(Reference(reference = FHIRString("Practitioner/5678")), performer.actor)
        assertNull(performer.onBehalfOf)
    }
}

class ProcedureFocalDeviceTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val focalDevice =
            ProcedureFocalDevice(
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
                action = CodeableConcept(text = FHIRString("action")),
                manipulated = Reference(reference = FHIRString("Device/1234")),
            )
        val json =
            objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(focalDevice)

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
              "action" : {
                "text" : "action"
              },
              "manipulated" : {
                "reference" : "Device/1234"
              }
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedProcedureFocalDevice = objectMapper.readValue<ProcedureFocalDevice>(json)
        assertEquals(focalDevice, deserializedProcedureFocalDevice)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val focalDevice = ProcedureFocalDevice(manipulated = Reference(reference = FHIRString("Device/1234")))
        val json =
            objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(focalDevice)

        val expectedJson =
            """
            {
              "manipulated" : {
                "reference" : "Device/1234"
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
              "manipulated" : {
                "reference" : "Device/5678"
              }
            }
            """.trimIndent()
        val focalDevice = objectMapper.readValue<ProcedureFocalDevice>(json)

        assertNull(focalDevice.id)
        assertEquals(listOf<Extension>(), focalDevice.extension)
        assertEquals(listOf<Extension>(), focalDevice.modifierExtension)
        assertNull(focalDevice.action)
        assertEquals(Reference(reference = FHIRString("Device/5678")), focalDevice.manipulated)
    }
}
