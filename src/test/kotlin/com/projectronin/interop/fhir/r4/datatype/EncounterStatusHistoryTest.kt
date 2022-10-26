package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.EncounterStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class EncounterStatusHistoryTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val encounterStatusHistory = EncounterStatusHistory(
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
            status = EncounterStatus.PLANNED.asCode(),
            period = Period(
                start = DateTime(value = "2021-11-17T08:00:00Z"),
                end = DateTime(value = "2021-11-17T09:00:00Z")
            )
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounterStatusHistory)

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
              "status" : "planned",
              "period" : {
                "start" : "2021-11-17T08:00:00Z",
                "end" : "2021-11-17T09:00:00Z"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedEncounterStatusHistory = objectMapper.readValue<EncounterStatusHistory>(json)
        assertEquals(encounterStatusHistory, deserializedEncounterStatusHistory)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val encounterStatusHistory = EncounterStatusHistory(
            status = EncounterStatus.ARRIVED.asCode(),
            period = Period(
                start = DateTime(value = "2021-11-17T08:00:00Z"),
                end = DateTime(value = "2021-11-17T09:00:00Z")
            )
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(encounterStatusHistory)

        val expectedJson = """
            {
              "status" : "arrived",
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
              "status" : "in-progress",
              "period" : {
                "start" : "2021-11-17T08:00:00Z",
                "end" : "2021-11-17T09:00:00Z"
              }
            }
        """.trimIndent()
        val encounterStatusHistory = objectMapper.readValue<EncounterStatusHistory>(json)

        assertNull(encounterStatusHistory.id)
        assertEquals(listOf<Extension>(), encounterStatusHistory.extension)
        assertEquals(listOf<Extension>(), encounterStatusHistory.modifierExtension)
        assertEquals(EncounterStatus.IN_PROGRESS.asCode(), encounterStatusHistory.status)
        assertEquals(
            Period(
                start = DateTime(value = "2021-11-17T08:00:00Z"),
                end = DateTime(value = "2021-11-17T09:00:00Z")
            ),
            encounterStatusHistory.period
        )
    }
}
