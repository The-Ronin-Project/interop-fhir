package com.projectronin.interop.fhir.r4.ronin.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.CodeableConcepts
import com.projectronin.interop.fhir.r4.ExtensionMeanings
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
import com.projectronin.interop.fhir.r4.resource.Resource
import com.projectronin.interop.fhir.r4.valueset.AppointmentStatus
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import com.projectronin.interop.fhir.r4.valueset.ParticipationStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OncologyAppointmentTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val oncologyAppointment = OncologyAppointment(
            id = Id("12345"),
            meta = Meta(
                profile = listOf(Canonical("http://projectronin.com/fhir/us/ronin/StructureDefinition/oncology-practitioner")),
            ),
            implicitRules = Uri("implicit-rules"),
            language = Code("en-US"),
            text = Narrative(
                status = NarrativeStatus.GENERATED,
                div = "div"
            ),
            contained = listOf(ContainedResource("""{"resourceType":"Banana","field":"24680"}""")),
            extension = listOf(
                Extension(
                    url = ExtensionMeanings.PARTNER_DEPARTMENT.uri,
                    value = DynamicValue(DynamicValueType.REFERENCE, Reference(reference = "reference"))
                )
            ),
            modifierExtension = listOf(
                Extension(
                    url = Uri("http://localhost/modifier-extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            identifier = listOf(
                Identifier(
                    system = CodeSystem.RONIN_TENANT.uri,
                    type = CodeableConcepts.RONIN_TENANT,
                    value = "tenantId"
                )
            ),
            status = AppointmentStatus.CANCELLED,
            cancelationReason = CodeableConcept(text = "cancel reason"),
            serviceCategory = listOf(CodeableConcept(text = "service category")),
            serviceType = listOf(CodeableConcept(text = "service type")),
            specialty = listOf(CodeableConcept(text = "specialty")),
            appointmentType = CodeableConcept(text = "appointment type"),
            reasonCode = listOf(CodeableConcept(text = "reason code")),
            reasonReference = listOf(Reference(display = "reason reference")),
            priority = 1,
            description = "appointment test",
            supportingInformation = listOf(Reference(display = "supporting info")),
            start = Instant(value = "2017-01-01T00:00:00Z"),
            end = Instant(value = "2017-01-01T01:00:00Z"),
            minutesDuration = 15,
            slot = listOf(Reference(display = "slot")),
            created = DateTime(value = "2021-11-16"),
            comment = "comment",
            patientInstruction = "patient instruction",
            basedOn = listOf(Reference(display = "based on")),
            participant = listOf(
                Participant(
                    actor = Reference(display = "actor"),
                    status = ParticipationStatus.ACCEPTED
                )
            ),
            requestedPeriod = listOf(Period(start = DateTime(value = "2021-11-16")))
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(oncologyAppointment)

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
                "url" : "http://projectronin.com/fhir/us/ronin/StructureDefinition/partnerDepartmentReference",
                "valueReference" : {
                  "reference" : "reference"
                }
              } ],
              "modifierExtension" : [ {
                "url" : "http://localhost/modifier-extension",
                "valueString" : "Value"
              } ],
              "identifier" : [ {
                "type" : {
                  "coding" : [ {
                    "system" : "http://projectronin.com/id/tenantId",
                    "code" : "TID",
                    "display" : "Ronin-specified Tenant Identifier"
                  } ],
                  "text" : "Tenant ID"
                },
                "system" : "http://projectronin.com/id/tenantId",
                "value" : "tenantId"
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

        val deserializedOncologyAppointment = JacksonManager.objectMapper.readValue<OncologyAppointment>(json)
        assertEquals(oncologyAppointment, deserializedOncologyAppointment)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val oncologyAppointment = OncologyAppointment(
            extension = listOf(
                Extension(
                    url = ExtensionMeanings.PARTNER_DEPARTMENT.uri,
                    value = DynamicValue(DynamicValueType.REFERENCE, Reference(reference = "reference"))
                )
            ),
            identifier = listOf(
                Identifier(
                    system = CodeSystem.RONIN_TENANT.uri,
                    type = CodeableConcepts.RONIN_TENANT,
                    value = "tenantId"
                )
            ),
            status = AppointmentStatus.CANCELLED,
            participant = listOf(
                Participant(
                    actor = Reference(display = "actor"),
                    status = ParticipationStatus.ACCEPTED
                )
            )
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(oncologyAppointment)

        val expectedJson = """
            {
              "resourceType" : "Appointment",
              "extension" : [ {
                "url" : "http://projectronin.com/fhir/us/ronin/StructureDefinition/partnerDepartmentReference",
                "valueReference" : {
                  "reference" : "reference"
                }
              } ],
              "identifier" : [ {
                "type" : {
                  "coding" : [ {
                    "system" : "http://projectronin.com/id/tenantId",
                    "code" : "TID",
                    "display" : "Ronin-specified Tenant Identifier"
                  } ],
                  "text" : "Tenant ID"
                },
                "system" : "http://projectronin.com/id/tenantId",
                "value" : "tenantId"
              } ],
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
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "resourceType" : "Appointment",
              "extension" : [ {
                "url" : "http://projectronin.com/fhir/us/ronin/StructureDefinition/partnerDepartmentReference",
                "valueReference" : {
                  "reference" : "reference"
                }
              } ],
              "identifier" : [ {
                "type" : {
                  "coding" : [ {
                    "system" : "http://projectronin.com/id/tenantId",
                    "code" : "TID",
                    "display" : "Ronin-specified Tenant Identifier"
                  } ],
                  "text" : "Tenant ID"
                },
                "system" : "http://projectronin.com/id/tenantId",
                "value" : "tenantId"
              } ],
              "status" : "cancelled",
              "participant" : [ {
                "actor" :  {
                  "display" : "actor"
                } ,
                "status" : "accepted"
              } ]
            }
        """.trimIndent()
        val appointment = JacksonManager.objectMapper.readValue<OncologyAppointment>(json)

        assertNull(appointment.id)
        assertNull(appointment.meta)
        assertNull(appointment.implicitRules)
        assertNull(appointment.language)
        assertNull(appointment.text)
        assertEquals(listOf<Resource>(), appointment.contained)
        assertEquals(listOf<Extension>(), appointment.modifierExtension)
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
    fun `fails if minutesDuration is not positive`() {
        val exception = assertThrows<IllegalArgumentException> {
            OncologyAppointment(
                minutesDuration = 0,
                extension = listOf(
                    Extension(
                        url = ExtensionMeanings.PARTNER_DEPARTMENT.uri,
                        value = DynamicValue(DynamicValueType.REFERENCE, Reference(reference = "reference"))
                    )
                ),
                identifier = listOf(
                    Identifier(
                        system = CodeSystem.RONIN_TENANT.uri,
                        type = CodeableConcepts.RONIN_TENANT,
                        value = "tenantId"
                    )
                ),
                status = AppointmentStatus.CANCELLED,
                participant = listOf(
                    Participant(
                        actor = Reference(display = "actor"),
                        status = ParticipationStatus.ACCEPTED
                    )
                )
            )
        }
        assertEquals("Appointment duration must be positive", exception.message)
    }

    @Test
    fun `fails if priority is negative`() {
        val exception = assertThrows<IllegalArgumentException> {
            OncologyAppointment(
                priority = -1,
                extension = listOf(
                    Extension(
                        url = ExtensionMeanings.PARTNER_DEPARTMENT.uri,
                        value = DynamicValue(DynamicValueType.REFERENCE, Reference(reference = "reference"))
                    )
                ),
                identifier = listOf(
                    Identifier(
                        system = CodeSystem.RONIN_TENANT.uri,
                        type = CodeableConcepts.RONIN_TENANT,
                        value = "tenantId"
                    )
                ),
                status = AppointmentStatus.CANCELLED,
                participant = listOf(
                    Participant(
                        actor = Reference(display = "actor"),
                        status = ParticipationStatus.ACCEPTED
                    )
                )
            )
        }
        assertEquals("Priority cannot be negative", exception.message)
    }

    @Test
    fun `fails if no participant`() {
        val exception = assertThrows<IllegalArgumentException> {
            OncologyAppointment(
                extension = listOf(
                    Extension(
                        url = ExtensionMeanings.PARTNER_DEPARTMENT.uri,
                        value = DynamicValue(DynamicValueType.REFERENCE, Reference(reference = "reference"))
                    )
                ),
                identifier = listOf(
                    Identifier(
                        system = CodeSystem.RONIN_TENANT.uri,
                        type = CodeableConcepts.RONIN_TENANT,
                        value = "tenantId"
                    )
                ),
                status = AppointmentStatus.CANCELLED,
                participant = listOf()
            )
        }
        assertEquals("At least one participant must be provided", exception.message)
    }

    @Test
    fun `fails if participant doesn't have type or actor`() {
        val exception = assertThrows<IllegalArgumentException> {
            OncologyAppointment(
                extension = listOf(
                    Extension(
                        url = ExtensionMeanings.PARTNER_DEPARTMENT.uri,
                        value = DynamicValue(DynamicValueType.REFERENCE, Reference(reference = "reference"))
                    )
                ),
                identifier = listOf(
                    Identifier(
                        system = CodeSystem.RONIN_TENANT.uri,
                        type = CodeableConcepts.RONIN_TENANT,
                        value = "tenantId"
                    )
                ),
                status = AppointmentStatus.CANCELLED,
                participant = listOf(Participant(status = ParticipationStatus.ACCEPTED))
            )
        }
        assertEquals(
            "[app-1](https://www.hl7.org/fhir/R4/appointment.html#invs): Either the type or actor on the participant SHALL be specified",
            exception.message
        )
    }

    @Test
    fun `fails if appointment has start without end`() {
        val exception = assertThrows<IllegalArgumentException> {
            OncologyAppointment(
                start = Instant(value = "2017-01-01T00:00:00Z"),
                extension = listOf(
                    Extension(
                        url = ExtensionMeanings.PARTNER_DEPARTMENT.uri,
                        value = DynamicValue(DynamicValueType.REFERENCE, Reference(reference = "reference"))
                    )
                ),
                identifier = listOf(
                    Identifier(
                        system = CodeSystem.RONIN_TENANT.uri,
                        type = CodeableConcepts.RONIN_TENANT,
                        value = "tenantId"
                    )
                ),
                status = AppointmentStatus.CANCELLED,
                participant = listOf(
                    Participant(
                        actor = Reference(display = "actor"),
                        status = ParticipationStatus.ACCEPTED
                    )
                )
            )
        }
        assertEquals(
            "[app-2](https://www.hl7.org/fhir/R4/appointment.html#invs): Either start and end are specified, or neither",
            exception.message
        )
    }

    @Test
    fun `fails if no start or end and status is not cancelled or proposed`() {
        val exception = assertThrows<IllegalArgumentException> {
            OncologyAppointment(
                extension = listOf(
                    Extension(
                        url = ExtensionMeanings.PARTNER_DEPARTMENT.uri,
                        value = DynamicValue(DynamicValueType.REFERENCE, Reference(reference = "reference"))
                    )
                ),
                identifier = listOf(
                    Identifier(
                        system = CodeSystem.RONIN_TENANT.uri,
                        type = CodeableConcepts.RONIN_TENANT,
                        value = "tenantId"
                    )
                ),
                status = AppointmentStatus.BOOKED,
                participant = listOf(
                    Participant(
                        actor = Reference(display = "actor"),
                        status = ParticipationStatus.ACCEPTED
                    )
                )
            )
        }
        assertEquals(
            "[app-3](https://www.hl7.org/fhir/R4/appointment.html#invs): Only proposed or cancelled appointments can be missing start/end dates",
            exception.message
        )
    }

    @Test
    fun `fails if cancelationReason is sent and status is not cancelled or noshow`() {
        val exception = assertThrows<IllegalArgumentException> {
            OncologyAppointment(
                cancelationReason = CodeableConcept(text = "cancel reason"),
                extension = listOf(
                    Extension(
                        url = ExtensionMeanings.PARTNER_DEPARTMENT.uri,
                        value = DynamicValue(DynamicValueType.REFERENCE, Reference(reference = "reference"))
                    )
                ),
                identifier = listOf(
                    Identifier(
                        system = CodeSystem.RONIN_TENANT.uri,
                        type = CodeableConcepts.RONIN_TENANT,
                        value = "tenantId"
                    )
                ),
                status = AppointmentStatus.PROPOSED,
                participant = listOf(
                    Participant(
                        actor = Reference(display = "actor"),
                        status = ParticipationStatus.ACCEPTED
                    )
                )
            )
        }
        assertEquals(
            "[app-4](https://www.hl7.org/fhir/R4/appointment.html#invs): Cancellation reason is only used for appointments that have been cancelled, or no-show",
            exception.message
        )
    }

    @Test
    fun `succeeds if partnerReference is missing`() {
        val oncologyAppointment = OncologyAppointment(
            id = Id("12345"),
            meta = Meta(
                profile = listOf(Canonical("http://projectronin.com/fhir/us/ronin/StructureDefinition/oncology-practitioner")),
            ),
            implicitRules = Uri("implicit-rules"),
            language = Code("en-US"),
            text = Narrative(
                status = NarrativeStatus.GENERATED,
                div = "div"
            ),
            contained = listOf(ContainedResource("""{"resourceType":"Banana","field":"24680"}""")),
            modifierExtension = listOf(
                Extension(
                    url = Uri("http://localhost/modifier-extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            identifier = listOf(
                Identifier(
                    system = CodeSystem.RONIN_TENANT.uri,
                    type = CodeableConcepts.RONIN_TENANT,
                    value = "tenantId"
                )
            ),
            status = AppointmentStatus.CANCELLED,
            cancelationReason = CodeableConcept(text = "cancel reason"),
            serviceCategory = listOf(CodeableConcept(text = "service category")),
            serviceType = listOf(CodeableConcept(text = "service type")),
            specialty = listOf(CodeableConcept(text = "specialty")),
            appointmentType = CodeableConcept(text = "appointment type"),
            reasonCode = listOf(CodeableConcept(text = "reason code")),
            reasonReference = listOf(Reference(display = "reason reference")),
            priority = 1,
            description = "appointment test",
            supportingInformation = listOf(Reference(display = "supporting info")),
            start = Instant(value = "2017-01-01T00:00:00Z"),
            end = Instant(value = "2017-01-01T01:00:00Z"),
            minutesDuration = 15,
            slot = listOf(Reference(display = "slot")),
            created = DateTime(value = "2021-11-16"),
            comment = "comment",
            patientInstruction = "patient instruction",
            basedOn = listOf(Reference(display = "based on")),
            participant = listOf(
                Participant(
                    actor = Reference(display = "actor"),
                    status = ParticipationStatus.ACCEPTED
                )
            ),
            requestedPeriod = listOf(Period(start = DateTime(value = "2021-11-16")))
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(oncologyAppointment)

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
              "modifierExtension" : [ {
                "url" : "http://localhost/modifier-extension",
                "valueString" : "Value"
              } ],
              "identifier" : [ {
                "type" : {
                  "coding" : [ {
                    "system" : "http://projectronin.com/id/tenantId",
                    "code" : "TID",
                    "display" : "Ronin-specified Tenant Identifier"
                  } ],
                  "text" : "Tenant ID"
                },
                "system" : "http://projectronin.com/id/tenantId",
                "value" : "tenantId"
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
    }

    @Test
    fun `fails if partnerReference value is not a Reference`() {
        val exception = assertThrows<IllegalArgumentException> {
            OncologyAppointment(
                extension = listOf(
                    Extension(
                        url = ExtensionMeanings.PARTNER_DEPARTMENT.uri,
                        value = DynamicValue(DynamicValueType.STRING, Reference(reference = "reference"))
                    )
                ),
                identifier = listOf(
                    Identifier(
                        system = CodeSystem.RONIN_TENANT.uri,
                        type = CodeableConcepts.RONIN_TENANT,
                        value = "tenantId"
                    )
                ),
                status = AppointmentStatus.PROPOSED,
                participant = listOf(
                    Participant(
                        actor = Reference(display = "actor"),
                        status = ParticipationStatus.ACCEPTED
                    )
                )
            )
        }
        assertEquals("Partner department reference must be of type Reference", exception.message)
    }

    @Test
    fun `fails if partnerReference value is missing`() {
        val exception = assertThrows<IllegalArgumentException> {
            OncologyAppointment(
                extension = listOf(
                    Extension(
                        url = ExtensionMeanings.PARTNER_DEPARTMENT.uri
                    )
                ),
                identifier = listOf(
                    Identifier(
                        system = CodeSystem.RONIN_TENANT.uri,
                        type = CodeableConcepts.RONIN_TENANT,
                        value = "tenantId"
                    )
                ),
                status = AppointmentStatus.PROPOSED,
                participant = listOf(
                    Participant(
                        actor = Reference(display = "actor"),
                        status = ParticipationStatus.ACCEPTED
                    )
                )
            )
        }
        assertEquals("Partner department reference must be of type Reference", exception.message)
    }

    @Test
    fun `fails if partnerReference value is null`() {
        val exception = assertThrows<IllegalArgumentException> {
            OncologyAppointment(
                extension = listOf(
                    Extension(
                        url = ExtensionMeanings.PARTNER_DEPARTMENT.uri,
                        value = null
                    )
                ),
                identifier = listOf(
                    Identifier(
                        system = CodeSystem.RONIN_TENANT.uri,
                        type = CodeableConcepts.RONIN_TENANT,
                        value = "tenantId"
                    )
                ),
                status = AppointmentStatus.PROPOSED,
                participant = listOf(
                    Participant(
                        actor = Reference(display = "actor"),
                        status = ParticipationStatus.ACCEPTED
                    )
                )
            )
        }
        assertEquals("Partner department reference must be of type Reference", exception.message)
    }

    @Test
    fun `can deserialize from Aidbox-style JSON`() {
        val json = """
            {
              "resourceType" : "Appointment",
              "extension" : [ {
                "url" : "http://projectronin.com/fhir/us/ronin/StructureDefinition/partnerDepartmentReference",
                "value" : {
                  "reference" : {
                     "reference" : "Organization/123"
                  }
                }
              } ],
              "identifier" : [ {
                "type" : {
                  "coding" : [ {
                    "system" : "http://projectronin.com/id/tenantId",
                    "code" : "TID",
                    "display" : "Ronin-specified Tenant Identifier"
                  } ],
                  "text" : "Tenant ID"
                },
                "system" : "http://projectronin.com/id/tenantId",
                "value" : "tenantId"
              } ],
              "status" : "cancelled",
              "participant" : [ {
                "actor" :  {
                  "reference" : "Practitioner/123"
                } ,
                "status" : "accepted"
              } ]
            }
        """.trimIndent()
        val appointment = JacksonManager.objectMapper.readValue<OncologyAppointment>(json)

        val expectedAppointment = OncologyAppointment(
            extension = listOf(
                Extension(
                    url = Uri("http://projectronin.com/fhir/us/ronin/StructureDefinition/partnerDepartmentReference"),
                    value = DynamicValue(DynamicValueType.REFERENCE, Reference(reference = "Organization/123"))
                )
            ),
            identifier = listOf(
                Identifier(
                    type = CodeableConcepts.RONIN_TENANT,
                    system = Uri("http://projectronin.com/id/tenantId"),
                    value = "tenantId"
                )
            ),
            status = AppointmentStatus.CANCELLED,
            participant = listOf(
                Participant(
                    actor = Reference(reference = "Practitioner/123"),
                    status = ParticipationStatus.ACCEPTED
                )
            )
        )
        assertEquals(expectedAppointment, appointment)
    }
}
