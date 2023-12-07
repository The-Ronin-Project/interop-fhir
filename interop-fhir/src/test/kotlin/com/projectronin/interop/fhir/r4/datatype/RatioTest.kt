package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class RatioTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val ratio =
            Ratio(
                id = FHIRString("12345"),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                numerator = Quantity(value = Decimal(3.0)),
                denominator = Quantity(value = Decimal(4.0)),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ratio)

        val expectedJson =
            """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "numerator" : {
            |    "value" : 3.0
            |  },
            |  "denominator" : {
            |    "value" : 4.0
            |  }
            |}
            """.trimMargin()
        assertEquals(expectedJson, json)

        val deserializedRatio = objectMapper.readValue<Ratio>(json)
        assertEquals(ratio, deserializedRatio)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val ratio = Ratio(numerator = Quantity(value = Decimal(3.0)), denominator = Quantity(value = Decimal(4.0)))
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ratio)

        val expectedJson =
            """
            |{
            |  "numerator" : {
            |    "value" : 3.0
            |  },
            |  "denominator" : {
            |    "value" : 4.0
            |  }
            |}
            """.trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            |{
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ]
            |}
            """.trimMargin()
        val ratio = objectMapper.readValue<Ratio>(json)

        assertNull(ratio.id)

        val expectedExtension =
            Extension(url = Uri("http://localhost/extension"), value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")))
        assertEquals(listOf(expectedExtension), ratio.extension)

        assertNull(ratio.numerator)
        assertNull(ratio.denominator)
    }
}
