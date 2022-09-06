package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UuidTest {
    @Test
    fun `can serialize`() {
        val uuid = Uuid("urn:uuid:12345678-90ab-cdef-1234-567890abcdef")
        val json = objectMapper.writeValueAsString(uuid)
        assertEquals("\"urn:uuid:12345678-90ab-cdef-1234-567890abcdef\"", json)
    }

    @Test
    fun `can deserialize`() {
        val json = "\"urn:uuid:12345678-90ab-cdef-1234-567890abcdef\""
        val uuid = objectMapper.readValue<Uuid>(json)
        assertEquals(Uuid("urn:uuid:12345678-90ab-cdef-1234-567890abcdef"), uuid)
    }
}
