package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.enums.CodedEnum
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class CodeTest {
    @Test
    fun `can serialize`() {
        val code = Code("Some Value")
        val json = objectMapper.writeValueAsString(code)
        assertEquals("\"Some Value\"", json)
    }

    @Test
    fun `can deserialize`() {
        val json = "\"Some Value\""
        val code = objectMapper.readValue<Code>(json)
        assertEquals(Code("Some Value"), code)
    }

    @Test
    fun `can use enum`() {
        val test = Code("joever").asEnum<Fake>()
        assertEquals(Fake.JOEVER, test)
    }

    @Test
    fun `can't use enum`() {
        val test = Code("back").asEnum<Fake>()
        assertNull(test)
    }

    enum class Fake(@JsonValue override val code: String) :
        CodedEnum<Fake> {
        JOEVER("joever")
    }
}
