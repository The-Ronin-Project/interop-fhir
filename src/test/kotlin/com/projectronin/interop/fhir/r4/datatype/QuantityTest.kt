package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.QuantityComparator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class QuantityTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val quantity = Quantity(
            id = "12345",
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            value = 17.5,
            comparator = QuantityComparator.GREATER_OR_EQUAL_TO,
            unit = "years",
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
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedQuantity = objectMapper.readValue<Quantity>(json)
        assertEquals(quantity, deserializedQuantity)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val quantity = Quantity(
            id = "12345",
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            value = 17.5,
            system = CodeSystem.UCUM.uri,
            code = Code("mL/min/{1.73_m2}"),
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
            |}""".trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "comparator" : ">="
            |}""".trimMargin()
        val quantity = objectMapper.readValue<Quantity>(json)

        assertNull(quantity.id)
        assertEquals(listOf<Extension>(), quantity.extension)
        assertNull(quantity.value)
        assertEquals(QuantityComparator.GREATER_OR_EQUAL_TO, quantity.comparator)
        assertNull(quantity.unit)
        assertNull(quantity.system)
        assertNull(quantity.code)
    }

    @Test
    fun `fails if code is provided without system`() {
        val exception = assertThrows<IllegalArgumentException> {
            Quantity(
                value = 60.0,
                unit = "mL/min/1.73m2",
                code = Code("mL/min/{1.73_m2}"),
            )
        }
        assertEquals(
            "If a code for the unit is present, the system SHALL also be present",
            exception.message
        )
    }
}
