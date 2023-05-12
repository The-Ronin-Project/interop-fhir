package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.interop.fhir.r4.resource.Appointment
import com.projectronin.interop.fhir.r4.resource.Participant
import com.projectronin.interop.fhir.r4.valueset.AppointmentStatus
import com.projectronin.interop.fhir.r4.valueset.ParticipationStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4AppointmentValidatorTest {
    @Test
    fun `fails if no status`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4AppointmentValidator.validate(
                Appointment(
                    id = Id("1234"),
                    status = null,
                    participant = listOf(
                        Participant(
                            actor = Reference(display = FHIRString("actor")),
                            status = ParticipationStatus.ACCEPTED.asCode()
                        )
                    )
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ Appointment.status",
            exception.message
        )
    }

    @Test
    fun `fails if status outside required bounds`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4AppointmentValidator.validate(
                Appointment(
                    status = Code("unsupported-value"),
                    participant = listOf(
                        Participant(
                            actor = Reference(display = FHIRString("actor")),
                            status = ParticipationStatus.ACCEPTED.asCode()
                        )
                    )
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-value' is outside of required value set @ Appointment.status",
            exception.message
        )
    }

    @Test
    fun `fails if minutesDuration is not positive`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4AppointmentValidator.validate(
                Appointment(
                    minutesDuration = PositiveInt(0),
                    status = AppointmentStatus.CANCELLED.asCode(),
                    participant = listOf(
                        Participant(
                            actor = Reference(display = FHIRString("actor")),
                            status = ParticipationStatus.ACCEPTED.asCode()
                        )
                    )
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_INV_PRIM: Supplied value is not valid for a PositiveInt @ Appointment.minutesDuration",
            exception.message
        )
    }

    @Test
    fun `fails if priority is negative`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4AppointmentValidator.validate(
                Appointment(
                    priority = UnsignedInt(-1),
                    status = AppointmentStatus.CANCELLED.asCode(),
                    participant = listOf(
                        Participant(
                            actor = Reference(display = FHIRString("actor")),
                            status = ParticipationStatus.ACCEPTED.asCode()
                        )
                    )
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_INV_PRIM: Supplied value is not valid for a UnsignedInt @ Appointment.priority",
            exception.message
        )
    }

    @Test
    fun `fails if no participant`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4AppointmentValidator.validate(
                Appointment(
                    status = AppointmentStatus.CANCELLED.asCode(),
                    participant = listOf()
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_APPT_006: At least one participant must be provided @ Appointment.participant",
            exception.message
        )
    }

    @Test
    fun `fails if appointment has start without end`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4AppointmentValidator.validate(
                Appointment(
                    status = AppointmentStatus.CANCELLED.asCode(),
                    participant = listOf(
                        Participant(
                            actor = Reference(display = FHIRString("actor")),
                            status = ParticipationStatus.ACCEPTED.asCode()
                        )
                    ),
                    start = Instant(value = "2017-01-01T00:00:00Z")
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_APPT_001: Either start and end are specified, or neither @ Appointment",
            exception.message
        )
    }

    @Test
    fun `fails if no start or end and status is not cancelled or proposed`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4AppointmentValidator.validate(
                Appointment(
                    status = AppointmentStatus.BOOKED.asCode(),
                    participant = listOf(
                        Participant(
                            actor = Reference(display = FHIRString("actor")),
                            status = ParticipationStatus.ACCEPTED.asCode()
                        )
                    )
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_APPT_002: Start and end can only be missing for appointments with the following statuses: proposed, cancelled, waitlist @ Appointment",
            exception.message
        )
    }

    @Test
    fun `fails if cancelationReason is sent and status is not cancelled or noshow`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4AppointmentValidator.validate(
                Appointment(
                    status = AppointmentStatus.PROPOSED.asCode(),
                    participant = listOf(
                        Participant(
                            actor = Reference(display = FHIRString("actor")),
                            status = ParticipationStatus.ACCEPTED.asCode()
                        )
                    ),
                    cancelationReason = CodeableConcept(text = FHIRString("cancel reason"))
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_APPT_003: cancelationReason is only used for appointments that have the following statuses: cancelled, noshow @ Appointment",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        R4AppointmentValidator.validate(
            Appointment(
                status = AppointmentStatus.CANCELLED.asCode(),
                participant = listOf(
                    Participant(
                        type = listOf(CodeableConcept(FHIRString("123"))),
                        status = ParticipationStatus.ACCEPTED.asCode()
                    )
                )
            )
        ).alertIfErrors()
    }
}

class R4ParticipantValidatorTest {
    @Test
    fun `fails if required is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val participant = Participant(
                actor = Reference(display = FHIRString("actor")),
                status = ParticipationStatus.ACCEPTED.asCode(),
                required = Code("unsupported-required")
            )
            R4ParticipantValidator.validate(participant).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-required' is outside of required value set @ Participant.required",
            exception.message
        )
    }

    @Test
    fun `fails if no status provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val participant = Participant(
                actor = Reference(display = FHIRString("actor")),
                status = null
            )
            R4ParticipantValidator.validate(participant).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ Participant.status",
            exception.message
        )
    }

    @Test
    fun `fails if status is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val participant = Participant(
                actor = Reference(display = FHIRString("actor")),
                status = Code("unsupported-status")
            )
            R4ParticipantValidator.validate(participant).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-status' is outside of required value set @ Participant.status",
            exception.message
        )
    }

    @Test
    fun `fails if nither the type nor actor is on the participant`() {
        val exception = assertThrows<IllegalArgumentException> {
            val participant = Participant(
                status = ParticipationStatus.ACCEPTED.asCode()
            )
            R4ParticipantValidator.validate(participant).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_PRTCPNT_001: Either the type or actor on the participant SHALL be specified @ Participant",
            exception.message
        )
    }

    @Test
    fun `validates successfully with actor`() {
        val participant = Participant(
            actor = Reference(display = FHIRString("actor")),
            status = ParticipationStatus.ACCEPTED.asCode()
        )
        R4ParticipantValidator.validate(participant).alertIfErrors()
    }

    @Test
    fun `validates successfully with type`() {
        val participant = Participant(
            type = listOf(CodeableConcept(id = FHIRString("1234"))),
            status = ParticipationStatus.ACCEPTED.asCode()
        )
        R4ParticipantValidator.validate(participant).alertIfErrors()
    }
}
