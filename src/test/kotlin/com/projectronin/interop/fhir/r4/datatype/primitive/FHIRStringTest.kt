package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FHIRStringTest {
    @Test
    fun `can serialize`() {
        val string = FHIRString("some value")
        val json = objectMapper.writeValueAsString(string)
        assertEquals("\"some value\"", json)
    }

    @Test
    fun `can deserialize`() {
        val json = "\"some value\""
        val string = objectMapper.readValue<FHIRString>(json)
        assertEquals(FHIRString("some value"), string)
    }

    @Test
    fun `supports String extension`() {
        assertEquals(FHIRString("value"), "value".asFHIR())
    }

    @Test
    fun `supports List extension`() {
        assertEquals(listOf(FHIRString("value"), FHIRString("value2")), listOf("value", "value2").asFHIR())
    }
}
