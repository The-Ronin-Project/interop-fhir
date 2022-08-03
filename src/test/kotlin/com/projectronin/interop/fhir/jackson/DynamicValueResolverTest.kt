package com.projectronin.interop.fhir.jackson

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.JsonNode
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DynamicValueResolverTest {
    private val jsonParser = mockk<JsonParser>(relaxed = true)
    private val resolver = DynamicValueResolver(jsonParser)

    @Test
    fun `non-nested value with unknown type`() {
        val node = mockk<JsonNode>()
        val exception = assertThrows<JsonParseException> {
            resolver.resolveDynamicValue(node, "Tuesday")
        }
        assertTrue(exception.message!!.startsWith("Unknown type for a dynamic value: Tuesday"))
    }

    @Test
    fun `non-nested value with known type`() {
        val node = mockk<JsonNode> {
            every { asInt() } returns 10
        }
        val dynamicValue = resolver.resolveDynamicValue(node, "Integer")
        assertEquals(DynamicValue(DynamicValueType.INTEGER, 10), dynamicValue)
    }

    @Test
    fun `nested value with no elements`() {
        val node = mockk<JsonNode> {
            every { fieldNames() } returns emptyList<String>().iterator()
        }
        val exception = assertThrows<JsonParseException> {
            resolver.resolveDynamicValue(node, "")
        }
        assertTrue(exception.message!!.startsWith("Encountered a nested dynamic value with an invalid number (0) of elements"))
    }

    @Test
    fun `nested value with multiple elements`() {
        val node = mockk<JsonNode> {
            every { fieldNames() } returns listOf("integer", "boolean").iterator()
        }
        val exception = assertThrows<JsonParseException> {
            resolver.resolveDynamicValue(node, "")
        }
        assertTrue(exception.message!!.startsWith("Encountered a nested dynamic value with an invalid number (2) of elements"))
    }

    @Test
    fun `nested value with unknown type`() {
        val node = mockk<JsonNode> {
            every { fieldNames() } returns iteratorFor("wednesday")
        }
        val exception = assertThrows<JsonParseException> {
            resolver.resolveDynamicValue(node, "")
        }
        assertTrue(exception.message!!.startsWith("Unknown type for a dynamic value: Wednesday"))
    }

    @Test
    fun `nested value with known type`() {
        val node = mockk<JsonNode> {
            every { fieldNames() } returns iteratorFor("boolean")
            // get is an inherited method in Kotlin, so need to indicate we're trying to the mockk.
            every { this@mockk.get("boolean") } returns mockk {
                every { asBoolean() } returns true
            }
        }
        val dynamicValue = resolver.resolveDynamicValue(node, "")
        assertEquals(DynamicValue(DynamicValueType.BOOLEAN, true), dynamicValue)
    }

    // Uplift to mockk 1.12.5 did not like our use of listOf("item").iterator(). This is a workaround that it has no issues with. Unsure if this is a Java version issue or something deeper in mockk.
    private fun iteratorFor(vararg value: String): Iterator<String> {
        val list = mutableListOf<String>()
        value.forEach { list.add(it) }
        return list.iterator()
    }
}
