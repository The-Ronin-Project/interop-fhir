package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DateTimeTest {
    @Test
    fun `can serialize`() {
        val dateTime = DateTime("2021-11-16T20:30:40+02:00")
        val json = objectMapper.writeValueAsString(dateTime)
        assertEquals("\"2021-11-16T20:30:40+02:00\"", json)
    }

    @Test
    fun `can deserialize`() {
        val json = "\"2021-11-16T20:30:40+02:00\""
        val dateTime = objectMapper.readValue<DateTime>(json)
        assertEquals(DateTime("2021-11-16T20:30:40+02:00"), dateTime)
    }
}
