package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.QuantityComparator
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CountTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val count = Count(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            value = Decimal(17.0),
            comparator = QuantityComparator.GREATER_OR_EQUAL_TO.asCode(),
            unit = FHIRString("1"),
            system = CodeSystem.UCUM.uri,
            code = Code("1")
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(count)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "value" : 17.0,
            |  "comparator" : ">=",
            |  "unit" : "1",
            |  "system" : "http://unitsofmeasure.org",
            |  "code" : "1"
            |}
        """.trimMargin()
        assertEquals(expectedJson, json)

        val deserializedCount = objectMapper.readValue<Count>(json)
        assertEquals(count, deserializedCount)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val count = Count(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            value = Decimal(17.0),
            system = CodeSystem.UCUM.uri,
            code = Code("1")
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(count)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "value" : 17.0,
            |  "system" : "http://unitsofmeasure.org",
            |  "code" : "1"
            |}
        """.trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "comparator" : ">="
            |}
        """.trimMargin()
        val count = objectMapper.readValue<Count>(json)

        Assertions.assertNull(count.id)
        assertEquals(listOf<Extension>(), count.extension)
        Assertions.assertNull(count.value)
        assertEquals(QuantityComparator.GREATER_OR_EQUAL_TO.asCode(), count.comparator)
        Assertions.assertNull(count.unit)
        Assertions.assertNull(count.system)
        Assertions.assertNull(count.code)
    }
}
