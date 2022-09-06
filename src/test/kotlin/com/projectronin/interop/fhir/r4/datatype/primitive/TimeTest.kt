package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TimeTest {
    @Test
    fun `can serialize`() {
        val time = Time("10:00:25.1234")
        val json = objectMapper.writeValueAsString(time)
        assertEquals("\"10:00:25.1234\"", json)
    }

    @Test
    fun `can deserialize`() {
        val json = "\"10:00:25.1234\""
        val time = objectMapper.readValue<Time>(json)
        assertEquals(Time("10:00:25.1234"), time)
    }
}
