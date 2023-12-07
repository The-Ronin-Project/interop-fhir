package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
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
import com.projectronin.interop.fhir.r4.datatype.primitive.Date
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.FilterOperator
import com.projectronin.interop.fhir.r4.valueset.PublicationStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ValueSetTest {
    companion object ConfiguredObjects {
        val designation =
            ValueSetDesignation(
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
                language = Code("en-US"),
                use =
                    Coding(
                        system = Uri("http://snomed.info/sct"),
                        code = Code("900000000000003001"),
                        display = FHIRString("Fully specified name"),
                    ),
                value = FHIRString("designation value"),
            )

        val concept =
            ValueSetConcept(
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
                code = Code("concept_code"),
                display = FHIRString("concept display"),
                designation = listOf(designation),
            )

        val filter =
            ValueSetFilter(
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
                property = Code("property_code"),
                op = FilterOperator.NOT_IN_SET.asCode(),
                value = FHIRString("filter value"),
            )

        val include =
            ValueSetInclude(
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
                system = Uri("http://localhost/include-system"),
                version = FHIRString("include_version"),
                concept = listOf(concept),
                filter = listOf(filter),
                valueSet =
                    listOf(
                        Canonical(
                            value = "canonical",
                        ),
                    ),
            )

        val exclude =
            ValueSetInclude(
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
                system = Uri("http://localhost/exclude-system"),
                version = FHIRString("exclude_version"),
                concept = listOf(concept),
                filter = listOf(filter),
                valueSet =
                    listOf(
                        Canonical(
                            value = "canonical",
                        ),
                    ),
            )

        val compose =
            ValueSetCompose(
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
                lockedDate = Date("2021-05-05"),
                inactive = FHIRBoolean(false),
                include = listOf(include),
                exclude = listOf(exclude),
            )

        val parameter =
            ValueSetParameter(
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
                name = FHIRString("parameter name"),
                value = DynamicValue(type = DynamicValueType.STRING, value = FHIRString("value_value")),
            )

        val containsChild =
            ValueSetContains(
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
                system = Uri("http://localhost/contains-system"),
                abstract = FHIRBoolean(false),
                inactive = FHIRBoolean(false),
                version = FHIRString("contains_version"),
                code = Code("contains_code"),
                display = FHIRString("contains display"),
                designation = listOf(designation),
            )

        val containsParent =
            ValueSetContains(
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
                system = Uri("http://localhost/contains-system"),
                abstract = FHIRBoolean(false),
                inactive = FHIRBoolean(false),
                version = FHIRString("contains_version"),
                code = Code("contains_code"),
                display = FHIRString("contains display"),
                designation = listOf(designation),
                contains = listOf(containsChild),
            )

        val expansion =
            ValueSetExpansion(
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
                identifier = Uri("http://localhost/expansion-identifier"),
                timestamp = DateTime("2020-01-01 14:01:01"),
                total = FHIRInteger(13),
                offset = FHIRInteger(12),
                parameter = listOf(parameter),
                contains = listOf(containsParent),
            )

        val valueSet =
            ValueSet(
                id = Id("12345"),
                meta =
                    Meta(
                        profile = listOf(Canonical("RoninConceptMap/guid")),
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
                url = Uri("http://localhost/valueset-uri"),
                identifier = listOf(Identifier(value = FHIRString("valueset_identifier"))),
                version = FHIRString("valueset_version"),
                name = FHIRString("valueset_name"),
                title = FHIRString("valueset_title"),
                status = PublicationStatus.DRAFT.asCode(),
                experimental = FHIRBoolean(false),
                date = DateTime("2021-02-03 12:09:35"),
                publisher = FHIRString("valueset_publisher"),
                contact =
                    listOf(
                        ContactDetail(
                            name = FHIRString("valueset_contactdetail"),
                        ),
                    ),
                description = Markdown("valueset_description"),
                useContext =
                    listOf(
                        UsageContext(
                            code =
                                Coding(
                                    version = FHIRString("valueset_usagecontextcode"),
                                ),
                            value =
                                DynamicValue(
                                    type = DynamicValueType.REFERENCE,
                                    value = Reference(display = FHIRString("valueset_usagecontextvaluedisplay")),
                                ),
                        ),
                    ),
                jurisdiction =
                    listOf(
                        CodeableConcept(
                            id = FHIRString("valueset_jurisdictioncodeableconcept"),
                        ),
                    ),
                immutable = FHIRBoolean(true),
                purpose = Markdown("valueset_purpose"),
                copyright = Markdown("valueset_copyright"),
                compose = compose,
                expansion = expansion,
            )
    }

    @Test
    fun `can serialize and deserialize JSON`() {
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(valueSet)
        val expectedJson =
            """
            {
              "resourceType" : "ValueSet",
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
              "url" : "http://localhost/valueset-uri",
              "identifier" : [ {
                "value" : "valueset_identifier"
              } ],
              "version" : "valueset_version",
              "name" : "valueset_name",
              "title" : "valueset_title",
              "status" : "draft",
              "experimental" : false,
              "date" : "2021-02-03 12:09:35",
              "publisher" : "valueset_publisher",
              "contact" : [ {
                "name" : "valueset_contactdetail"
              } ],
              "description" : "valueset_description",
              "useContext" : [ {
                "code" : {
                  "version" : "valueset_usagecontextcode"
                },
                "valueReference" : {
                  "display" : "valueset_usagecontextvaluedisplay"
                }
              } ],
              "jurisdiction" : [ {
                "id" : "valueset_jurisdictioncodeableconcept"
              } ],
              "immutable" : true,
              "purpose" : "valueset_purpose",
              "copyright" : "valueset_copyright",
              "compose" : {
                "id" : "12345",
                "extension" : [ {
                  "url" : "http://localhost/extension",
                  "valueString" : "Value"
                } ],
                "modifierExtension" : [ {
                  "url" : "http://localhost/modifier-extension",
                  "valueString" : "Value"
                } ],
                "lockedDate" : "2021-05-05",
                "inactive" : false,
                "include" : [ {
                  "id" : "12345",
                  "extension" : [ {
                    "url" : "http://localhost/extension",
                    "valueString" : "Value"
                  } ],
                  "modifierExtension" : [ {
                    "url" : "http://localhost/modifier-extension",
                    "valueString" : "Value"
                  } ],
                  "system" : "http://localhost/include-system",
                  "version" : "include_version",
                  "concept" : [ {
                    "id" : "12345",
                    "extension" : [ {
                      "url" : "http://localhost/extension",
                      "valueString" : "Value"
                    } ],
                    "modifierExtension" : [ {
                      "url" : "http://localhost/modifier-extension",
                      "valueString" : "Value"
                    } ],
                    "code" : "concept_code",
                    "display" : "concept display",
                    "designation" : [ {
                      "id" : "12345",
                      "extension" : [ {
                        "url" : "http://localhost/extension",
                        "valueString" : "Value"
                      } ],
                      "modifierExtension" : [ {
                        "url" : "http://localhost/modifier-extension",
                        "valueString" : "Value"
                      } ],
                      "language" : "en-US",
                      "use" : {
                        "system" : "http://snomed.info/sct",
                        "code" : "900000000000003001",
                        "display" : "Fully specified name"
                      },
                      "value" : "designation value"
                    } ]
                  } ],
                  "filter" : [ {
                    "id" : "12345",
                    "extension" : [ {
                      "url" : "http://localhost/extension",
                      "valueString" : "Value"
                    } ],
                    "modifierExtension" : [ {
                      "url" : "http://localhost/modifier-extension",
                      "valueString" : "Value"
                    } ],
                    "property" : "property_code",
                    "op" : "not-in",
                    "value" : "filter value"
                  } ],
                  "valueSet" : [ "canonical" ]
                } ],
                "exclude" : [ {
                  "id" : "12345",
                  "extension" : [ {
                    "url" : "http://localhost/extension",
                    "valueString" : "Value"
                  } ],
                  "modifierExtension" : [ {
                    "url" : "http://localhost/modifier-extension",
                    "valueString" : "Value"
                  } ],
                  "system" : "http://localhost/exclude-system",
                  "version" : "exclude_version",
                  "concept" : [ {
                    "id" : "12345",
                    "extension" : [ {
                      "url" : "http://localhost/extension",
                      "valueString" : "Value"
                    } ],
                    "modifierExtension" : [ {
                      "url" : "http://localhost/modifier-extension",
                      "valueString" : "Value"
                    } ],
                    "code" : "concept_code",
                    "display" : "concept display",
                    "designation" : [ {
                      "id" : "12345",
                      "extension" : [ {
                        "url" : "http://localhost/extension",
                        "valueString" : "Value"
                      } ],
                      "modifierExtension" : [ {
                        "url" : "http://localhost/modifier-extension",
                        "valueString" : "Value"
                      } ],
                      "language" : "en-US",
                      "use" : {
                        "system" : "http://snomed.info/sct",
                        "code" : "900000000000003001",
                        "display" : "Fully specified name"
                      },
                      "value" : "designation value"
                    } ]
                  } ],
                  "filter" : [ {
                    "id" : "12345",
                    "extension" : [ {
                      "url" : "http://localhost/extension",
                      "valueString" : "Value"
                    } ],
                    "modifierExtension" : [ {
                      "url" : "http://localhost/modifier-extension",
                      "valueString" : "Value"
                    } ],
                    "property" : "property_code",
                    "op" : "not-in",
                    "value" : "filter value"
                  } ],
                  "valueSet" : [ "canonical" ]
                } ]
              },
              "expansion" : {
                "id" : "12345",
                "extension" : [ {
                  "url" : "http://localhost/extension",
                  "valueString" : "Value"
                } ],
                "modifierExtension" : [ {
                  "url" : "http://localhost/modifier-extension",
                  "valueString" : "Value"
                } ],
                "identifier" : "http://localhost/expansion-identifier",
                "timestamp" : "2020-01-01 14:01:01",
                "total" : 13,
                "offset" : 12,
                "parameter" : [ {
                  "id" : "12345",
                  "extension" : [ {
                    "url" : "http://localhost/extension",
                    "valueString" : "Value"
                  } ],
                  "modifierExtension" : [ {
                    "url" : "http://localhost/modifier-extension",
                    "valueString" : "Value"
                  } ],
                  "name" : "parameter name",
                  "valueString" : "value_value"
                } ],
                "contains" : [ {
                  "id" : "12345",
                  "extension" : [ {
                    "url" : "http://localhost/extension",
                    "valueString" : "Value"
                  } ],
                  "modifierExtension" : [ {
                    "url" : "http://localhost/modifier-extension",
                    "valueString" : "Value"
                  } ],
                  "system" : "http://localhost/contains-system",
                  "abstract" : false,
                  "inactive" : false,
                  "version" : "contains_version",
                  "code" : "contains_code",
                  "display" : "contains display",
                  "designation" : [ {
                    "id" : "12345",
                    "extension" : [ {
                      "url" : "http://localhost/extension",
                      "valueString" : "Value"
                    } ],
                    "modifierExtension" : [ {
                      "url" : "http://localhost/modifier-extension",
                      "valueString" : "Value"
                    } ],
                    "language" : "en-US",
                    "use" : {
                      "system" : "http://snomed.info/sct",
                      "code" : "900000000000003001",
                      "display" : "Fully specified name"
                    },
                    "value" : "designation value"
                  } ],
                  "contains" : [ {
                    "id" : "12345",
                    "extension" : [ {
                      "url" : "http://localhost/extension",
                      "valueString" : "Value"
                    } ],
                    "modifierExtension" : [ {
                      "url" : "http://localhost/modifier-extension",
                      "valueString" : "Value"
                    } ],
                    "system" : "http://localhost/contains-system",
                    "abstract" : false,
                    "inactive" : false,
                    "version" : "contains_version",
                    "code" : "contains_code",
                    "display" : "contains display",
                    "designation" : [ {
                      "id" : "12345",
                      "extension" : [ {
                        "url" : "http://localhost/extension",
                        "valueString" : "Value"
                      } ],
                      "modifierExtension" : [ {
                        "url" : "http://localhost/modifier-extension",
                        "valueString" : "Value"
                      } ],
                      "language" : "en-US",
                      "use" : {
                        "system" : "http://snomed.info/sct",
                        "code" : "900000000000003001",
                        "display" : "Fully specified name"
                      },
                      "value" : "designation value"
                    } ]
                  } ]
                } ]
              }
            }
            """.trimIndent()

        assertEquals(expectedJson, json)

        val deserializedValueSet = objectMapper.readValue<ValueSet>(json)
        assertEquals(valueSet, deserializedValueSet)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val valueSet =
            ValueSet(
                status = PublicationStatus.RETIRED.asCode(),
            )

        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(valueSet)
        val expectedJson =
            """
            {
              "resourceType" : "ValueSet",
              "status" : "retired"
            }
            """.trimIndent()

        assertEquals(expectedJson, json)

        val deserializedValueSet = objectMapper.readValue<ValueSet>(json)
        assertEquals(valueSet, deserializedValueSet)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            {
              "resourceType" : "ValueSet",
              "status" : "unknown"
            }
            """.trimIndent()

        val valueSet = objectMapper.readValue<ValueSet>(json)
        valueSet.apply {
            assertNull(id)
            assertNull(meta)
            assertNull(implicitRules)
            assertNull(language)
            assertNull(text)
            assertEquals(listOf<Resource<*>>(), contained)
            assertEquals(listOf<Extension>(), extension)
            assertEquals(listOf<Extension>(), modifierExtension)
            assertNull(url)
            assertEquals(listOf<Identifier>(), identifier)
            assertNull(version)
            assertNull(name)
            assertNull(title)
            assertEquals(Code("unknown"), status)
            assertNull(experimental)
            assertNull(date)
            assertNull(publisher)
            assertEquals(listOf<ContactDetail>(), contact)
            assertNull(description)
            assertEquals(listOf<UsageContext>(), useContext)
            assertEquals(listOf<CodeableConcept>(), jurisdiction)
            assertNull(immutable)
            assertNull(purpose)
            assertNull(copyright)
            assertNull(compose)
            assertNull(expansion)
        }
    }

    @Test
    fun `can deserialize from JSON with missing required field`() {
        val json =
            """
            {
              "resourceType" : "ValueSet"
            }
            """.trimIndent()

        val valueSet = objectMapper.readValue<ValueSet>(json)
        valueSet.apply {
            assertNull(id)
            assertNull(meta)
            assertNull(implicitRules)
            assertNull(language)
            assertNull(text)
            assertEquals(listOf<Resource<*>>(), contained)
            assertEquals(listOf<Extension>(), extension)
            assertEquals(listOf<Extension>(), modifierExtension)
            assertNull(url)
            assertEquals(listOf<Identifier>(), identifier)
            assertNull(version)
            assertNull(name)
            assertNull(title)
            assertNull(status)
            assertNull(experimental)
            assertNull(date)
            assertNull(publisher)
            assertEquals(listOf<ContactDetail>(), contact)
            assertNull(description)
            assertEquals(listOf<UsageContext>(), useContext)
            assertEquals(listOf<CodeableConcept>(), jurisdiction)
            assertNull(immutable)
            assertNull(purpose)
            assertNull(copyright)
            assertNull(compose)
            assertNull(expansion)
        }
    }
}

class ValueSetParameterTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ValueSetTest.parameter)
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
                "valueString" : "Value"
              } ],
              "name" : "parameter name",
              "valueString" : "value_value"
            }
            """.trimIndent()

        assertEquals(expectedJson, json)

        val deserializedValueSetParameter = objectMapper.readValue<ValueSetParameter>(json)
        assertEquals(ValueSetTest.parameter, deserializedValueSetParameter)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val parameter =
            ValueSetParameter(
                name = FHIRString("name"),
            )

        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(parameter)
        val expectedJson =
            """
            {
              "name" : "name"
            }
            """.trimIndent()

        assertEquals(expectedJson, json)

        val deserializedValueSetParameter = objectMapper.readValue<ValueSetParameter>(json)
        assertEquals(parameter, deserializedValueSetParameter)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            {
              "name" : "name"
            }
            """.trimIndent()

        val parameter = objectMapper.readValue<ValueSetParameter>(json)
        parameter.apply {
            assertNull(id)
            assertEquals(listOf<Extension>(), extension)
            assertEquals(listOf<Extension>(), modifierExtension)
            assertEquals(FHIRString("name"), name)
            assertNull(value)
        }
    }
}

class ValueSetContainsTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ValueSetTest.containsParent)
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
                "valueString" : "Value"
              } ],
              "system" : "http://localhost/contains-system",
              "abstract" : false,
              "inactive" : false,
              "version" : "contains_version",
              "code" : "contains_code",
              "display" : "contains display",
              "designation" : [ {
                "id" : "12345",
                "extension" : [ {
                  "url" : "http://localhost/extension",
                  "valueString" : "Value"
                } ],
                "modifierExtension" : [ {
                  "url" : "http://localhost/modifier-extension",
                  "valueString" : "Value"
                } ],
                "language" : "en-US",
                "use" : {
                  "system" : "http://snomed.info/sct",
                  "code" : "900000000000003001",
                  "display" : "Fully specified name"
                },
                "value" : "designation value"
              } ],
              "contains" : [ {
                "id" : "12345",
                "extension" : [ {
                  "url" : "http://localhost/extension",
                  "valueString" : "Value"
                } ],
                "modifierExtension" : [ {
                  "url" : "http://localhost/modifier-extension",
                  "valueString" : "Value"
                } ],
                "system" : "http://localhost/contains-system",
                "abstract" : false,
                "inactive" : false,
                "version" : "contains_version",
                "code" : "contains_code",
                "display" : "contains display",
                "designation" : [ {
                  "id" : "12345",
                  "extension" : [ {
                    "url" : "http://localhost/extension",
                    "valueString" : "Value"
                  } ],
                  "modifierExtension" : [ {
                    "url" : "http://localhost/modifier-extension",
                    "valueString" : "Value"
                  } ],
                  "language" : "en-US",
                  "use" : {
                    "system" : "http://snomed.info/sct",
                    "code" : "900000000000003001",
                    "display" : "Fully specified name"
                  },
                  "value" : "designation value"
                } ]
              } ]
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedValueSetContains = objectMapper.readValue<ValueSetContains>(json)
        assertEquals(ValueSetTest.containsParent, deserializedValueSetContains)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val contains = ValueSetContains()

        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(contains)
        val expectedJson =
            """
            { }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedValueSetContains = objectMapper.readValue<ValueSetContains>(json)
        assertEquals(contains, deserializedValueSetContains)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            { }
            """.trimIndent()

        val contains = objectMapper.readValue<ValueSetContains>(json)
        contains.apply {
            assertNull(id)
            assertEquals(listOf<Extension>(), extension)
            assertEquals(listOf<Extension>(), modifierExtension)
            assertNull(system)
            assertNull(abstract)
            assertNull(inactive)
            assertNull(version)
            assertNull(code)
            assertNull(display)
            assertEquals(listOf<ValueSetDesignation>(), designation)
            assertEquals(listOf<ValueSetContains>(), contains.contains)
        }
    }
}

class ValueSetExpansionTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ValueSetTest.expansion)
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
                "valueString" : "Value"
              } ],
              "identifier" : "http://localhost/expansion-identifier",
              "timestamp" : "2020-01-01 14:01:01",
              "total" : 13,
              "offset" : 12,
              "parameter" : [ {
                "id" : "12345",
                "extension" : [ {
                  "url" : "http://localhost/extension",
                  "valueString" : "Value"
                } ],
                "modifierExtension" : [ {
                  "url" : "http://localhost/modifier-extension",
                  "valueString" : "Value"
                } ],
                "name" : "parameter name",
                "valueString" : "value_value"
              } ],
              "contains" : [ {
                "id" : "12345",
                "extension" : [ {
                  "url" : "http://localhost/extension",
                  "valueString" : "Value"
                } ],
                "modifierExtension" : [ {
                  "url" : "http://localhost/modifier-extension",
                  "valueString" : "Value"
                } ],
                "system" : "http://localhost/contains-system",
                "abstract" : false,
                "inactive" : false,
                "version" : "contains_version",
                "code" : "contains_code",
                "display" : "contains display",
                "designation" : [ {
                  "id" : "12345",
                  "extension" : [ {
                    "url" : "http://localhost/extension",
                    "valueString" : "Value"
                  } ],
                  "modifierExtension" : [ {
                    "url" : "http://localhost/modifier-extension",
                    "valueString" : "Value"
                  } ],
                  "language" : "en-US",
                  "use" : {
                    "system" : "http://snomed.info/sct",
                    "code" : "900000000000003001",
                    "display" : "Fully specified name"
                  },
                  "value" : "designation value"
                } ],
                "contains" : [ {
                  "id" : "12345",
                  "extension" : [ {
                    "url" : "http://localhost/extension",
                    "valueString" : "Value"
                  } ],
                  "modifierExtension" : [ {
                    "url" : "http://localhost/modifier-extension",
                    "valueString" : "Value"
                  } ],
                  "system" : "http://localhost/contains-system",
                  "abstract" : false,
                  "inactive" : false,
                  "version" : "contains_version",
                  "code" : "contains_code",
                  "display" : "contains display",
                  "designation" : [ {
                    "id" : "12345",
                    "extension" : [ {
                      "url" : "http://localhost/extension",
                      "valueString" : "Value"
                    } ],
                    "modifierExtension" : [ {
                      "url" : "http://localhost/modifier-extension",
                      "valueString" : "Value"
                    } ],
                    "language" : "en-US",
                    "use" : {
                      "system" : "http://snomed.info/sct",
                      "code" : "900000000000003001",
                      "display" : "Fully specified name"
                    },
                    "value" : "designation value"
                  } ]
                } ]
              } ]
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedValueSetExpansion = objectMapper.readValue<ValueSetExpansion>(json)
        assertEquals(ValueSetTest.expansion, deserializedValueSetExpansion)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val expansion =
            ValueSetExpansion(
                timestamp = DateTime("1986-04-03"),
            )

        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(expansion)
        val expectedJson =
            """
            {
              "timestamp" : "1986-04-03"
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedValueSetExpansion = objectMapper.readValue<ValueSetExpansion>(json)
        assertEquals(expansion, deserializedValueSetExpansion)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            {
              "timestamp" : "1986-04-03"
            }
            """.trimIndent()

        val expansion = objectMapper.readValue<ValueSetExpansion>(json)
        expansion.apply {
            assertNull(id)
            assertEquals(listOf<Extension>(), extension)
            assertEquals(listOf<Extension>(), modifierExtension)
            assertNull(identifier)
            assertEquals(DateTime("1986-04-03"), timestamp)
            assertNull(total)
            assertNull(offset)
            assertEquals(listOf<ValueSetParameter>(), parameter)
            assertEquals(listOf<ValueSetContains>(), contains)
        }
    }
}

class ValueSetDesignationTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ValueSetTest.designation)
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
                "valueString" : "Value"
              } ],
              "language" : "en-US",
              "use" : {
                "system" : "http://snomed.info/sct",
                "code" : "900000000000003001",
                "display" : "Fully specified name"
              },
              "value" : "designation value"
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedValueSetDesignation = objectMapper.readValue<ValueSetDesignation>(json)
        assertEquals(ValueSetTest.designation, deserializedValueSetDesignation)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val designation =
            ValueSetDesignation(
                value = FHIRString("value"),
            )

        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(designation)
        val expectedJson =
            """
            {
              "value" : "value"
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedValueSetDesignation = objectMapper.readValue<ValueSetDesignation>(json)
        assertEquals(designation, deserializedValueSetDesignation)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            {
              "value" : "value"
            }
            """.trimIndent()

        val designation = objectMapper.readValue<ValueSetDesignation>(json)
        designation.apply {
            assertNull(id)
            assertEquals(listOf<Extension>(), extension)
            assertEquals(listOf<Extension>(), modifierExtension)
            assertNull(language)
            assertNull(use)
            assertEquals(FHIRString("value"), value)
        }
    }
}

class ValueSetConceptTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ValueSetTest.concept)
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
                "valueString" : "Value"
              } ],
              "code" : "concept_code",
              "display" : "concept display",
              "designation" : [ {
                "id" : "12345",
                "extension" : [ {
                  "url" : "http://localhost/extension",
                  "valueString" : "Value"
                } ],
                "modifierExtension" : [ {
                  "url" : "http://localhost/modifier-extension",
                  "valueString" : "Value"
                } ],
                "language" : "en-US",
                "use" : {
                  "system" : "http://snomed.info/sct",
                  "code" : "900000000000003001",
                  "display" : "Fully specified name"
                },
                "value" : "designation value"
              } ]
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedValueSetConcept = objectMapper.readValue<ValueSetConcept>(json)
        assertEquals(ValueSetTest.concept, deserializedValueSetConcept)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val concept =
            ValueSetConcept(
                code = Code("code"),
            )

        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(concept)
        val expectedJson =
            """
            {
              "code" : "code"
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedValueSetConcept = objectMapper.readValue<ValueSetConcept>(json)
        assertEquals(concept, deserializedValueSetConcept)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            {
              "code" : "code"
            }
            """.trimIndent()

        val concept = objectMapper.readValue<ValueSetConcept>(json)
        concept.apply {
            assertEquals(Code("code"), code)
            assertNull(display)
            assertEquals(listOf<ValueSetDesignation>(), designation)
        }
    }
}

class ValueSetFilterTest {
    // All properties are required for this element: no null-property tests
    @Test
    fun `can serialize and deserialize JSON`() {
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ValueSetTest.filter)
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
                "valueString" : "Value"
              } ],
              "property" : "property_code",
              "op" : "not-in",
              "value" : "filter value"
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedValueSetFilter = objectMapper.readValue<ValueSetFilter>(json)
        assertEquals(ValueSetTest.filter, deserializedValueSetFilter)
    }
}

class ValueSetIncludeTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ValueSetTest.include)
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
                "valueString" : "Value"
              } ],
              "system" : "http://localhost/include-system",
              "version" : "include_version",
              "concept" : [ {
                "id" : "12345",
                "extension" : [ {
                  "url" : "http://localhost/extension",
                  "valueString" : "Value"
                } ],
                "modifierExtension" : [ {
                  "url" : "http://localhost/modifier-extension",
                  "valueString" : "Value"
                } ],
                "code" : "concept_code",
                "display" : "concept display",
                "designation" : [ {
                  "id" : "12345",
                  "extension" : [ {
                    "url" : "http://localhost/extension",
                    "valueString" : "Value"
                  } ],
                  "modifierExtension" : [ {
                    "url" : "http://localhost/modifier-extension",
                    "valueString" : "Value"
                  } ],
                  "language" : "en-US",
                  "use" : {
                    "system" : "http://snomed.info/sct",
                    "code" : "900000000000003001",
                    "display" : "Fully specified name"
                  },
                  "value" : "designation value"
                } ]
              } ],
              "filter" : [ {
                "id" : "12345",
                "extension" : [ {
                  "url" : "http://localhost/extension",
                  "valueString" : "Value"
                } ],
                "modifierExtension" : [ {
                  "url" : "http://localhost/modifier-extension",
                  "valueString" : "Value"
                } ],
                "property" : "property_code",
                "op" : "not-in",
                "value" : "filter value"
              } ],
              "valueSet" : [ "canonical" ]
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedValueSetInclude = objectMapper.readValue<ValueSetInclude>(json)
        assertEquals(ValueSetTest.include, deserializedValueSetInclude)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val include = ValueSetInclude()
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(include)
        val expectedJson =
            """
            { }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedValueSetInclude = objectMapper.readValue<ValueSetInclude>(json)
        assertEquals(include, deserializedValueSetInclude)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            { }
            """.trimIndent()

        val include = objectMapper.readValue<ValueSetInclude>(json)
        include.apply {
            assertNull(id)
            assertEquals(listOf<Extension>(), extension)
            assertEquals(listOf<Extension>(), modifierExtension)
            assertNull(system)
            assertNull(version)
            assertEquals(listOf<ValueSetConcept>(), concept)
            assertEquals(listOf<ValueSetFilter>(), filter)
            assertEquals(listOf<ValueSet>(), valueSet)
        }
    }
}

class ValueSetComposeTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ValueSetTest.compose)
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
                "valueString" : "Value"
              } ],
              "lockedDate" : "2021-05-05",
              "inactive" : false,
              "include" : [ {
                "id" : "12345",
                "extension" : [ {
                  "url" : "http://localhost/extension",
                  "valueString" : "Value"
                } ],
                "modifierExtension" : [ {
                  "url" : "http://localhost/modifier-extension",
                  "valueString" : "Value"
                } ],
                "system" : "http://localhost/include-system",
                "version" : "include_version",
                "concept" : [ {
                  "id" : "12345",
                  "extension" : [ {
                    "url" : "http://localhost/extension",
                    "valueString" : "Value"
                  } ],
                  "modifierExtension" : [ {
                    "url" : "http://localhost/modifier-extension",
                    "valueString" : "Value"
                  } ],
                  "code" : "concept_code",
                  "display" : "concept display",
                  "designation" : [ {
                    "id" : "12345",
                    "extension" : [ {
                      "url" : "http://localhost/extension",
                      "valueString" : "Value"
                    } ],
                    "modifierExtension" : [ {
                      "url" : "http://localhost/modifier-extension",
                      "valueString" : "Value"
                    } ],
                    "language" : "en-US",
                    "use" : {
                      "system" : "http://snomed.info/sct",
                      "code" : "900000000000003001",
                      "display" : "Fully specified name"
                    },
                    "value" : "designation value"
                  } ]
                } ],
                "filter" : [ {
                  "id" : "12345",
                  "extension" : [ {
                    "url" : "http://localhost/extension",
                    "valueString" : "Value"
                  } ],
                  "modifierExtension" : [ {
                    "url" : "http://localhost/modifier-extension",
                    "valueString" : "Value"
                  } ],
                  "property" : "property_code",
                  "op" : "not-in",
                  "value" : "filter value"
                } ],
                "valueSet" : [ "canonical" ]
              } ],
              "exclude" : [ {
                "id" : "12345",
                "extension" : [ {
                  "url" : "http://localhost/extension",
                  "valueString" : "Value"
                } ],
                "modifierExtension" : [ {
                  "url" : "http://localhost/modifier-extension",
                  "valueString" : "Value"
                } ],
                "system" : "http://localhost/exclude-system",
                "version" : "exclude_version",
                "concept" : [ {
                  "id" : "12345",
                  "extension" : [ {
                    "url" : "http://localhost/extension",
                    "valueString" : "Value"
                  } ],
                  "modifierExtension" : [ {
                    "url" : "http://localhost/modifier-extension",
                    "valueString" : "Value"
                  } ],
                  "code" : "concept_code",
                  "display" : "concept display",
                  "designation" : [ {
                    "id" : "12345",
                    "extension" : [ {
                      "url" : "http://localhost/extension",
                      "valueString" : "Value"
                    } ],
                    "modifierExtension" : [ {
                      "url" : "http://localhost/modifier-extension",
                      "valueString" : "Value"
                    } ],
                    "language" : "en-US",
                    "use" : {
                      "system" : "http://snomed.info/sct",
                      "code" : "900000000000003001",
                      "display" : "Fully specified name"
                    },
                    "value" : "designation value"
                  } ]
                } ],
                "filter" : [ {
                  "id" : "12345",
                  "extension" : [ {
                    "url" : "http://localhost/extension",
                    "valueString" : "Value"
                  } ],
                  "modifierExtension" : [ {
                    "url" : "http://localhost/modifier-extension",
                    "valueString" : "Value"
                  } ],
                  "property" : "property_code",
                  "op" : "not-in",
                  "value" : "filter value"
                } ],
                "valueSet" : [ "canonical" ]
              } ]
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedValueSetCompose = objectMapper.readValue<ValueSetCompose>(json)
        assertEquals(ValueSetTest.compose, deserializedValueSetCompose)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val compose =
            ValueSetCompose(
                include = listOf(ValueSetTest.include),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(compose)
        val expectedJson =
            """
            {
              "include" : [ {
                "id" : "12345",
                "extension" : [ {
                  "url" : "http://localhost/extension",
                  "valueString" : "Value"
                } ],
                "modifierExtension" : [ {
                  "url" : "http://localhost/modifier-extension",
                  "valueString" : "Value"
                } ],
                "system" : "http://localhost/include-system",
                "version" : "include_version",
                "concept" : [ {
                  "id" : "12345",
                  "extension" : [ {
                    "url" : "http://localhost/extension",
                    "valueString" : "Value"
                  } ],
                  "modifierExtension" : [ {
                    "url" : "http://localhost/modifier-extension",
                    "valueString" : "Value"
                  } ],
                  "code" : "concept_code",
                  "display" : "concept display",
                  "designation" : [ {
                    "id" : "12345",
                    "extension" : [ {
                      "url" : "http://localhost/extension",
                      "valueString" : "Value"
                    } ],
                    "modifierExtension" : [ {
                      "url" : "http://localhost/modifier-extension",
                      "valueString" : "Value"
                    } ],
                    "language" : "en-US",
                    "use" : {
                      "system" : "http://snomed.info/sct",
                      "code" : "900000000000003001",
                      "display" : "Fully specified name"
                    },
                    "value" : "designation value"
                  } ]
                } ],
                "filter" : [ {
                  "id" : "12345",
                  "extension" : [ {
                    "url" : "http://localhost/extension",
                    "valueString" : "Value"
                  } ],
                  "modifierExtension" : [ {
                    "url" : "http://localhost/modifier-extension",
                    "valueString" : "Value"
                  } ],
                  "property" : "property_code",
                  "op" : "not-in",
                  "value" : "filter value"
                } ],
                "valueSet" : [ "canonical" ]
              } ]
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedValueSetCompose = objectMapper.readValue<ValueSetCompose>(json)
        assertEquals(compose, deserializedValueSetCompose)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            {
              "include" : [ {
                "id" : "12345",
                "extension" : [ {
                  "url" : "http://localhost/extension",
                  "valueString" : "Value"
                } ],
                "modifierExtension" : [ {
                  "url" : "http://localhost/modifier-extension",
                  "valueString" : "Value"
                } ],
                "system" : "http://localhost/include-system",
                "version" : "include_version",
                "concept" : [ {
                  "id" : "12345",
                  "extension" : [ {
                    "url" : "http://localhost/extension",
                    "valueString" : "Value"
                  } ],
                  "modifierExtension" : [ {
                    "url" : "http://localhost/modifier-extension",
                    "valueString" : "Value"
                  } ],
                  "code" : "concept_code",
                  "display" : "concept display",
                  "designation" : [ {
                    "id" : "12345",
                    "extension" : [ {
                      "url" : "http://localhost/extension",
                      "valueString" : "Value"
                    } ],
                    "modifierExtension" : [ {
                      "url" : "http://localhost/modifier-extension",
                      "valueString" : "Value"
                    } ],
                    "language" : "en-US",
                    "use" : {
                      "system" : "http://snomed.info/sct",
                      "code" : "900000000000003001",
                      "display" : "Fully specified name"
                    },
                    "value" : "designation value"
                  } ]
                } ],
                "filter" : [ {
                  "id" : "12345",
                  "extension" : [ {
                    "url" : "http://localhost/extension",
                    "valueString" : "Value"
                  } ],
                  "modifierExtension" : [ {
                    "url" : "http://localhost/modifier-extension",
                    "valueString" : "Value"
                  } ],
                  "property" : "property_code",
                  "op" : "not-in",
                  "value" : "filter value"
                } ],
                "valueSet" : [ "canonical" ]
              } ]
            }
            """.trimIndent()
        val compose = objectMapper.readValue<ValueSetCompose>(json)
        compose.apply {
            assertNull(id)
            assertEquals(listOf<Extension>(), extension)
            assertEquals(listOf<Extension>(), modifierExtension)
            assertNull(lockedDate)
            assertNull(inactive)
            assertEquals(1, include?.size)
        }
    }
}
