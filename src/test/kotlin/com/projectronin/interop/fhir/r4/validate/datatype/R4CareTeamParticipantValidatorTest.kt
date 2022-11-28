package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.CareTeamParticipant
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4CareTeamParticipantValidatorTest {
    @Test
    fun `fails if onBehalfOf populated without practitioner`() {
        val exception = assertThrows<IllegalArgumentException> {
            val participant = CareTeamParticipant(
                onBehalfOf = Reference(display = "Test")
            )
            R4CareTeamParticipantValidator.validate(participant).alertIfErrors()
        }
        Assertions.assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_CRTMPRTCPNT_001: CareTeam.participant.onBehalfOf can only be populated when CareTeam.participant.member is a Practitioner @ CareTeamParticipant",
            exception.message
        )
    }

    @Test
    fun `fails if onBehalfOf populated with non practitioner indication`() {
        val exception = assertThrows<IllegalArgumentException> {
            val participant = CareTeamParticipant(
                onBehalfOf = Reference(display = "Test"),
                member = Reference(display = "not right type")
            )
            R4CareTeamParticipantValidator.validate(participant).alertIfErrors()
        }
        Assertions.assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_CRTMPRTCPNT_001: CareTeam.participant.onBehalfOf can only be populated when CareTeam.participant.member is a Practitioner @ CareTeamParticipant",
            exception.message
        )
    }

    @Test
    fun `fails if onBehalfOf populated with non practitioner`() {
        val exception = assertThrows<IllegalArgumentException> {
            val participant = CareTeamParticipant(
                onBehalfOf = Reference(display = "Test"),
                member = Reference(display = "not right type", reference = "Garbage")
            )
            R4CareTeamParticipantValidator.validate(participant).alertIfErrors()
        }
        Assertions.assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_CRTMPRTCPNT_001: CareTeam.participant.onBehalfOf can only be populated when CareTeam.participant.member is a Practitioner @ CareTeamParticipant",
            exception.message
        )
    }

    @Test
    fun `validates when both member type and onbehalf of are present`() {
        val participant = CareTeamParticipant(
            onBehalfOf = Reference(display = "Test"),
            member = Reference(display = "Test", type = Uri("Practitioner"))
        )
        R4CareTeamParticipantValidator.validate(participant).alertIfErrors()
    }

    @Test
    fun `validates when both member and onbehalf of are absent`() {
        val participant = CareTeamParticipant(
            id = "123"
        )
        R4CareTeamParticipantValidator.validate(participant).alertIfErrors()
    }

    @Test
    fun `validates when both member reference and onbehalf of are present`() {
        val participant = CareTeamParticipant(
            onBehalfOf = Reference(display = "Test"),
            member = Reference(display = "Test", reference = "ehr.com/Practitioner/123")
        )
        R4CareTeamParticipantValidator.validate(participant).alertIfErrors()
    }
}
