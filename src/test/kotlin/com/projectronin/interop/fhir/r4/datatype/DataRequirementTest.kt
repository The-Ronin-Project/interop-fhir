package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.SortDirection
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class DataRequirementTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val dataRequirement = DataRequirement(
            id = "12345",
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            type = Code("data-type"),
            profile = listOf(Canonical("data-profile")),
            subject = DynamicValue(DynamicValueType.REFERENCE, Reference(display = "subject-reference")),
            mustSupport = listOf("item 1"),
            codeFilter = listOf(
                CodeFilter(
                    id = "12345",
                    extension = listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, "Value")
                        )
                    ),
                    searchParam = "search param",
                    valueSet = Canonical("code-value-set"),
                    code = listOf(Coding(userSelected = false))
                )
            ),
            dateFilter = listOf(
                DateFilter(
                    id = "12345",
                    extension = listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, "Value")
                        )
                    ),
                    path = "date-filter-path",
                    value = DynamicValue(DynamicValueType.DATE_TIME, DateTime("2021-10-31"))
                )
            ),
            limit = PositiveInt(5),
            sort = listOf(
                Sort(
                    id = "12345",
                    extension = listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, "Value")
                        )
                    ),
                    path = "sort-path",
                    direction = SortDirection.ASCENDING.asCode()
                )
            )
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataRequirement)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "type" : "data-type",
            |  "profile" : [ "data-profile" ],
            |  "subjectReference" : {
            |    "display" : "subject-reference"
            |  },
            |  "mustSupport" : [ "item 1" ],
            |  "codeFilter" : [ {
            |    "id" : "12345",
            |    "extension" : [ {
            |      "url" : "http://localhost/extension",
            |      "valueString" : "Value"
            |    } ],
            |    "searchParam" : "search param",
            |    "valueSet" : "code-value-set",
            |    "code" : [ {
            |      "userSelected" : false
            |    } ]
            |  } ],
            |  "dateFilter" : [ {
            |    "id" : "12345",
            |    "extension" : [ {
            |      "url" : "http://localhost/extension",
            |      "valueString" : "Value"
            |    } ],
            |    "path" : "date-filter-path",
            |    "valueDateTime" : "2021-10-31"
            |  } ],
            |  "limit" : 5,
            |  "sort" : [ {
            |    "id" : "12345",
            |    "extension" : [ {
            |      "url" : "http://localhost/extension",
            |      "valueString" : "Value"
            |    } ],
            |    "path" : "sort-path",
            |    "direction" : "ascending"
            |  } ]
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedDataRequirement = objectMapper.readValue<DataRequirement>(json)
        assertEquals(dataRequirement, deserializedDataRequirement)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val dataRequirement = DataRequirement(
            type = Code("type")
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataRequirement)

        val expectedJson = """
            |{
            |  "type" : "type"
            |}""".trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "type" : "type"
            |}""".trimMargin()
        val dataRequirement = objectMapper.readValue<DataRequirement>(json)

        assertNull(dataRequirement.id)
        assertEquals(listOf<Extension>(), dataRequirement.extension)
        assertEquals(Code("type"), dataRequirement.type)
        assertEquals(listOf<Canonical>(), dataRequirement.profile)
        assertNull(dataRequirement.subject)
        assertEquals(listOf<String>(), dataRequirement.mustSupport)
        assertEquals(listOf<CodeFilter>(), dataRequirement.codeFilter)
        assertEquals(listOf<DateFilter>(), dataRequirement.dateFilter)
        assertNull(dataRequirement.id)
        assertEquals(listOf<Sort>(), dataRequirement.sort)
    }

    @Test
    fun `can serialize and deserialize with list of primitive extensions`() {
        val extension = Extension(
            url = Uri("http://localhost/extension"),
            value = DynamicValue(DynamicValueType.STRING, "Value")
        )
        val canonical1 = Canonical("data-profile1")
        val canonical2 = Canonical("data-profile2", "12345", listOf(extension))
        val canonical3 = Canonical("data-profile3", null, listOf(extension))
        val canonical4 = Canonical("data-profile4", "67890", listOf())
        val dataRequirement = DataRequirement(
            type = Code("type"),
            profile = listOf(canonical1, canonical2, canonical3, canonical4)
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataRequirement)

        val expectedJson = """
            |{
            |  "type" : "type",
            |  "profile" : [ "data-profile1", "data-profile2", "data-profile3", "data-profile4" ],
            |  "_profile" : [ null, {
            |    "id" : "12345",
            |    "extension" : [ {
            |      "url" : "http://localhost/extension",
            |      "valueString" : "Value"
            |    } ]
            |  }, {
            |    "extension" : [ {
            |      "url" : "http://localhost/extension",
            |      "valueString" : "Value"
            |    } ]
            |  }, {
            |    "id" : "67890"
            |  } ]
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedDataRequirement = objectMapper.readValue<DataRequirement>(json)
        assertEquals(dataRequirement, deserializedDataRequirement)
    }
    @Test
    fun `can serialize and deserialize with list of primitive extensions with null values`() {
        val extension = Extension(
            url = Uri("http://localhost/extension"),
            value = DynamicValue(DynamicValueType.STRING, "Value")
        )
        val canonical1 = Canonical("data-profile1")
        val canonical2 = Canonical(null, "12345", listOf(extension))
        val canonical3 = Canonical("data-profile3", null, listOf(extension))
        val canonical4 = Canonical("data-profile4", "67890", listOf())
        val dataRequirement = DataRequirement(
            type = Code("type"),
            profile = listOf(canonical1, canonical2, canonical3, canonical4)
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataRequirement)

        val expectedJson = """
            |{
            |  "type" : "type",
            |  "profile" : [ "data-profile1", null, "data-profile3", "data-profile4" ],
            |  "_profile" : [ null, {
            |    "id" : "12345",
            |    "extension" : [ {
            |      "url" : "http://localhost/extension",
            |      "valueString" : "Value"
            |    } ]
            |  }, {
            |    "extension" : [ {
            |      "url" : "http://localhost/extension",
            |      "valueString" : "Value"
            |    } ]
            |  }, {
            |    "id" : "67890"
            |  } ]
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedDataRequirement = objectMapper.readValue<DataRequirement>(json)
        assertEquals(dataRequirement, deserializedDataRequirement)
    }
}
