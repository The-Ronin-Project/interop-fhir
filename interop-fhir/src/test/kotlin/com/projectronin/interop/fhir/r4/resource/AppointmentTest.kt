package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.AppointmentStatus
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import com.projectronin.interop.fhir.r4.valueset.ParticipantRequired
import com.projectronin.interop.fhir.r4.valueset.ParticipationStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

@Suppress("ktlint:standard:max-line-length")
class AppointmentTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val appointment =
            Appointment(
                id = Id("12345"),
                meta =
                    Meta(
                        profile = listOf(Canonical("RoninAppointment")),
                    ),
                implicitRules = Uri("implicit-rules"),
                language = Code("en-US"),
                text =
                    Narrative(
                        status = NarrativeStatus.GENERATED.asCode(),
                        div = FHIRString("div"),
                    ),
                contained = listOf(Location(id = Id("1234"), name = FHIRString("Contained Location"))),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                modifierExtension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/modifier-extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                identifier = listOf(Identifier(value = FHIRString("id"))),
                status = AppointmentStatus.CANCELLED.asCode(),
                cancelationReason = CodeableConcept(text = FHIRString("cancel reason")),
                serviceCategory = listOf(CodeableConcept(text = FHIRString("service category"))),
                serviceType = listOf(CodeableConcept(text = FHIRString("service type"))),
                specialty = listOf(CodeableConcept(text = FHIRString("specialty"))),
                appointmentType = CodeableConcept(text = FHIRString("appointment type")),
                reasonCode = listOf(CodeableConcept(text = FHIRString("reason code"))),
                reasonReference = listOf(Reference(display = FHIRString("reason reference"))),
                priority = UnsignedInt(1),
                description = FHIRString("appointment test"),
                supportingInformation = listOf(Reference(display = FHIRString("supporting info"))),
                start = Instant(value = "2017-01-01T00:00:00Z"),
                end = Instant(value = "2017-01-01T01:00:00Z"),
                minutesDuration = PositiveInt(15),
                slot = listOf(Reference(display = FHIRString("slot"))),
                created = DateTime(value = "2021-11-16"),
                comment = FHIRString("comment"),
                patientInstruction = FHIRString("patient instruction"),
                basedOn = listOf(Reference(display = FHIRString("based on"))),
                participant =
                    listOf(
                        Participant(
                            actor = Reference(display = FHIRString("actor")),
                            status = ParticipationStatus.ACCEPTED.asCode(),
                        ),
                    ),
                requestedPeriod = listOf(Period(start = DateTime(value = "2021-11-16"))),
            )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(appointment)

        val expectedJson =
            """
            {
              "resourceType" : "Appointment",
              "id" : "12345",
              "meta" : {
                "profile" : [ "RoninAppointment" ]
              },
              "implicitRules" : "implicit-rules",
              "language" : "en-US",
              "text" : {
                "status" : "generated",
                "div" : "div"
              },
              "contained" : [ {
                "resourceType" : "Location",
                "id" : "1234",
                "name" : "Contained Location"
              } ],
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
              "status" : "cancelled",
              "cancelationReason" : {
                "text" : "cancel reason"
              },
              "serviceCategory" : [ {
                "text" : "service category"
              } ],
              "serviceType" : [ {
                "text" : "service type"
              } ],
              "specialty" : [ {
                "text" : "specialty"
              } ],
              "appointmentType" : {
                "text" : "appointment type"
              },
              "reasonCode" : [ {
                "text" : "reason code"
              } ],
              "reasonReference" : [ {
                "display" : "reason reference"
              } ],
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
              "patientInstruction" : "patient instruction",
              "basedOn" : [ {
                "display" : "based on"
              } ],
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

        val deserializedAppointment = JacksonManager.objectMapper.readValue<Appointment>(json)
        assertEquals(appointment, deserializedAppointment)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val appointment =
            Appointment(
                status = AppointmentStatus.CANCELLED.asCode(),
                participant =
                    listOf(
                        Participant(
                            actor = Reference(display = FHIRString("actor")),
                            status = ParticipationStatus.ACCEPTED.asCode(),
                        ),
                    ),
            )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(appointment)

        val expectedJson =
            """
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
        val appointment =
            Appointment(
                status = AppointmentStatus.CANCELLED.asCode(),
                participant =
                    listOf(
                        Participant(
                            type = listOf(CodeableConcept(FHIRString("123"))),
                            status = ParticipationStatus.ACCEPTED.asCode(),
                        ),
                    ),
            )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(appointment)

        val expectedJson =
            """
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
        val json =
            """
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
        val appointment = JacksonManager.objectMapper.readValue<Appointment>(json)

        assertNull(appointment.id)
        assertNull(appointment.meta)
        assertNull(appointment.implicitRules)
        assertNull(appointment.language)
        assertNull(appointment.text)
        assertEquals(listOf<Resource<Nothing>>(), appointment.contained)
        assertEquals(listOf<Extension>(), appointment.extension)
        assertEquals(listOf<Extension>(), appointment.modifierExtension)
        assertEquals(listOf<Identifier>(), appointment.identifier)
        assertNull(appointment.cancelationReason)
        assertEquals(listOf<CodeableConcept>(), appointment.serviceCategory)
        assertEquals(listOf<CodeableConcept>(), appointment.serviceType)
        assertEquals(listOf<CodeableConcept>(), appointment.specialty)
        assertNull(appointment.appointmentType)
        assertEquals(listOf<CodeableConcept>(), appointment.reasonCode)
        assertEquals(listOf<Reference>(), appointment.reasonReference)
        assertNull(appointment.priority)
        assertNull(appointment.description)
        assertEquals(listOf<Reference>(), appointment.supportingInformation)
        assertNull(appointment.start)
        assertNull(appointment.end)
        assertNull(appointment.minutesDuration)
        assertEquals(listOf<Reference>(), appointment.slot)
        assertNull(appointment.created)
        assertNull(appointment.comment)
        assertNull(appointment.patientInstruction)
        assertEquals(listOf<Reference>(), appointment.basedOn)
        assertEquals(listOf<Period>(), appointment.requestedPeriod)
    }

    @Test
    fun `can deserialize Appointment with contained unsupported resources`() {
        // This is a real example from Cerner Code Sandbox, but this is technically the post-transform, but our models should still handle it.
        val json =
            """{"resourceType":"Appointment","id":"ejh3j95h-4892263","meta":{"versionId":"3-1","lastUpdated":"2023-08-01T17:14:42Z","source":"https://objectstorage.us-phoenix-1.oraclecloud.com/n/idoll6i6jmjd/b/dev-data-lake-bronze/o/raw_data_response/tenant_id=ejh3j95h/transaction_id/80fa5abf-c596-47f0-a0ba-ba65caf4497d","profile":["http://projectronin.io/fhir/StructureDefinition/ronin-appointment"]},"text":{"status":"generated","div":"<div xmlns=\"http://www.w3.org/1999/xhtml\"><p><b>Appointment</b></p><p><b>Status</b>: Cancelled</p><p><b>Service Type</b>: Video Visit</p><p><b>Start</b>: Sep  7, 2023 10:00 P.M. UTC</p><p><b>End</b>: Sep  7, 2023 10:15 P.M. UTC</p><p><b>Participants</b>:</p><dl><dd><b>Location</b>: MX Clinic 1</dd><dd><b>Practitioner</b>: Grossnickle, Luke</dd><dd><b>Patient</b>: Smart II, Nancy</dd></dl><p><b>Video Visit</b>: Yes</p></div>"},"contained":[{"resourceType":"HealthcareService","id":"5674962","type":[{"text":"Patient Virtual Meeting Room"}],"telecom":[{"system":"url","value":"http://patientlink.vmr.net"}]},{"resourceType":"HealthcareService","id":"5674965","type":[{"text":"Provider Virtual Meeting Room"}],"telecom":[{"system":"url","value":"http://providerlink.vmr.net"}]}],"extension":[{"url":"https://fhir-ehr.cerner.com/r4/StructureDefinition/is-cancelable","valueBoolean":false},{"url":"https://fhir-ehr.cerner.com/r4/StructureDefinition/is-reschedulable","valueBoolean":false},{"url":"http://projectronin.io/fhir/StructureDefinition/Extension/tenant-sourceAppointmentStatus","valueCoding":{"system":"http://projectronin.io/fhir/CodeSystem/ejh3j95h/AppointmentStatus","code":"cancelled"}}],"identifier":[{"type":{"coding":[{"system":"http://projectronin.com/id/fhir","code":"FHIR ID","display":"FHIR Identifier"}],"text":"FHIR Identifier"},"system":"http://projectronin.com/id/fhir","value":"4892263"},{"type":{"coding":[{"system":"http://projectronin.com/id/tenantId","code":"TID","display":"Ronin-specified Tenant Identifier"}],"text":"Ronin-specified Tenant Identifier"},"system":"http://projectronin.com/id/tenantId","value":"ejh3j95h"},{"type":{"coding":[{"system":"http://projectronin.com/id/dataAuthorityId","code":"DAID","display":"Data Authority Identifier"}],"text":"Data Authority Identifier"},"system":"http://projectronin.com/id/dataAuthorityId","value":"EHR Data Authority"}],"status":"cancelled","cancelationReason":{"coding":[{"system":"https://fhir.cerner.com/ec2458f2-1e24-41c8-b71b-0e701af7583d/codeSet/14229","code":"0","userSelected":true}]},"serviceType":[{"coding":[{"system":"https://fhir.cerner.com/ec2458f2-1e24-41c8-b71b-0e701af7583d/codeSet/14249","code":"2572307911","display":"Video Visit","userSelected":true},{"system":"http://snomed.info/sct","code":"394581000","display":"Community medicine (qualifier value)","userSelected":false}],"text":"Video Visit"}],"reasonCode":[{"text":"I have a cramp"}],"description":"Video Visit","start":"2023-09-07T22:00:00Z","end":"2023-09-07T22:15:00Z","minutesDuration":15,"participant":[{"actor":{"reference":"Location/ejh3j95h-21304876","type":"Location","_type":{"extension":[{"url":"http://projectronin.io/fhir/StructureDefinition/Extension/ronin-dataAuthorityIdentifier","valueIdentifier":{"type":{"coding":[{"system":"http://projectronin.com/id/dataAuthorityId","code":"DAID","display":"Data Authority Identifier"}],"text":"Data Authority Identifier"},"system":"http://projectronin.com/id/dataAuthorityId","value":"EHR Data Authority"}}]},"display":"MX Clinic 1"},"required":"required","status":"accepted"},{"type":[{"coding":[{"system":"https://fhir.cerner.com/ec2458f2-1e24-41c8-b71b-0e701af7583d/codeSet/14250","code":"0","userSelected":true}]},{"coding":[{"system":"http://terminology.hl7.org/CodeSystem/v3-ParticipationType","code":"PPRF","display":"primary performer"}],"text":"primary performer"}],"actor":{"reference":"Practitioner/ejh3j95h-12825702","type":"Practitioner","_type":{"extension":[{"url":"http://projectronin.io/fhir/StructureDefinition/Extension/ronin-dataAuthorityIdentifier","valueIdentifier":{"type":{"coding":[{"system":"http://projectronin.com/id/dataAuthorityId","code":"DAID","display":"Data Authority Identifier"}],"text":"Data Authority Identifier"},"system":"http://projectronin.com/id/dataAuthorityId","value":"EHR Data Authority"}}]},"display":"Grossnickle, Luke"},"required":"required","status":"accepted"},{"type":[{"coding":[{"system":"https://fhir.cerner.com/ec2458f2-1e24-41c8-b71b-0e701af7583d/codeSet/14250","code":"4572","display":"Patient","userSelected":true}],"text":"Patient"}],"actor":{"reference":"Patient/ejh3j95h-12724066","type":"Patient","_type":{"extension":[{"url":"http://projectronin.io/fhir/StructureDefinition/Extension/ronin-dataAuthorityIdentifier","valueIdentifier":{"type":{"coding":[{"system":"http://projectronin.com/id/dataAuthorityId","code":"DAID","display":"Data Authority Identifier"}],"text":"Data Authority Identifier"},"system":"http://projectronin.com/id/dataAuthorityId","value":"EHR Data Authority"}}]},"display":"Smart II, Nancy"},"required":"required","status":"accepted"},{"type":[{"text":"Patient Virtual Meeting Room"}],"actor":{"reference":"#5674962"},"status":"accepted"},{"type":[{"text":"Provider Virtual Meeting Room"}],"actor":{"reference":"#5674965"},"status":"accepted"}],"requestedPeriod":[{"start":"2023-09-07T22:00:00Z","end":"2023-09-07T22:15:00Z"}]}"""
        val appointment = JacksonManager.objectMapper.readValue<Appointment>(json)

        assertEquals(2, appointment.contained.size)
        val contained1 = appointment.contained[0]
        assertInstanceOf(UnknownResource::class.java, contained1)
        assertEquals("HealthcareService", contained1.resourceType)
        assertEquals("5674962", contained1.id!!.value)

        val contained2 = appointment.contained[1]
        assertInstanceOf(UnknownResource::class.java, contained2)
        assertEquals("HealthcareService", contained2.resourceType)
        assertEquals("5674965", contained2.id!!.value)
    }
}

class ParticipantTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val participant =
            Participant(
                id = FHIRString("67890"),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                modifierExtension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/modifier-extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                type = listOf(CodeableConcept(text = FHIRString("abc"))),
                actor = Reference(display = FHIRString("actor")),
                required = ParticipantRequired.REQUIRED.asCode(),
                status = ParticipationStatus.ACCEPTED.asCode(),
                period =
                    Period(
                        start = DateTime("1998-08"),
                        end = DateTime("2002-05"),
                    ),
            )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(participant)

        val expectedJson =
            """
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
        val participant =
            Participant(
                actor = Reference(display = FHIRString("actor")),
                status = ParticipationStatus.ACCEPTED.asCode(),
            )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(participant)

        val expectedJson =
            """
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
        val json =
            """
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
