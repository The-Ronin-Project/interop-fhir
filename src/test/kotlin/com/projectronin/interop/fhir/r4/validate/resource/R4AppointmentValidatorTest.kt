package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Participant
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.resource.Appointment
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
                "ERROR INV_VALUE_SET: status is outside of required value set @ Appointment.status",
            exception.message
        )
    }

    @Test
    fun `fails if minutesDuration is not positive`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4AppointmentValidator.validate(
                Appointment(
                    minutesDuration = FHIRInteger(0),
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
                "ERROR R4_APPT_004: Appointment duration must be positive @ Appointment.minutesDuration",
            exception.message
        )
    }

    @Test
    fun `fails if priority is negative`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4AppointmentValidator.validate(
                Appointment(
                    priority = FHIRInteger(-1),
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
                "ERROR R4_APPT_005: Priority cannot be negative @ Appointment.priority",
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
