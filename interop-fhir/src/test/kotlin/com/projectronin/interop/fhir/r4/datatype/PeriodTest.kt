package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class PeriodTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val period =
            Period(
                id = FHIRString("12345"),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                start = DateTime("1998-08"),
                end = DateTime("2002-05"),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(period)

        val expectedJson =
            """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "start" : "1998-08",
            |  "end" : "2002-05"
            |}
            """.trimMargin()
        assertEquals(expectedJson, json)

        val deserializedPeriod = objectMapper.readValue<Period>(json)
        assertEquals(period, deserializedPeriod)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val period =
            Period(
                start = DateTime("1998-08"),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(period)

        val expectedJson =
            """
            |{
            |  "start" : "1998-08"
            |}
            """.trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON`() {
        val json =
            """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "start" : "1998-08",
            |  "end" : "2002-05"
            |}
            """.trimMargin()
        val period = objectMapper.readValue<Period>(json)

        assertEquals(FHIRString("12345"), period.id)

        val expectedSubExtension =
            Extension(
                url = Uri("http://localhost/extension"),
                value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
            )
        assertEquals(listOf(expectedSubExtension), period.extension)

        assertEquals(DateTime("1998-08"), period.start)
        assertEquals(DateTime("2002-05"), period.end)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            |{
            |  "end" : "2002-05"
            |}
            """.trimMargin()
        val period = objectMapper.readValue<Period>(json)

        assertNull(period.id)
        assertEquals(listOf<Extension>(), period.extension)
        assertNull(period.start)
        assertEquals(DateTime("2002-05"), period.end)
    }
}
