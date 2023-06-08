package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class DecimalTest {
    @Test
    fun `can serialize`() {
        val decimal = Decimal(BigDecimal("25.0"))
        val json = objectMapper.writeValueAsString(decimal)
        assertEquals("25.0", json)
    }

    @Test
    fun `can deserialize`() {
        val json = "25.0"
        val decimal = objectMapper.readValue<Decimal>(json)
        assertEquals(Decimal(BigDecimal("25.0"), null), decimal)
    }

    @Test
    fun `can construct from double`() {
        val decimal = Decimal(25.0)
        assertEquals(Decimal(25.0), decimal)
    }

    @Test
    fun `can construct from double type null`() {
        val doubleNull: Double? = null
        val decimal = Decimal(doubleNull)
        assertEquals(Decimal(doubleNull), decimal)
    }

    @Test
    fun `can construct from number type null`() {
        val numberNull: Number? = null
        val decimal = Decimal(numberNull)
        assertEquals(Decimal(numberNull), decimal)
    }

    @Test
    fun `can construct from int`() {
        val decimal = Decimal(25)
        assertEquals(Decimal(25), decimal)
    }

    @Test
    fun `can deserialize from int`() {
        val json = "25"
        val decimal = objectMapper.readValue<Decimal>(json)
        assertEquals(Decimal(BigDecimal(25.0)), decimal)
    }

    @Test
    fun `can deserialize from long`() {
        val json = "2147483648" // Int.MAX_VALUE + 1
        val decimal = objectMapper.readValue<Decimal>(json)
        assertEquals(Decimal(BigDecimal(2147483648.0)), decimal)
    }
}
