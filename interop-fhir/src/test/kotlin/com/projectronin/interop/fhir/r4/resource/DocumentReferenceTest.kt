package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.Attachment
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
import com.projectronin.interop.fhir.r4.datatype.primitive.Base64Binary
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.CompositionStatus
import com.projectronin.interop.fhir.r4.valueset.DocumentReferenceStatus
import com.projectronin.interop.fhir.r4.valueset.DocumentRelationshipType
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class DocumentReferenceTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val documentReference = DocumentReference(
            id = Id("12345"),
            meta = Meta(
                profile = listOf(Canonical("RoninAppointment"))
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
            masterIdentifier = Identifier(value = FHIRString("masterIdentifier")),
            identifier = listOf(Identifier(value = FHIRString("identifier"))),
            status = DocumentReferenceStatus.CURRENT.asCode(),
            docStatus = CompositionStatus.FINAL.asCode(),
            type = CodeableConcept(text = FHIRString("type")),
            category = listOf(CodeableConcept(text = FHIRString("category"))),
            subject = Reference(display = FHIRString("subject")),
            date = Instant("2017-01-01T00:00:00Z"),
            author = listOf(Reference(display = FHIRString("author"))),
            authenticator = Reference(display = FHIRString("authenticator")),
            custodian = Reference(display = FHIRString("custodian")),
            relatesTo = listOf(
                DocumentReferenceRelatesTo(
                    code = DocumentRelationshipType.SIGNS.asCode(),
                    target = Reference(display = FHIRString("target"))
                )
            ),
            description = FHIRString("description"),
            securityLabel = listOf(CodeableConcept(text = FHIRString("securityLabel"))),
            content = listOf(DocumentReferenceContent(attachment = Attachment(data = Base64Binary("r2d2")))),
            context = DocumentReferenceContext(id = FHIRString("contextId"))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(documentReference)

        val expectedJson = """
            {
              "resourceType" : "DocumentReference",
              "id" : "12345",
              "meta" : {
                "profile" : [ "RoninAppointment" ]
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
              "masterIdentifier" : {
                "value" : "masterIdentifier"
              },
              "identifier" : [ {
                "value" : "identifier"
              } ],
              "status" : "current",
              "docStatus" : "final",
              "type" : {
                "text" : "type"
              },
              "category" : [ {
                "text" : "category"
              } ],
              "subject" : {
                "display" : "subject"
              },
              "date" : "2017-01-01T00:00:00Z",
              "author" : [ {
                "display" : "author"
              } ],
              "authenticator" : {
                "display" : "authenticator"
              },
              "custodian" : {
                "display" : "custodian"
              },
              "relatesTo" : [ {
                "code" : "signs",
                "target" : {
                  "display" : "target"
                }
              } ],
              "description" : "description",
              "securityLabel" : [ {
                "text" : "securityLabel"
              } ],
              "content" : [ {
                "attachment" : {
                  "data" : "r2d2"
                }
              } ],
              "context" : {
                "id" : "contextId"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedDocumentReference = objectMapper.readValue<DocumentReference>(json)
        assertEquals(documentReference, deserializedDocumentReference)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val documentReference = DocumentReference(
            status = DocumentReferenceStatus.SUPERSEDED.asCode()
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(documentReference)

        val expectedJson = """
            {
              "resourceType" : "DocumentReference",
              "status" : "superseded"
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "resourceType" : "DocumentReference",
              "status" : "entered-in-error"
            }
        """.trimIndent()

        val documentReference = objectMapper.readValue<DocumentReference>(json)

        assertNull(documentReference.id)
        assertNull(documentReference.meta)
        assertNull(documentReference.implicitRules)
        assertNull(documentReference.language)
        assertNull(documentReference.text)
        assertEquals(listOf<Resource<*>>(), documentReference.contained)
        assertEquals(listOf<Extension>(), documentReference.extension)
        assertEquals(listOf<Extension>(), documentReference.modifierExtension)
        assertNull(documentReference.masterIdentifier)
        assertEquals(listOf<Identifier>(), documentReference.identifier)
        assertEquals(Code("entered-in-error"), documentReference.status)
        assertNull(documentReference.docStatus)
        assertNull(documentReference.type)
        assertEquals(listOf<CodeableConcept>(), documentReference.category)
        assertNull(documentReference.subject)
        assertNull(documentReference.date)
        assertEquals(listOf<Reference>(), documentReference.author)
        assertNull(documentReference.authenticator)
        assertNull(documentReference.custodian)
        assertEquals(listOf<DocumentReferenceRelatesTo>(), documentReference.relatesTo)
        assertNull(documentReference.description)
        assertEquals(listOf<CodeableConcept>(), documentReference.securityLabel)
        assertEquals(listOf<DocumentReferenceContent>(), documentReference.content)
        assertNull(documentReference.context)
    }
}

class DocumentReferenceRelatesToTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val relatesTo = DocumentReferenceRelatesTo(
            id = FHIRString("67890"),
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
            code = DocumentRelationshipType.SIGNS.asCode(),
            target = Reference(display = FHIRString("target"))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(relatesTo)

        val expectedJson = """
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
              "code" : "signs",
              "target" : {
                "display" : "target"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedRelatesTo = objectMapper.readValue<DocumentReferenceRelatesTo>(json)
        assertEquals(relatesTo, deserializedRelatesTo)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val relatesTo = DocumentReferenceRelatesTo(
            code = DocumentRelationshipType.SIGNS.asCode(),
            target = Reference(display = FHIRString("target"))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(relatesTo)

        val expectedJson = """
            {
              "code" : "signs",
              "target" : {
                "display" : "target"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "code" : "appends",
              "target" : {
                "display" : "target"
              }
            }
        """.trimIndent()

        val relatesTo = objectMapper.readValue<DocumentReferenceRelatesTo>(json)

        assertNull(relatesTo.id)
        assertEquals(listOf<Extension>(), relatesTo.extension)
        assertEquals(listOf<Extension>(), relatesTo.modifierExtension)
        assertEquals(Code("appends"), relatesTo.code)
        assertEquals(Reference(display = FHIRString("target")), relatesTo.target)
    }
}

class DocumentReferenceContentTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val content = DocumentReferenceContent(
            id = FHIRString("67890"),
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
            attachment = Attachment(data = Base64Binary("abcd")),
            format = Coding(display = FHIRString("format"))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(content)

        val expectedJson = """
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
              "attachment" : {
                "data" : "abcd"
              },
              "format" : {
                "display" : "format"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedContent = objectMapper.readValue<DocumentReferenceContent>(json)
        assertEquals(content, deserializedContent)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val content = DocumentReferenceContent(
            attachment = Attachment(data = Base64Binary("abcd"))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(content)

        val expectedJson = """
            {
              "attachment" : {
                "data" : "abcd"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "attachment" : {
                "data" : "1b3d"
              }
            }
        """.trimIndent()

        val content = objectMapper.readValue<DocumentReferenceContent>(json)

        assertNull(content.id)
        assertEquals(listOf<Extension>(), content.extension)
        assertEquals(listOf<Extension>(), content.modifierExtension)
        assertEquals(Attachment(data = Base64Binary("1b3d")), content.attachment)
        assertNull(content.format)
    }
}

class DocumentReferenceContextTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val context = DocumentReferenceContext(
            id = FHIRString("67890"),
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
            encounter = listOf(Reference(display = FHIRString("encounter"))),
            event = listOf(CodeableConcept(text = FHIRString("event"))),
            period = Period(start = DateTime("1905-08-23")),
            facilityType = CodeableConcept(text = FHIRString("facilityType")),
            practiceSetting = CodeableConcept(text = FHIRString("practiceSetting")),
            sourcePatientInfo = Reference(display = FHIRString("sourcePatientInfo")),
            related = listOf(Reference(display = FHIRString("related")))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(context)

        val expectedJson = """
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
              "encounter" : [ {
                "display" : "encounter"
              } ],
              "event" : [ {
                "text" : "event"
              } ],
              "period" : {
                "start" : "1905-08-23"
              },
              "facilityType" : {
                "text" : "facilityType"
              },
              "practiceSetting" : {
                "text" : "practiceSetting"
              },
              "sourcePatientInfo" : {
                "display" : "sourcePatientInfo"
              },
              "related" : [ {
                "display" : "related"
              } ]
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedContext = objectMapper.readValue<DocumentReferenceContext>(json)
        assertEquals(context, deserializedContext)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val context = DocumentReferenceContext(
            encounter = listOf(Reference(display = FHIRString("encounter")))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(context)

        val expectedJson = """
            {
              "encounter" : [ {
                "display" : "encounter"
              } ]
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "event" : [ {
                "text" : "event"
              } ]
            }
        """.trimIndent()

        val context = objectMapper.readValue<DocumentReferenceContext>(json)

        assertNull(context.id)
        assertEquals(listOf<Extension>(), context.extension)
        assertEquals(listOf<Extension>(), context.modifierExtension)
        assertEquals(listOf<Reference>(), context.encounter)
        assertEquals(listOf(CodeableConcept(text = FHIRString("event"))), context.event)
        assertNull(context.period)
        assertNull(context.facilityType)
        assertNull(context.practiceSetting)
        assertNull(context.sourcePatientInfo)
        assertEquals(listOf<Reference>(), context.related)
    }
}
