package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class InstantTest {
    @Test
    fun `can serialize`() {
        val instant = Instant("2017-01-01T00:00:00.123+05:00")
        val json = objectMapper.writeValueAsString(instant)
        assertEquals("\"2017-01-01T00:00:00.123+05:00\"", json)
    }

    @Test
    fun `can deserialize`() {
        val json = "\"2017-01-01T00:00:00.123+05:00\""
        val instant = objectMapper.readValue<Instant>(json)
        assertEquals(Instant("2017-01-01T00:00:00.123+05:00"), instant)
    }
}
