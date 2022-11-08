package com.projectronin.interop.fhir.r4.datatype.medication

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class SubstitutionTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val substitution = Substitution(
            id = "12345",
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            allowed = DynamicValue(DynamicValueType.BOOLEAN, true),
            reason = CodeableConcept(text = "reason")
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(substitution)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "allowedBoolean" : true,
            |  "reason" : {
            |    "text" : "reason"
            |  }
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedSubstitution = objectMapper.readValue<Substitution>(json)
        assertEquals(substitution, deserializedSubstitution)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val substitution = Substitution(
            allowed = DynamicValue(
                DynamicValueType.CODEABLE_CONCEPT,
                CodeableConcept(
                    text = "allowed"
                )
            )
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(substitution)

        val expectedJson = """
            |{
            |  "allowedCodeableConcept" : {
            |    "text" : "allowed"
            |  }
            |}""".trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "allowedBoolean" : true
            |}""".trimMargin()
        val substitution = objectMapper.readValue<Substitution>(json)

        assertNull(substitution.id)
        assertEquals(listOf<Extension>(), substitution.extension)
        assertEquals(DynamicValue(DynamicValueType.BOOLEAN, true), substitution.allowed)
        assertNull(substitution.reason)
    }
}
