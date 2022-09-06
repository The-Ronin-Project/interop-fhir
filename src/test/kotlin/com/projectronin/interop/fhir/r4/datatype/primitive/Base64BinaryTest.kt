package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Base64BinaryTest {
    @Test
    fun `can serialize`() {
        val base64Binary = Base64Binary("abcdefgh")
        val json = objectMapper.writeValueAsString(base64Binary)
        assertEquals("\"abcdefgh\"", json)
    }

    @Test
    fun `can deserialize`() {
        val json = "\"abcdefgh\""
        val base64Binary = objectMapper.readValue<Base64Binary>(json)
        assertEquals(Base64Binary("abcdefgh"), base64Binary)
    }
}
