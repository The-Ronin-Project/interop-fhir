package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FHIRBooleanTest {
    @Test
    fun `can serialize`() {
        val boolean = FHIRBoolean(true)
        val json = objectMapper.writeValueAsString(boolean)
        assertEquals("true", json)
    }

    @Test
    fun `can deserialize`() {
        val json = "false"
        val boolean = objectMapper.readValue<FHIRBoolean>(json)
        assertEquals(FHIRBoolean(false), boolean)
    }

    @Test
    fun `supports Boolean extension`() {
        assertEquals(FHIRBoolean(true), true.asFHIR())
    }

    @Test
    fun `supports TRUE static`() {
        assertEquals(FHIRBoolean(true), FHIRBoolean.TRUE)
    }

    @Test
    fun `supports FALSE static`() {
        assertEquals(FHIRBoolean(false), FHIRBoolean.FALSE)
    }
}
