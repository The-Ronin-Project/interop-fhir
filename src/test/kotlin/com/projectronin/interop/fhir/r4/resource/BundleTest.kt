package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.HumanName
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.Signature
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.BundleType
import com.projectronin.interop.fhir.r4.valueset.HttpVerb
import com.projectronin.interop.fhir.r4.valueset.SearchEntryMode
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BundleTest {
    @Test
    fun `can serialize and deserialize JSON with known resource type`() {
        val patient =
            Patient(
                id = Id("5678"),
                name = listOf(HumanName(family = FHIRString("Doe"), given = listOf(FHIRString("John"))))
            )
        val bundle = Bundle(
            id = Id("1234"),
            meta = Meta(profile = listOf(Canonical("RoninPractitioner"))),
            implicitRules = Uri("implicit-rules"),
            language = Code("en-US"),
            identifier = Identifier(value = FHIRString("identifier")),
            type = BundleType.SEARCHSET.asCode(),
            timestamp = Instant("2017-01-01T00:00:00Z"),
            total = UnsignedInt(1),
            link = listOf(BundleLink(relation = FHIRString("next"), url = Uri("http://example.com"))),
            entry = listOf(BundleEntry(resource = patient)),
            signature = Signature(
                type = listOf(Coding(display = FHIRString("type"))),
                `when` = Instant("2017-01-01T00:00:00Z"),
                who = Reference(reference = FHIRString("who"))
            )
        )

        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bundle)

        val expectedJson = """
            {
              "resourceType" : "Bundle",
              "id" : "1234",
              "meta" : {
                "profile" : [ "RoninPractitioner" ]
              },
              "implicitRules" : "implicit-rules",
              "language" : "en-US",
              "identifier" : {
                "value" : "identifier"
              },
              "type" : "searchset",
              "timestamp" : "2017-01-01T00:00:00Z",
              "total" : 1,
              "link" : [ {
                "relation" : "next",
                "url" : "http://example.com"
              } ],
              "entry" : [ {
                "resource" : {
                  "resourceType" : "Patient",
                  "id" : "5678",
                  "name" : [ {
                    "family" : "Doe",
                    "given" : [ "John" ]
                  } ]
                }
              } ],
              "signature" : {
                "type" : [ {
                  "display" : "type"
                } ],
                "when" : "2017-01-01T00:00:00Z",
                "who" : {
                  "reference" : "who"
                }
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedBundle = objectMapper.readValue<Bundle>(json)
        assertEquals(bundle, deserializedBundle)
    }

    @Test
    fun `can serialize and deserialize JSON with unknown resource type`() {
        val unknownResource = UnknownResource(
            resourceType = "Banana",
            id = Id("5678"),
            otherData = mapOf("key" to "value", "key2" to mapOf("value" to "sub value"))
        )
        val bundle = Bundle(
            id = Id("1234"),
            meta = Meta(profile = listOf(Canonical("RoninPractitioner"))),
            implicitRules = Uri("implicit-rules"),
            language = Code("en-US"),
            identifier = Identifier(value = FHIRString("identifier")),
            type = BundleType.SEARCHSET.asCode(),
            timestamp = Instant("2017-01-01T00:00:00Z"),
            total = UnsignedInt(1),
            link = listOf(BundleLink(relation = FHIRString("next"), url = Uri("http://example.com"))),
            entry = listOf(BundleEntry(resource = unknownResource)),
            signature = Signature(
                type = listOf(Coding(display = FHIRString("type"))),
                `when` = Instant("2017-01-01T00:00:00Z"),
                who = Reference(reference = FHIRString("who"))
            )
        )

        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bundle)

        val expectedJson = """
            {
              "resourceType" : "Bundle",
              "id" : "1234",
              "meta" : {
                "profile" : [ "RoninPractitioner" ]
              },
              "implicitRules" : "implicit-rules",
              "language" : "en-US",
              "identifier" : {
                "value" : "identifier"
              },
              "type" : "searchset",
              "timestamp" : "2017-01-01T00:00:00Z",
              "total" : 1,
              "link" : [ {
                "relation" : "next",
                "url" : "http://example.com"
              } ],
              "entry" : [ {
                "resource" : {
                  "resourceType" : "Banana",
                  "id" : "5678",
                  "key" : "value",
                  "key2" : {
                    "value" : "sub value"
                  }
                }
              } ],
              "signature" : {
                "type" : [ {
                  "display" : "type"
                } ],
                "when" : "2017-01-01T00:00:00Z",
                "who" : {
                  "reference" : "who"
                }
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedBundle = objectMapper.readValue<Bundle>(json)
        assertEquals(bundle, deserializedBundle)
    }
}

class BundleEntryTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val bundleEntry = BundleEntry(
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
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            link = listOf(BundleLink(relation = FHIRString("next"), url = Uri("http://www.example.com/next"))),
            resource = Patient(id = Id("1234")),
            search = BundleSearch(mode = SearchEntryMode.INCLUDE.asCode()),
            request = BundleRequest(method = HttpVerb.GET.asCode(), url = Uri("http://www.example.com/get")),
            response = BundleResponse(status = FHIRString("Ok"))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bundleEntry)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "modifierExtension" : [ {
            |    "url" : "http://localhost/modifier-extension",
            |    "valueString" : "Value"
            |  } ],
            |  "link" : [ {
            |    "relation" : "next",
            |    "url" : "http://www.example.com/next"
            |  } ],
            |  "resource" : {
            |    "resourceType" : "Patient",
            |    "id" : "1234"
            |  },
            |  "search" : {
            |    "mode" : "include"
            |  },
            |  "request" : {
            |    "method" : "GET",
            |    "url" : "http://www.example.com/get"
            |  },
            |  "response" : {
            |    "status" : "Ok"
            |  }
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedBundleEntry = objectMapper.readValue<BundleEntry>(json)
        assertEquals(bundleEntry, deserializedBundleEntry)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val bundleEntry = BundleEntry(search = BundleSearch(mode = SearchEntryMode.INCLUDE.asCode()))

        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bundleEntry)

        val expectedJson = """
            |{
            |  "search" : {
            |    "mode" : "include"
            |  }
            |}""".trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "id" : "1234"
            |}""".trimMargin()
        val bundleEntry = objectMapper.readValue<BundleEntry>(json)

        assertEquals(FHIRString("1234"), bundleEntry.id)
        assertEquals(listOf<Extension>(), bundleEntry.extension)
        assertEquals(listOf<Extension>(), bundleEntry.modifierExtension)
        assertEquals(listOf<BundleLink>(), bundleEntry.link)
        Assertions.assertNull(bundleEntry.resource)
        Assertions.assertNull(bundleEntry.search)
        Assertions.assertNull(bundleEntry.request)
        Assertions.assertNull(bundleEntry.response)
    }
}

class BundleLinkTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val bundleLink = BundleLink(
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
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            relation = FHIRString("next"),
            url = Uri("http://www.example.com/next")
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bundleLink)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "modifierExtension" : [ {
            |    "url" : "http://localhost/modifier-extension",
            |    "valueString" : "Value"
            |  } ],
            |  "relation" : "next",
            |  "url" : "http://www.example.com/next"
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedBundleLink = objectMapper.readValue<BundleLink>(json)
        assertEquals(bundleLink, deserializedBundleLink)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val bundleLink = BundleLink(relation = FHIRString("prev"), url = Uri("http://www.example.com/prev"))

        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bundleLink)

        val expectedJson = """
            |{
            |  "relation" : "prev",
            |  "url" : "http://www.example.com/prev"
            |}""".trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "relation" : "prev",
            |  "url" : "http://www.example.com/prev"
            |}""".trimMargin()
        val bundleLink = objectMapper.readValue<BundleLink>(json)

        Assertions.assertNull(bundleLink.id)
        assertEquals(listOf<Extension>(), bundleLink.extension)
        assertEquals(listOf<Extension>(), bundleLink.modifierExtension)
        assertEquals(FHIRString("prev"), bundleLink.relation)
        assertEquals(Uri("http://www.example.com/prev"), bundleLink.url)
    }
}

class BundleRequestTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val bundleRequest = BundleRequest(
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
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            method = HttpVerb.GET.asCode(),
            url = Uri("http://www.example.com/get"),
            ifNoneMatch = FHIRString("if none match"),
            ifModifiedSince = Instant("2017-01-01T00:00:00Z"),
            ifMatch = FHIRString("if match"),
            ifNoneExist = FHIRString("if none exist")
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bundleRequest)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "modifierExtension" : [ {
            |    "url" : "http://localhost/modifier-extension",
            |    "valueString" : "Value"
            |  } ],
            |  "method" : "GET",
            |  "url" : "http://www.example.com/get",
            |  "ifNoneMatch" : "if none match",
            |  "ifModifiedSince" : "2017-01-01T00:00:00Z",
            |  "ifMatch" : "if match",
            |  "ifNoneExist" : "if none exist"
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedBundleRequest = objectMapper.readValue<BundleRequest>(json)
        assertEquals(bundleRequest, deserializedBundleRequest)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val bundleRequest = BundleRequest(method = HttpVerb.POST.asCode(), url = Uri("http://www.example.com/post"))

        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bundleRequest)

        val expectedJson = """
            |{
            |  "method" : "POST",
            |  "url" : "http://www.example.com/post"
            |}""".trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "method" : "PUT",
            |  "url" : "http://www.example.com/put"
            |}""".trimMargin()
        val bundleRequest = objectMapper.readValue<BundleRequest>(json)

        Assertions.assertNull(bundleRequest.id)
        assertEquals(listOf<Extension>(), bundleRequest.extension)
        assertEquals(listOf<Extension>(), bundleRequest.modifierExtension)
        assertEquals(HttpVerb.PUT.asCode(), bundleRequest.method)
        assertEquals(Uri("http://www.example.com/put"), bundleRequest.url)
        Assertions.assertNull(bundleRequest.ifNoneMatch)
        Assertions.assertNull(bundleRequest.ifModifiedSince)
        Assertions.assertNull(bundleRequest.ifMatch)
        Assertions.assertNull(bundleRequest.ifNoneExist)
    }
}

class BundleResponseTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val bundleResponse = BundleResponse(
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
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            status = FHIRString("Ok"),
            location = Uri("http://www.example.com/location"),
            etag = FHIRString("etag"),
            lastModified = Instant("2015-02-07T13:28:17.239+02:00"),
            outcome = Patient(id = Id("67890"))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bundleResponse)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "modifierExtension" : [ {
            |    "url" : "http://localhost/modifier-extension",
            |    "valueString" : "Value"
            |  } ],
            |  "status" : "Ok",
            |  "location" : "http://www.example.com/location",
            |  "etag" : "etag",
            |  "lastModified" : "2015-02-07T13:28:17.239+02:00",
            |  "outcome" : {
            |    "resourceType" : "Patient",
            |    "id" : "67890"
            |  }
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedBundleResponse = objectMapper.readValue<BundleResponse>(json)
        assertEquals(bundleResponse, deserializedBundleResponse)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val bundleResponse = BundleResponse(status = FHIRString("status"))

        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bundleResponse)

        val expectedJson = """
            |{
            |  "status" : "status"
            |}""".trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "status" : "status"
            |}""".trimMargin()
        val bundleResponse = objectMapper.readValue<BundleResponse>(json)

        Assertions.assertNull(bundleResponse.id)
        assertEquals(listOf<Extension>(), bundleResponse.extension)
        assertEquals(listOf<Extension>(), bundleResponse.modifierExtension)
        assertEquals(FHIRString("status"), bundleResponse.status)
        Assertions.assertNull(bundleResponse.location)
        Assertions.assertNull(bundleResponse.etag)
        Assertions.assertNull(bundleResponse.lastModified)
        Assertions.assertNull(bundleResponse.outcome)
    }
}

class BundleSearchTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val bundleSearch = BundleSearch(
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
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            mode = SearchEntryMode.INCLUDE.asCode(),
            score = Decimal(1.4)
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bundleSearch)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "modifierExtension" : [ {
            |    "url" : "http://localhost/modifier-extension",
            |    "valueString" : "Value"
            |  } ],
            |  "mode" : "include",
            |  "score" : 1.4
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedBundleSearch = objectMapper.readValue<BundleSearch>(json)
        assertEquals(bundleSearch, deserializedBundleSearch)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val bundleSearch = BundleSearch(mode = SearchEntryMode.OUTCOME.asCode())

        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bundleSearch)

        val expectedJson = """
            |{
            |  "mode" : "outcome"
            |}""".trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "score" : 3.14
            |}""".trimMargin()
        val bundleSearch = objectMapper.readValue<BundleSearch>(json)

        Assertions.assertNull(bundleSearch.id)
        assertEquals(listOf<Extension>(), bundleSearch.extension)
        assertEquals(listOf<Extension>(), bundleSearch.modifierExtension)
        Assertions.assertNull(bundleSearch.mode)
        assertEquals(Decimal(3.14), bundleSearch.score)
    }
}
