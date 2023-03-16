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
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class QuantityTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val quantity = Quantity(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            value = Decimal(17.5),
            comparator = QuantityComparator.GREATER_OR_EQUAL_TO.asCode(),
            unit = FHIRString("years"),
            system = CodeSystem.UCUM.uri,
            code = Code("a")
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(quantity)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "value" : 17.5,
            |  "comparator" : ">=",
            |  "unit" : "years",
            |  "system" : "http://unitsofmeasure.org",
            |  "code" : "a"
            |}
        """.trimMargin()
        assertEquals(expectedJson, json)

        val deserializedQuantity = objectMapper.readValue<Quantity>(json)
        assertEquals(quantity, deserializedQuantity)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val quantity = Quantity(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            value = Decimal(17.5),
            system = CodeSystem.UCUM.uri,
            code = Code("mL/min/{1.73_m2}")
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(quantity)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "value" : 17.5,
            |  "system" : "http://unitsofmeasure.org",
            |  "code" : "mL/min/{1.73_m2}"
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
        val quantity = objectMapper.readValue<Quantity>(json)

        assertNull(quantity.id)
        assertEquals(listOf<Extension>(), quantity.extension)
        assertNull(quantity.value)
        assertEquals(QuantityComparator.GREATER_OR_EQUAL_TO.asCode(), quantity.comparator)
        assertNull(quantity.unit)
        assertNull(quantity.system)
        assertNull(quantity.code)
    }
}
