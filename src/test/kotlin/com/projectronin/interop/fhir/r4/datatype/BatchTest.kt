package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class BatchTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val batch = Batch(
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
            lotNumber = FHIRString("123ABC"),
            expirationDate = DateTime("2022-08-31")
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(batch)

        val expectedJson = """
            {
              "id" : "12345",
              "extension" : [ {
                "url" : "http://localhost/extension",
                "valueString" : "Value"
              } ],
              "modifierExtension" : [ {
                "url" : "http://localhost/modifier-extension",
                "valueString" : "Value"
              } ],
              "lotNumber" : "123ABC",
              "expirationDate" : "2022-08-31"
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedBatch = JacksonManager.objectMapper.readValue<Batch>(json)
        assertEquals(batch, deserializedBatch)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val batch = Batch()
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(batch)

        val expectedJson = "{ }"
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = "{ }"
        val batch = JacksonManager.objectMapper.readValue<Batch>(json)

        assertNull(batch.id)
        assertEquals(listOf<Extension>(), batch.extension)
        assertEquals(listOf<Extension>(), batch.modifierExtension)
        assertNull(batch.lotNumber)
        assertNull(batch.expirationDate)
    }
}
