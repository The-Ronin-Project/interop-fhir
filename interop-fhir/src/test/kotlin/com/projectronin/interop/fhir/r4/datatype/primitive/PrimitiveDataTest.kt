package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PrimitiveDataTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val primitiveData = PrimitiveData(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            )
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(primitiveData)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ]
            |}
        """.trimMargin()
        assertEquals(expectedJson, json)

        val deserializedPrimitiveData = objectMapper.readValue<PrimitiveData>(json)
        assertEquals(primitiveData, deserializedPrimitiveData)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val primitiveData = PrimitiveData(
            extension = listOf(
                Extension(
                    url = Uri("http://hl7.org/fhir/StructureDefinition/rendered-value"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("xxx-xx-1234"))
                )
            )
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(primitiveData)

        val expectedJson = """
            |{
            |  "extension" : [ {
            |    "url" : "http://hl7.org/fhir/StructureDefinition/rendered-value",
            |    "valueString" : "xxx-xx-1234"
            |  } ]
            |}
        """.trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "id" : "12345"
            |}
        """.trimMargin()
        val primitiveData = objectMapper.readValue<PrimitiveData>(json)

        assertEquals(FHIRString("12345"), primitiveData.id)
        assertEquals(listOf<Extension>(), primitiveData.extension)
    }
}
