package com.projectronin.interop.fhir.stu3.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Participant
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.ContainedResource
import com.projectronin.interop.fhir.r4.valueset.AppointmentStatus
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import com.projectronin.interop.fhir.r4.valueset.ParticipationStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class STU3AppointmentTest {
    private val goodSTU3Appointment = STU3Appointment(
        id = Id("12345"),
        meta = Meta(
            profile = listOf(Canonical("http://projectronin.com/fhir/us/ronin/StructureDefinition/oncology-practitioner")),
        ),
        implicitRules = Uri("implicit-rules"),
        language = Code("en-US"),
        text = Narrative(
            status = NarrativeStatus.GENERATED.asCode(),
            div = "div"
        ),
        contained = listOf(ContainedResource("""{"resourceType":"Banana","field":"24680"}""")),
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
        identifier = listOf(Identifier(value = "id")),
        status = AppointmentStatus.CANCELLED.asCode(),
        serviceCategory = CodeableConcept(text = "service category"),
        serviceType = listOf(CodeableConcept(text = "service type")),
        specialty = listOf(CodeableConcept(text = "specialty")),
        appointmentType = CodeableConcept(text = "appointment type"),
        reason = listOf(CodeableConcept(text = "reason code")),
        indication = listOf(Reference(display = "reason reference")),
        priority = 1,
        description = "appointment test",
        supportingInformation = listOf(Reference(display = "supporting info")),
        start = Instant(value = "2017-01-01T00:00:00Z"),
        end = Instant(value = "2017-01-01T01:00:00Z"),
        minutesDuration = 15,
        slot = listOf(Reference(display = "slot")),
        created = DateTime(value = "2021-11-16"),
        comment = "comment",
        incomingReferral = listOf(Reference(display = "based on")),
        participant = listOf(
            Participant(
                actor = Reference(display = "actor"),
                status = ParticipationStatus.ACCEPTED.asCode()
            )
        ),
        requestedPeriod = listOf(Period(start = DateTime(value = "2021-11-16")))
    )

    @Test
    fun `can serialize and deserialize JSON`() {
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(goodSTU3Appointment)

        val expectedJson = """
            {
              "resourceType" : "Appointment",
              "id" : "12345",
              "meta" : {
                "profile" : [ "http://projectronin.com/fhir/us/ronin/StructureDefinition/oncology-practitioner" ]
              },
              "implicitRules" : "implicit-rules",
              "language" : "en-US",
              "text" : {
                "status" : "generated",
                "div" : "div"
              },
              "contained" : [ {"resourceType":"Banana","field":"24680"} ],
              "extension" : [ {
                "url" : "http://localhost/extension",
                "valueString" : "Value"
              } ],
              "modifierExtension" : [ {
                "url" : "http://localhost/modifier-extension",
                "valueString" : "Value"
              } ],
              "identifier" : [ {
                "value" : "id"
              } ],
              "reason" : [ {
                "text" : "reason code"
              } ],
              "indication" : [ {
                "display" : "reason reference"
              } ],
              "incomingReferral" : [ {
                "display" : "based on"
              } ],
              "status" : "cancelled",
              "serviceCategory" : {
                "text" : "service category"
              },
              "serviceType" : [ {
                "text" : "service type"
              } ],
              "specialty" : [ {
                "text" : "specialty"
              } ],
              "appointmentType" : {
                "text" : "appointment type"
              },
              "priority" : 1,
              "description" : "appointment test",
              "supportingInformation" : [ {
                "display" : "supporting info"
              } ],
              "start" : "2017-01-01T00:00:00Z",
              "end" : "2017-01-01T01:00:00Z",
              "minutesDuration" : 15,
              "slot" : [ {
                "display" : "slot"
              } ],
              "created" : "2021-11-16",
              "comment" : "comment",
              "participant" : [ {
                "actor" : {
                  "display" : "actor"
                },
                "status" : "accepted"
              } ],
              "requestedPeriod" : [ {
                "start" : "2021-11-16"
              } ]
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedAppointment = JacksonManager.objectMapper.readValue<STU3Appointment>(json)
        assertEquals(goodSTU3Appointment, deserializedAppointment)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val appointment = STU3Appointment(
            status = AppointmentStatus.CANCELLED.asCode(),
            participant = listOf(
                Participant(
                    actor = Reference(display = "actor"),
                    status = ParticipationStatus.ACCEPTED.asCode()
                )
            )
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(appointment)

        val expectedJson = """
            {
              "resourceType" : "Appointment",
              "status" : "cancelled",
              "participant" : [ {
                "actor" : {
                  "display" : "actor"
                },
                "status" : "accepted"
              } ]
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `serialized JSON without an actor but with a type`() {
        val appointment = STU3Appointment(
            status = AppointmentStatus.CANCELLED.asCode(),
            participant = listOf(
                Participant(
                    type = listOf(CodeableConcept("123")),
                    status = ParticipationStatus.ACCEPTED.asCode()
                )
            )
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(appointment)

        val expectedJson = """
            {
              "resourceType" : "Appointment",
              "status" : "cancelled",
              "participant" : [ {
                "type" : [ {
                  "id" : "123"
                } ],
                "status" : "accepted"
              } ]
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "resourceType" : "Appointment",
              "status" : "cancelled",
              "participant" : [ {
                "actor" :{
                  "display" : "actor"
                },
                "status" : "accepted"
              } ]
            }
        """.trimIndent()
        val appointment = JacksonManager.objectMapper.readValue<STU3Appointment>(json)

        assertNull(appointment.id)
        assertNull(appointment.meta)
        assertNull(appointment.implicitRules)
        assertNull(appointment.language)
        assertNull(appointment.text)
        assertEquals(listOf<STU3Resource<Nothing>>(), appointment.contained)
        assertEquals(listOf<Extension>(), appointment.extension)
        assertEquals(listOf<Extension>(), appointment.modifierExtension)
        assertEquals(listOf<Identifier>(), appointment.identifier)
        assertNull(appointment.serviceCategory)
        assertEquals(listOf<CodeableConcept>(), appointment.serviceType)
        assertEquals(listOf<CodeableConcept>(), appointment.specialty)
        assertNull(appointment.appointmentType)
        assertEquals(listOf<CodeableConcept>(), appointment.reason)
        assertEquals(listOf<Reference>(), appointment.indication)
        assertNull(appointment.priority)
        assertNull(appointment.description)
        assertEquals(listOf<Reference>(), appointment.supportingInformation)
        assertNull(appointment.start)
        assertNull(appointment.end)
        assertNull(appointment.minutesDuration)
        assertEquals(listOf<Reference>(), appointment.slot)
        assertNull(appointment.created)
        assertNull(appointment.comment)
        assertEquals(listOf<Reference>(), appointment.incomingReferral)
        assertEquals(listOf<Period>(), appointment.requestedPeriod)
    }

    @Test
    fun `test transform to R4`() {
        val r4Appointment = goodSTU3Appointment.transformToR4()
        assertEquals(goodSTU3Appointment.serviceCategory, r4Appointment.serviceCategory[0])
    }
}
