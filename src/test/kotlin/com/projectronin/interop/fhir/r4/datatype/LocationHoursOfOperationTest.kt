package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.primitive.Time
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.DayOfWeek
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class LocationHoursOfOperationTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val hoursOfOperation = LocationHoursOfOperation(
            id = "12345",
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            daysOfWeek = listOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
            allDay = true,
            openingTime = Time("06:30:00"),
            closingTime = Time("18:00:00")
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(hoursOfOperation)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "daysOfWeek" : [ "sat", "sun" ],
            |  "allDay" : true,
            |  "openingTime" : "06:30:00",
            |  "closingTime" : "18:00:00"
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedHoursOfOperation = objectMapper.readValue<LocationHoursOfOperation>(json)
        assertEquals(hoursOfOperation, deserializedHoursOfOperation)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val hoursOfOperation = LocationHoursOfOperation(id = "12345")
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(hoursOfOperation)

        val expectedJson = """
            |{
            |  "id" : "12345"
            |}""".trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "id" : "12345"
            |}""".trimMargin()
        val hoursOfOperation = objectMapper.readValue<LocationHoursOfOperation>(json)

        assertEquals("12345", hoursOfOperation.id)
        assertEquals(listOf<Extension>(), hoursOfOperation.extension)
        assertEquals(listOf<Extension>(), hoursOfOperation.modifierExtension)
        assertEquals(listOf<DayOfWeek>(), hoursOfOperation.daysOfWeek)
        assertNull(hoursOfOperation.openingTime)
        assertNull(hoursOfOperation.closingTime)
    }
}
