package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Duration
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Expression
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.RelatedArtifact
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.datatype.primitive.asFHIR
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class RequestGroupTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val requestGroup =
            RequestGroup(
                id = Id("12345"),
                meta =
                    Meta(
                        profile = listOf(Canonical("RoninRequestGroup")),
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
                instantiatesCanonical = listOf(Canonical("instantiatesCanonical")),
                instantiatesUri = listOf(Uri("instantiatesUri")),
                basedOn = listOf(Reference(id = "basedOn".asFHIR())),
                replaces = listOf(Reference(id = "replaces".asFHIR())),
                groupIdentifier = Identifier(value = FHIRString("id")),
                status = Code("status"),
                intent = Code("intent"),
                priority = Code("priority"),
                code = CodeableConcept(text = "coding".asFHIR()),
                subject = Reference(id = "subject".asFHIR()),
                encounter = Reference(id = "encounter".asFHIR()),
                authoredOn = DateTime("2023-05-02"),
                author = Reference(id = "encounter".asFHIR()),
                reasonCode = listOf(CodeableConcept(text = "coding".asFHIR())),
                reasonReference = listOf(Reference(id = "reasonReference".asFHIR())),
                note = listOf(Annotation(text = Markdown("note"))),
                action = listOf(RequestGroupAction(id = "action".asFHIR())),
            )

        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestGroup)

        val expectedJson =
            """
            {
              "resourceType" : "RequestGroup",
              "id" : "12345",
              "meta" : {
                "profile" : [ "RoninRequestGroup" ]
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
              "instantiatesCanonical" : [ "instantiatesCanonical" ],
              "instantiatesUri" : [ "instantiatesUri" ],
              "basedOn" : [ {
                "id" : "basedOn"
              } ],
              "replaces" : [ {
                "id" : "replaces"
              } ],
              "groupIdentifier" : {
                "value" : "id"
              },
              "status" : "status",
              "intent" : "intent",
              "priority" : "priority",
              "code" : {
                "text" : "coding"
              },
              "subject" : {
                "id" : "subject"
              },
              "encounter" : {
                "id" : "encounter"
              },
              "authoredOn" : "2023-05-02",
              "author" : {
                "id" : "encounter"
              },
              "reasonCode" : [ {
                "text" : "coding"
              } ],
              "reasonReference" : [ {
                "id" : "reasonReference"
              } ],
              "note" : [ {
                "text" : "note"
              } ],
              "action" : [ {
                "id" : "action"
              } ]
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserialized = JacksonManager.objectMapper.readValue<RequestGroup>(json)
        assertEquals(requestGroup, deserialized)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val requestGroup =
            RequestGroup(
                status = Code("status"),
                intent = Code("intent"),
            )

        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestGroup)

        val expectedJson =
            """
            {
              "resourceType" : "RequestGroup",
              "status" : "status",
              "intent" : "intent"
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserialized = JacksonManager.objectMapper.readValue<RequestGroup>(json)
        assertEquals(requestGroup, deserialized)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            {
              "resourceType" : "RequestGroup",
              "status" : "status",
              "intent" : "intent"
            }
            """.trimIndent()
        val requestGroup = JacksonManager.objectMapper.readValue<RequestGroup>(json)

        assertNull(requestGroup.id)
        assertNull(requestGroup.meta)
        assertNull(requestGroup.implicitRules)
        assertNull(requestGroup.language)
        assertNull(requestGroup.text)
        assertEquals(listOf<Resource<*>>(), requestGroup.contained)
        assertEquals(listOf<Extension>(), requestGroup.extension)
        assertEquals(listOf<Extension>(), requestGroup.modifierExtension)
        assertEquals(listOf<Identifier>(), requestGroup.identifier)
        assertEquals(listOf<Canonical>(), requestGroup.instantiatesCanonical)
        assertEquals(listOf<Uri>(), requestGroup.instantiatesUri)
        assertEquals(listOf<Reference>(), requestGroup.basedOn)
        assertEquals(listOf<Reference>(), requestGroup.replaces)
        assertNull(requestGroup.groupIdentifier)
        assertEquals("status", requestGroup.status?.value)
        assertEquals("intent", requestGroup.intent?.value)
        assertNull(requestGroup.priority)
        assertNull(requestGroup.code)
        assertNull(requestGroup.subject)
        assertNull(requestGroup.encounter)
        assertNull(requestGroup.authoredOn)
        assertNull(requestGroup.author)
        assertEquals(listOf<CodeableConcept>(), requestGroup.reasonCode)
        assertEquals(listOf<Reference>(), requestGroup.reasonReference)
        assertEquals(listOf<Annotation>(), requestGroup.note)
        assertEquals(listOf<RequestGroupAction>(), requestGroup.action)
    }
}

class RequestGroupActionTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val action =
            RequestGroupAction(
                id = "id".asFHIR(),
                extension = listOf(Extension("extId".asFHIR())),
                modifierExtension = listOf(Extension("extId".asFHIR())),
                prefix = "prefix".asFHIR(),
                title = "title".asFHIR(),
                description = "description".asFHIR(),
                textEquivalent = "textEquivalent".asFHIR(),
                priority = Code("priority"),
                code = listOf(CodeableConcept(text = "coding".asFHIR())),
                documentation = listOf(RelatedArtifact(type = Code("documentation"))),
                condition =
                    listOf(
                        RequestGroupCondition(
                            kind = Code("kind"),
                        ),
                    ),
                relatedAction =
                    listOf(
                        RequestGroupRelatedAction(
                            actionId = Id("actionId"),
                            relationship = Code("relationship"),
                        ),
                    ),
                timing = DynamicValue(DynamicValueType.DATE_TIME, value = DateTime("2023-05-02")),
                participant = listOf(Reference()),
                type = CodeableConcept(text = "type".asFHIR()),
                groupingBehavior = Code("groupingBehavior"),
                selectionBehavior = Code("selectionBehavior"),
                requiredBehavior = Code("requiredBehavior"),
                precheckBehavior = Code("precheckBehavior"),
                cardinalityBehavior = Code("cardinalityBehavior"),
                resource = Reference(),
                action = listOf(RequestGroupAction(id = "action2".asFHIR())),
            )

        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(action)

        val expectedJson =
            """
            {
              "id" : "id",
              "extension" : [ {
                "id" : "extId"
              } ],
              "modifierExtension" : [ {
                "id" : "extId"
              } ],
              "prefix" : "prefix",
              "title" : "title",
              "description" : "description",
              "textEquivalent" : "textEquivalent",
              "priority" : "priority",
              "code" : [ {
                "text" : "coding"
              } ],
              "documentation" : [ {
                "type" : "documentation"
              } ],
              "condition" : [ {
                "kind" : "kind"
              } ],
              "relatedAction" : [ {
                "actionId" : "actionId",
                "relationship" : "relationship"
              } ],
              "timingDateTime" : "2023-05-02",
              "participant" : [ { } ],
              "type" : {
                "text" : "type"
              },
              "groupingBehavior" : "groupingBehavior",
              "selectionBehavior" : "selectionBehavior",
              "requiredBehavior" : "requiredBehavior",
              "precheckBehavior" : "precheckBehavior",
              "cardinalityBehavior" : "cardinalityBehavior",
              "resource" : { },
              "action" : [ {
                "id" : "action2"
              } ]
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserialized = JacksonManager.objectMapper.readValue<RequestGroupAction>(json)
        assertEquals(action, deserialized)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val action = RequestGroupAction()

        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(action)

        val expectedJson =
            """
            { }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserialized = JacksonManager.objectMapper.readValue<RequestGroupAction>(json)
        assertEquals(action, deserialized)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            { }
            """.trimIndent()
        val action = JacksonManager.objectMapper.readValue<RequestGroupAction>(json)

        assertNull(action.id)
        assertEquals(emptyList<Extension>(), action.extension)
        assertEquals(emptyList<Extension>(), action.modifierExtension)
        assertNull(action.prefix)
        assertNull(action.title)
        assertNull(action.description)
        assertNull(action.textEquivalent)
        assertNull(action.priority)
        assertEquals(emptyList<CodeableConcept>(), action.code)
        assertEquals(emptyList<RelatedArtifact>(), action.documentation)
        assertEquals(emptyList<RequestGroupCondition>(), action.condition)
        assertEquals(emptyList<RequestGroupRelatedAction>(), action.relatedAction)
        assertNull(action.timing)
        assertEquals(emptyList<Reference>(), action.participant)
        assertNull(action.type)
        assertNull(action.groupingBehavior)
        assertNull(action.selectionBehavior)
        assertNull(action.requiredBehavior)
        assertNull(action.precheckBehavior)
        assertNull(action.cardinalityBehavior)
        assertNull(action.resource)
        assertEquals(emptyList<RequestGroupAction>(), action.action)
    }
}

class RequestGroupConditionTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val condition =
            RequestGroupCondition(
                id = "id".asFHIR(),
                extension = listOf(Extension("extId".asFHIR())),
                modifierExtension = listOf(Extension("extId".asFHIR())),
                kind = Code("kind"),
                expression = Expression(language = Code("language")),
            )

        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(condition)

        val expectedJson =
            """
            {
              "id" : "id",
              "extension" : [ {
                "id" : "extId"
              } ],
              "modifierExtension" : [ {
                "id" : "extId"
              } ],
              "kind" : "kind",
              "expression" : {
                "language" : "language"
              }
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserialized = JacksonManager.objectMapper.readValue<RequestGroupCondition>(json)
        assertEquals(condition, deserialized)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val condition =
            RequestGroupCondition(
                kind = Code("kind"),
            )

        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(condition)

        val expectedJson =
            """
            {
              "kind" : "kind"
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserialized = JacksonManager.objectMapper.readValue<RequestGroupCondition>(json)
        assertEquals(condition, deserialized)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            {
              "kind" : "kind"
            }
            """.trimIndent()
        val condition = JacksonManager.objectMapper.readValue<RequestGroupCondition>(json)

        assertNull(condition.id)
        assertEquals(emptyList<Extension>(), condition.extension)
        assertEquals(emptyList<Extension>(), condition.modifierExtension)
        assertEquals("kind", condition.kind?.value)
        assertNull(condition.expression)
    }
}

class RequestGroupRelatedActionTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val action =
            RequestGroupRelatedAction(
                id = "id".asFHIR(),
                extension = listOf(Extension("extId".asFHIR())),
                modifierExtension = listOf(Extension("extId".asFHIR())),
                actionId = Id("actionId"),
                relationship = Code("relationship"),
                offset = DynamicValue(type = DynamicValueType.DURATION, value = Duration(value = Decimal(12))),
            )

        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(action)

        val expectedJson =
            """
            {
              "id" : "id",
              "extension" : [ {
                "id" : "extId"
              } ],
              "modifierExtension" : [ {
                "id" : "extId"
              } ],
              "actionId" : "actionId",
              "relationship" : "relationship",
              "offsetDuration" : {
                "value" : 12.0
              }
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserialized = JacksonManager.objectMapper.readValue<RequestGroupRelatedAction>(json)
        assertEquals(action, deserialized)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val action =
            RequestGroupRelatedAction(
                actionId = Id("actionId"),
                relationship = Code("relationship"),
            )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(action)

        val expectedJson =
            """
            {
              "actionId" : "actionId",
              "relationship" : "relationship"
            }
            """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            {
              "actionId" : "actionId",
              "relationship" : "relationship"
            }
            """.trimIndent()
        val action = JacksonManager.objectMapper.readValue<RequestGroupRelatedAction>(json)

        assertNull(action.id)
        assertEquals(emptyList<Extension>(), action.extension)
        assertEquals(emptyList<Extension>(), action.modifierExtension)
        assertEquals("actionId", action.actionId?.value)
        assertEquals("relationship", action.relationship?.value)
        assertNull(action.offset)
    }
}
