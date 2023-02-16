package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UnsignedIntTest {
    @Test
    fun `can serialize`() {
        val unsignedInt = UnsignedInt(1)
        val json = objectMapper.writeValueAsString(unsignedInt)
        assertEquals("1", json)
    }

    @Test
    fun `can deserialize`() {
        val json = "1"
        val unsignedInt = objectMapper.readValue<UnsignedInt>(json)
        assertEquals(UnsignedInt(1), unsignedInt)
    }
}
