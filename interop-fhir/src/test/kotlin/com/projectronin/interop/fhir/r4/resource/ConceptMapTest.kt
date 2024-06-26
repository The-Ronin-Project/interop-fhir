package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.ContactDetail
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.UsageContext
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.ConceptMapEquivalence
import com.projectronin.interop.fhir.r4.valueset.ConceptMapMode
import com.projectronin.interop.fhir.r4.valueset.ConceptMapStatus
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ConceptMapTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val source = DynamicValue(type = DynamicValueType.URI, value = Uri("sourceUri"))
        val target = DynamicValue(type = DynamicValueType.URI, value = Uri("targetUri"))
        val depends =
            ConceptMapDependsOn(
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
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                property = Uri("propertyUri"),
                system = Canonical("canonical"),
                value = FHIRString("value"),
                display = FHIRString("display"),
            )
        val elementTarget =
            ConceptMapTarget(
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
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                code = Code("test"),
                display = FHIRString("conceptMapElement"),
                equivalence = ConceptMapEquivalence.EQUIVALENT.asCode(),
                comment = FHIRString("Nope"),
                dependsOn = listOf(depends),
            )
        val element =
            ConceptMapElement(
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
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                code = Code("test"),
                display = FHIRString("conceptMapElement"),
                target = listOf(elementTarget),
            )
        val unmapped =
            ConceptMapUnmapped(
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
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                mode = ConceptMapMode.FIXED.asCode(),
                code = Code(value = "value"),
                display = FHIRString("unmapped"),
                uri = Canonical("canonical"),
            )

        val group =
            ConceptMapGroup(
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
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                source = Uri("groupSourceURI"),
                sourceVersion = FHIRString("source.V1"),
                target = Uri("groupTargetURI"),
                targetVersion = FHIRString("target.V1"),
                element = listOf(element),
                unmapped = unmapped,
            )
        val conceptMap =
            ConceptMap(
                id = Id("12345"),
                meta =
                    Meta(
                        profile = listOf(Canonical("RoninConceptMap/guid")),
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
                url = Uri("google.com"),
                identifier = Identifier(value = FHIRString("id")),
                version = FHIRString("v1"),
                name = FHIRString("name"),
                status = ConceptMapStatus.ACTIVE.asCode(),
                experimental = FHIRBoolean.FALSE,
                date = DateTime("2020"),
                publisher = FHIRString("Ronin"),
                contact = listOf(ContactDetail(name = FHIRString("Big Dawg"))),
                description = Markdown("text"),
                useContext =
                    listOf(
                        UsageContext(
                            code = Coding(),
                            value =
                                DynamicValue(
                                    type = DynamicValueType.REFERENCE,
                                    value = Reference(display = FHIRString("blah")),
                                ),
                        ),
                    ),
                jurisdiction = listOf(CodeableConcept(text = FHIRString("jurisdiction category"))),
                purpose = Markdown("text"),
                copyright = Markdown("text"),
                source = source,
                target = target,
                group = listOf(group),
            )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(conceptMap)
        print(json)
        val expectedJson =
            """
            {
              "resourceType" : "ConceptMap",
              "id" : "12345",
              "meta" : {
                "profile" : [ "RoninConceptMap/guid" ]
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
              "url" : "google.com",
              "identifier" : {
                "value" : "id"
              },
              "version" : "v1",
              "name" : "name",
              "status" : "active",
              "experimental" : false,
              "date" : "2020",
              "publisher" : "Ronin",
              "contact" : [ {
                "name" : "Big Dawg"
              } ],
              "description" : "text",
              "useContext" : [ {
                "code" : { },
                "valueReference" : {
                  "display" : "blah"
                }
              } ],
              "jurisdiction" : [ {
                "text" : "jurisdiction category"
              } ],
              "purpose" : "text",
              "copyright" : "text",
              "sourceUri" : "sourceUri",
              "targetUri" : "targetUri",
              "group" : [ {
                "id" : "12345",
                "extension" : [ {
                  "url" : "http://localhost/extension",
                  "valueString" : "Value"
                } ],
                "modifierExtension" : [ {
                  "url" : "http://localhost/modifier-extension",
                  "valueString" : "Value"
                } ],
                "source" : "groupSourceURI",
                "sourceVersion" : "source.V1",
                "target" : "groupTargetURI",
                "targetVersion" : "target.V1",
                "element" : [ {
                  "id" : "12345",
                  "extension" : [ {
                    "url" : "http://localhost/extension",
                    "valueString" : "Value"
                  } ],
                  "modifierExtension" : [ {
                    "url" : "http://localhost/modifier-extension",
                    "valueString" : "Value"
                  } ],
                  "code" : "test",
                  "display" : "conceptMapElement",
                  "target" : [ {
                    "id" : "12345",
                    "extension" : [ {
                      "url" : "http://localhost/extension",
                      "valueString" : "Value"
                    } ],
                    "modifierExtension" : [ {
                      "url" : "http://localhost/modifier-extension",
                      "valueString" : "Value"
                    } ],
                    "code" : "test",
                    "display" : "conceptMapElement",
                    "equivalence" : "equivalent",
                    "comment" : "Nope",
                    "dependsOn" : [ {
                      "id" : "12345",
                      "extension" : [ {
                        "url" : "http://localhost/extension",
                        "valueString" : "Value"
                      } ],
                      "modifierExtension" : [ {
                        "url" : "http://localhost/modifier-extension",
                        "valueString" : "Value"
                      } ],
                      "property" : "propertyUri",
                      "system" : "canonical",
                      "value" : "value",
                      "display" : "display"
                    } ]
                  } ]
                } ],
                "unmapped" : {
                  "id" : "12345",
                  "extension" : [ {
                    "url" : "http://localhost/extension",
                    "valueString" : "Value"
                  } ],
                  "modifierExtension" : [ {
                    "url" : "http://localhost/modifier-extension",
                    "valueString" : "Value"
                  } ],
                  "mode" : "fixed",
                  "code" : "value",
                  "display" : "unmapped",
                  "uri" : "canonical"
                }
              } ]
            }
            """.trimIndent()
        assertEquals(expectedJson, json)
        val deserializedConceptMap = JacksonManager.objectMapper.readValue<ConceptMap>(json)
        assertEquals(conceptMap, deserializedConceptMap)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val conceptMap =
            ConceptMap(
                status = ConceptMapStatus.ACTIVE.asCode(),
            )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(conceptMap)

        val expectedJson =
            """
            {
              "resourceType" : "ConceptMap",
              "status" : "active"
            }
            """.trimIndent()
        assertEquals(expectedJson, json)
    }
}
