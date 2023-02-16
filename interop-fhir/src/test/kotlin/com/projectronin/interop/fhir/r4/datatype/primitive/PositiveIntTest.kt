package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PositiveIntTest {
    @Test
    fun `can serialize`() {
        val positiveInt = PositiveInt(100)
        val json = objectMapper.writeValueAsString(positiveInt)
        assertEquals("100", json)
    }

    @Test
    fun `can deserialize`() {
        val json = "100"
        val positiveInt = objectMapper.readValue<PositiveInt>(json)
        assertEquals(PositiveInt(100), positiveInt)
    }
}
