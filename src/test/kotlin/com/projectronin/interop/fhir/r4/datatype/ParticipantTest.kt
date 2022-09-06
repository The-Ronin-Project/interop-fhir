package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.ParticipantRequired
import com.projectronin.interop.fhir.r4.valueset.ParticipationStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ParticipantTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val participant = Participant(
            id = "67890",
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            modifierExtension = listOf(
                Extension(
                    url = Uri("http://localhost/modifier-extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            type = listOf(CodeableConcept(text = "abc")),
            actor = Reference(display = "actor"),
            required = ParticipantRequired.REQUIRED.asCode(),
            status = ParticipationStatus.ACCEPTED.asCode(),
            period = Period(
                start = DateTime("1998-08"),
                end = DateTime("2002-05")
            )
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(participant)

        val expectedJson = """
            {
              "id" : "67890",
              "extension" : [ {
                "url" : "http://localhost/extension",
                "valueString" : "Value"
              } ],
              "modifierExtension" : [ {
                "url" : "http://localhost/modifier-extension",
                "valueString" : "Value"
              } ],
              "type" : [ {
                "text" : "abc"
              } ],
              "actor" : {
                "display" : "actor"
              },
              "required" : "required",
              "status" : "accepted",
              "period" : {
                "start" : "1998-08",
                "end" : "2002-05"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedParticipant = JacksonManager.objectMapper.readValue<Participant>(json)
        assertEquals(participant, deserializedParticipant)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val participant = Participant(
            actor = Reference(display = "actor"),
            status = ParticipationStatus.ACCEPTED.asCode()
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(participant)

        val expectedJson = """
            {
              "actor" : {
                "display" : "actor"
              },
              "status" : "accepted"
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedParticipant = JacksonManager.objectMapper.readValue<Participant>(json)
        assertEquals(participant, deserializedParticipant)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
             "type" : [ {
                "text" : "abc"
             } ],
             "status" : "accepted"
            }
        """.trimIndent()
        val participant = JacksonManager.objectMapper.readValue<Participant>(json)

        assertNull(participant.id)
        assertEquals(listOf<Extension>(), participant.extension)
        assertEquals(listOf<Extension>(), participant.modifierExtension)
        assertNull(participant.actor)
        assertNull(participant.required)
        assertNull(participant.period)
    }
}
