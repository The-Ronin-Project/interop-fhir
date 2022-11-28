package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ConditionEvidenceTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val conditionEvidence = ConditionEvidence(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            modifierExtension = listOf(
                Extension(
                    url = Uri("http://localhost/modifier-extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            code = listOf(CodeableConcept(text = FHIRString("code"))),
            detail = listOf(Reference(display = FHIRString("detail")))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(conditionEvidence)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "modifierExtension" : [ {
            |    "url" : "http://localhost/modifier-extension",
            |    "valueString" : "Value"
            |  } ],
            |  "code" : [ {
            |    "text" : "code"
            |  } ],
            |  "detail" : [ {
            |    "display" : "detail"
            |  } ]
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedConditionEvidence = objectMapper.readValue<ConditionEvidence>(json)
        assertEquals(conditionEvidence, deserializedConditionEvidence)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val conditionEvidence = ConditionEvidence(
            code = listOf(CodeableConcept(text = FHIRString("code")))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(conditionEvidence)

        val expectedJson = """
            |{
            |  "code" : [ {
            |    "text" : "code"
            |  } ]
            |}""".trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "detail" : [ {
            |    "display" : "detail"
            |  } ]
            |}""".trimMargin()
        val conditionEvidence = objectMapper.readValue<ConditionEvidence>(json)

        assertNull(conditionEvidence.id)
        assertEquals(listOf<Extension>(), conditionEvidence.extension)
        assertEquals(listOf<Extension>(), conditionEvidence.modifierExtension)
        assertEquals(listOf<CodeableConcept>(), conditionEvidence.code)
        assertEquals(listOf(Reference(display = FHIRString("detail"))), conditionEvidence.detail)
    }
}
