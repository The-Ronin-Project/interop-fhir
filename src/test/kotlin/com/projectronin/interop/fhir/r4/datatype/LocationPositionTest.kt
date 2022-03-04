package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class LocationPositionTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val position = LocationPosition(
            id = "12345",
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            longitude = Decimal(13.81531),
            latitude = Decimal(66.077132),
            altitude = Decimal(17.0)
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(position)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "longitude" : 13.81531,
            |  "latitude" : 66.077132,
            |  "altitude" : 17.0
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedPosition = objectMapper.readValue<LocationPosition>(json)
        assertEquals(position, deserializedPosition)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val position = LocationPosition(
            longitude = Decimal(13.81531),
            latitude = Decimal(66.077132)
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(position)

        val expectedJson = """
            |{
            |  "longitude" : 13.81531,
            |  "latitude" : 66.077132
            |}""".trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "longitude" : 13.81531,
            |  "latitude" : 66.077132
            |}""".trimMargin()
        val position = objectMapper.readValue<LocationPosition>(json)

        assertNull(position.id)
        assertEquals(listOf<Extension>(), position.extension)
        assertNull(position.altitude)
    }
}
