package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OidTest {
    @Test
    fun `can serialize`() {
        val oid = Oid("urn:oid:1.2.3.4.5")
        val json = objectMapper.writeValueAsString(oid)
        assertEquals("\"urn:oid:1.2.3.4.5\"", json)
    }

    @Test
    fun `can deserialize`() {
        val json = "\"urn:oid:1.2.3.4.5\""
        val oid = objectMapper.readValue<Oid>(json)
        assertEquals(Oid("urn:oid:1.2.3.4.5"), oid)
    }
}
