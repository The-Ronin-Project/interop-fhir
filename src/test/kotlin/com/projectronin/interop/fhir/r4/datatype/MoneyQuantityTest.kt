package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.QuantityComparator
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class MoneyQuantityTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val moneyQuantity = MoneyQuantity(
            id = "12345",
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            value = 17.5,
            comparator = QuantityComparator.GREATER_OR_EQUAL_TO.asCode(),
            system = CodeSystem.CURRENCY.uri,
            code = Code("USD")
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(moneyQuantity)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "value" : 17.5,
            |  "comparator" : ">=",
            |  "system" : "urn:iso:std:iso:4217",
            |  "code" : "USD"
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedMoneyQuantity = objectMapper.readValue<MoneyQuantity>(json)
        assertEquals(moneyQuantity, deserializedMoneyQuantity)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val moneyQuantity = MoneyQuantity(
            id = "12345",
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            value = 17.5,
            system = CodeSystem.CURRENCY.uri,
            code = Code("USD")
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(moneyQuantity)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "value" : 17.5,
            |  "system" : "urn:iso:std:iso:4217",
            |  "code" : "USD"
            |}""".trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "comparator" : ">="
            |}""".trimMargin()
        val distance = objectMapper.readValue<Distance>(json)

        assertNull(distance.id)
        assertEquals(listOf<Extension>(), distance.extension)
        assertNull(distance.value)
        assertEquals(QuantityComparator.GREATER_OR_EQUAL_TO.asCode(), distance.comparator)
        assertNull(distance.unit)
        assertNull(distance.system)
        assertNull(distance.code)
    }
}
