package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Participant
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.valueset.ParticipationStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4ParticipantValidatorTest {
    @Test
    fun `fails if required is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val participant = Participant(
                actor = Reference(display = "actor"),
                status = ParticipationStatus.ACCEPTED.asCode(),
                required = Code("unsupported-required")
            )
            R4ParticipantValidator.validate(participant).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: required is outside of required value set @ Participant.required",
            exception.message
        )
    }

    @Test
    fun `fails if no status provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val participant = Participant(
                actor = Reference(display = "actor"),
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
                actor = Reference(display = "actor"),
                status = Code("unsupported-status")
            )
            R4ParticipantValidator.validate(participant).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: status is outside of required value set @ Participant.status",
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
            actor = Reference(display = "actor"),
            status = ParticipationStatus.ACCEPTED.asCode()
        )
        R4ParticipantValidator.validate(participant).alertIfErrors()
    }

    @Test
    fun `validates successfully with type`() {
        val participant = Participant(
            type = listOf(CodeableConcept(id = "1234")),
            status = ParticipationStatus.ACCEPTED.asCode()
        )
        R4ParticipantValidator.validate(participant).alertIfErrors()
    }
}
