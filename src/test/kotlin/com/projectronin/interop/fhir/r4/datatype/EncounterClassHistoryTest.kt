package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class EncounterClassHistoryTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val encounterClassHistory = EncounterClassHistory(
            id = "12345",
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            modifierExtension = listOf(
                Extension(
                    url = Uri("http://localhost/modifier-extension"),
                    value = DynamicValue(DynamicValueType.INTEGER, 1)
                )
            ),
            `class` = Code("PRENC"),
            period = Period(
                start = DateTime(value = "2021-11-17T08:00:00Z"),
                end = DateTime(value = "2021-11-17T09:00:00Z")
            )
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounterClassHistory)

        val expectedJson = """
            {
              "id" : "12345",
              "extension" : [ {
                "url" : "http://localhost/extension",
                "valueString" : "Value"
              } ],
              "modifierExtension" : [ {
                "url" : "http://localhost/modifier-extension",
                "valueInteger" : 1
              } ],
              "class" : "PRENC",
              "period" : {
                "start" : "2021-11-17T08:00:00Z",
                "end" : "2021-11-17T09:00:00Z"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedEncounterClassHistory = objectMapper.readValue<EncounterClassHistory>(json)
        assertEquals(encounterClassHistory, deserializedEncounterClassHistory)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val encounterClassHistory = EncounterClassHistory(
            `class` = Code("PRENC"),
            period = Period(
                start = DateTime(value = "2021-11-17T08:00:00Z"),
                end = DateTime(value = "2021-11-17T09:00:00Z")
            )
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounterClassHistory)

        val expectedJson = """
            {
              "class" : "PRENC",
              "period" : {
                "start" : "2021-11-17T08:00:00Z",
                "end" : "2021-11-17T09:00:00Z"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "class" : "PRENC",
              "period" : {
                "start" : "2021-11-17T08:00:00Z",
                "end" : "2021-11-17T09:00:00Z"
              }
            }
        """.trimIndent()
        val encounterClassHistory = objectMapper.readValue<EncounterClassHistory>(json)

        assertNull(encounterClassHistory.id)
        assertEquals(listOf<Extension>(), encounterClassHistory.extension)
        assertEquals(listOf<Extension>(), encounterClassHistory.modifierExtension)
        assertEquals(Code("PRENC"), encounterClassHistory.`class`)
        assertEquals(
            Period(
                start = DateTime(value = "2021-11-17T08:00:00Z"),
                end = DateTime(value = "2021-11-17T09:00:00Z")
            ),
            encounterClassHistory.period
        )
    }
}
