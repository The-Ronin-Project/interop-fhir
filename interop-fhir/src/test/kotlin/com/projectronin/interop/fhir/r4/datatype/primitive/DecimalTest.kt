package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DecimalTest {
    @Test
    fun `can serialize`() {
        val decimal = Decimal(25.0)
        val json = objectMapper.writeValueAsString(decimal)
        assertEquals("25.0", json)
    }

    @Test
    fun `can deserialize`() {
        val json = "25.0"
        val decimal = objectMapper.readValue<Decimal>(json)
        assertEquals(Decimal(25.0), decimal)
    }

    @Test
    fun `can deserialize from int`() {
        val json = "25"
        val decimal = objectMapper.readValue<Decimal>(json)
        assertEquals(Decimal(25.0), decimal)
    }

    @Test
    fun `can deserialize from long`() {
        val json = "2147483648" // Int.MAX_VALUE + 1
        val decimal = objectMapper.readValue<Decimal>(json)
        assertEquals(Decimal(2147483648.0), decimal)
    }
}
