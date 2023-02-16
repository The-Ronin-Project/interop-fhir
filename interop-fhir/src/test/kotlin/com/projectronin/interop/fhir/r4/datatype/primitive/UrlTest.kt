package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UrlTest {
    @Test
    fun `can serialize`() {
        val url = Url("http://localhost")
        val json = objectMapper.writeValueAsString(url)
        assertEquals("\"http://localhost\"", json)
    }

    @Test
    fun `can deserialize`() {
        val json = "\"http://localhost\""
        val url = objectMapper.readValue<Url>(json)
        assertEquals(Url("http://localhost"), url)
    }
}
