package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DateTest {
    @Test
    fun `can serialize`() {
        val date = Date("2021-11-16")
        val json = objectMapper.writeValueAsString(date)
        assertEquals("\"2021-11-16\"", json)
    }

    @Test
    fun `can deserialize`() {
        val json = "\"2021-11-16\""
        val date = objectMapper.readValue<Date>(json)
        assertEquals(Date("2021-11-16"), date)
    }
}
