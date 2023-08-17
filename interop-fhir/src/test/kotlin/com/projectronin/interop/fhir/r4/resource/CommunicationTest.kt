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
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.EventStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class CommunicationTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val communication: Communication = Communication(
            id = Id("12345"),
            meta = Meta(
                profile = listOf(Canonical("RoninCommunication"))
            ),
            implicitRules = Uri("implicit-rules"),
            language = Code("en-US"),
            text = Narrative(
                status = com.projectronin.interop.fhir.r4.valueset.NarrativeStatus.GENERATED.asCode(),
                div = FHIRString("div")
            ),
            contained = listOf(Location(id = Id("1234"), name = FHIRString("Contained Location"))),
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
            instantiatesCanonical = listOf(Canonical("RoninCommunication")),
            instantiatesUri = listOf(Uri("http://localhost/uri")),
            basedOn = listOf(Reference(display = FHIRString("communication-based-on"))),
            partOf = listOf(Reference(display = FHIRString("communication-part-of"))),
            inResponseTo = listOf(Reference(display = FHIRString("communication-in-response-to"))),
            status = EventStatus.COMPLETED.asCode(),
            statusReason = CodeableConcept(text = FHIRString("communication-status-reason")),
            category = listOf(CodeableConcept(text = FHIRString("communication-category"))),
            priority = Code("priority"),
            medium = listOf(CodeableConcept(text = FHIRString("communication-medium"))),
            subject = Reference(display = FHIRString("communication-subject")),
            topic = CodeableConcept(text = FHIRString("communication-topic")),
            about = listOf(Reference(display = FHIRString("communication-about"))),
            encounter = Reference(display = FHIRString("communication-encounter")),
            sent = DateTime("2023-01-30"),
            received = DateTime("2023-01-31"),
            recipient = listOf(Reference(display = FHIRString("communication-recipient"))),
            sender = Reference(display = FHIRString("communication-sender")),
            reasonCode = listOf(CodeableConcept(text = FHIRString("communication-reason-code"))),
            reasonReference = listOf(Reference(display = FHIRString("communication-reason-reference"))),
            payload = listOf(
                CommunicationPayload(
                    content = DynamicValue(
                        DynamicValueType.REFERENCE,
                        Reference(display = FHIRString("communication-payload-content"))
                    )
                )
            ),
            note = listOf(Annotation(text = Markdown("communication-note")))
        )

        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(communication)

        val expectedJson = """
            {
              "resourceType" : "Communication",
              "id" : "12345",
              "meta" : {
                "profile" : [ "RoninCommunication" ]
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
              "instantiatesCanonical" : [ "RoninCommunication" ],
              "instantiatesUri" : [ "http://localhost/uri" ],
              "basedOn" : [ {
                "display" : "communication-based-on"
              } ],
              "partOf" : [ {
                "display" : "communication-part-of"
              } ],
              "inResponseTo" : [ {
                "display" : "communication-in-response-to"
              } ],
              "status" : "completed",
              "statusReason" : {
                "text" : "communication-status-reason"
              },
              "category" : [ {
                "text" : "communication-category"
              } ],
              "priority" : "priority",
              "medium" : [ {
                "text" : "communication-medium"
              } ],
              "subject" : {
                "display" : "communication-subject"
              },
              "topic" : {
                "text" : "communication-topic"
              },
              "about" : [ {
                "display" : "communication-about"
              } ],
              "encounter" : {
                "display" : "communication-encounter"
              },
              "sent" : "2023-01-30",
              "received" : "2023-01-31",
              "recipient" : [ {
                "display" : "communication-recipient"
              } ],
              "sender" : {
                "display" : "communication-sender"
              },
              "reasonCode" : [ {
                "text" : "communication-reason-code"
              } ],
              "reasonReference" : [ {
                "display" : "communication-reason-reference"
              } ],
              "payload" : [ {
                "contentReference" : {
                  "display" : "communication-payload-content"
                }
              } ],
              "note" : [ {
                "text" : "communication-note"
              } ]
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedCommunication = objectMapper.readValue<Communication>(json)
        assertEquals(communication, deserializedCommunication)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val communication = Communication(
            status = EventStatus.ENTERED_IN_ERROR.asCode()
        )

        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(communication)
        val expectedJson = """
            {
              "resourceType" : "Communication",
              "status" : "entered-in-error"
            }
        """.trimIndent()

        assertEquals(expectedJson, json)

        val deserializedCommunication = objectMapper.readValue<Communication>(json)
        assertEquals(communication, deserializedCommunication)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "resourceType" : "Communication",
              "status" : "entered-in-error"
            }
        """.trimIndent()

        val communication = objectMapper.readValue<Communication>(json)
        communication.apply {
            assertNull(id)
            assertNull(meta)
            assertNull(implicitRules)
            assertNull(language)
            assertNull(text)
            assertEquals(listOf<Resource<*>>(), contained)
            assertEquals(listOf<Extension>(), extension)
            assertEquals(listOf<Extension>(), modifierExtension)
            assertEquals(listOf<Identifier>(), identifier)
            assertEquals(listOf<Canonical>(), instantiatesCanonical)
            assertEquals(listOf<Uri>(), instantiatesUri)
            assertEquals(listOf<Reference>(), basedOn)
            assertEquals(listOf<Reference>(), partOf)
            assertEquals(listOf<Reference>(), inResponseTo)
            assertEquals(Code("entered-in-error"), status)
            assertNull(statusReason)
            assertEquals(listOf<CodeableConcept>(), category)
            assertNull(priority)
            assertEquals(listOf<CodeableConcept>(), medium)
            assertNull(subject)
            assertNull(topic)
            assertEquals(listOf<Reference>(), about)
            assertNull(encounter)
            assertNull(sent)
            assertNull(received)
            assertEquals(listOf<Reference>(), recipient)
            assertNull(sender)
            assertEquals(listOf<CodeableConcept>(), reasonCode)
            assertEquals(listOf<Reference>(), reasonReference)
            assertEquals(listOf<CommunicationPayload>(), payload)
            assertEquals(listOf<Annotation>(), note)
        }
    }
}

class CommunicationPayloadTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val communicationPayload = CommunicationPayload(
            id = FHIRString("39876"),
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
            content = DynamicValue(
                DynamicValueType.REFERENCE,
                Reference(display = FHIRString("communication-payload-content"))
            )
        )

        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(communicationPayload)

        val expectedJson = """
            {
              "id" : "39876",
              "extension" : [ {
                "url" : "http://localhost/extension",
                "valueString" : "Value"
              } ],
              "modifierExtension" : [ {
                "url" : "http://localhost/modifier-extension",
                "valueString" : "Value"
              } ],
              "contentReference" : {
                "display" : "communication-payload-content"
              }
            }
        """.trimIndent()

        assertEquals(expectedJson, json)

        val deserializedCommunicationPayload = objectMapper.readValue<CommunicationPayload>(json)
        assertEquals(communicationPayload, deserializedCommunicationPayload)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val communicationPayload = CommunicationPayload(
            content = DynamicValue(
                DynamicValueType.REFERENCE,
                Reference(display = FHIRString("communication-payload-content"))
            )
        )

        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(communicationPayload)

        val expectedJson = """
            {
              "contentReference" : {
                "display" : "communication-payload-content"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedCommunicationPayload = objectMapper.readValue<CommunicationPayload>(json)
        assertEquals(communicationPayload, deserializedCommunicationPayload)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "contentReference" : {
                "display" : "communication-payload-content"
              }
            }
        """.trimIndent()

        val communicationPayload = objectMapper.readValue<CommunicationPayload>(json)
        communicationPayload.apply {
            assertNull(communicationPayload.id)
            assertEquals(listOf<Extension>(), extension)
            assertEquals(listOf<Extension>(), modifierExtension)
            assertEquals(
                DynamicValue(
                    DynamicValueType.REFERENCE,
                    Reference(display = FHIRString("communication-payload-content"))
                ),
                content
            )
        }
    }
}
