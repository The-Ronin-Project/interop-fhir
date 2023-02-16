package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FHIRIntegerTest {
    @Test
    fun `can serialize`() {
        val integer = FHIRInteger(10)
        val json = objectMapper.writeValueAsString(integer)
        assertEquals("10", json)
    }

    @Test
    fun `can deserialize`() {
        val json = "10"
        val integer = objectMapper.readValue<FHIRInteger>(json)
        assertEquals(FHIRInteger(10), integer)
    }

    @Test
    fun `supports Int extension`() {
        assertEquals(FHIRInteger(123), 123.asFHIR())
    }
}
