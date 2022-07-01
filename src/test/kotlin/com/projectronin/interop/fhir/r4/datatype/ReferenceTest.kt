package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ReferenceTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val reference = Reference(
            id = "12345",
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            reference = "Patient/123",
            type = Uri("Patient"),
            identifier = Identifier(value = "123"),
            display = "Patient 123"
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(reference)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "reference" : "Patient/123",
            |  "type" : "Patient",
            |  "identifier" : {
            |    "value" : "123"
            |  },
            |  "display" : "Patient 123"
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedReference = objectMapper.readValue<Reference>(json)
        assertEquals(reference, deserializedReference)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val reference = Reference(type = Uri("Patient"), display = "any")
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(reference)

        val expectedJson = """
            |{
            |  "type" : "Patient",
            |  "display" : "any"
            |}""".trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "reference" : "Patient/123"
            |}""".trimMargin()
        val reference = objectMapper.readValue<Reference>(json)

        assertNull(reference.id)
        assertEquals(listOf<Extension>(), reference.extension)
        assertEquals("Patient/123", reference.reference)
        assertNull(reference.type)
        assertNull(reference.identifier)
        assertNull(reference.display)
    }

    @Test
    fun `At least one of reference, identifier and display SHALL be present (unless an extension)`() {
        val exception = assertThrows<IllegalArgumentException> {
            Reference()
        }
        assertEquals(
            "At least one of reference, identifier and display SHALL be present (unless an extension is provided)",
            exception.message
        )
    }
}
