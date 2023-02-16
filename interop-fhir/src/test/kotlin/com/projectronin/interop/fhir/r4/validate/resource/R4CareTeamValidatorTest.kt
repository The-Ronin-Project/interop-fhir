package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.CareTeam
import com.projectronin.interop.fhir.r4.resource.CareTeamParticipant
import com.projectronin.interop.fhir.r4.valueset.CareTeamStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4CareTeamValidatorTest {

    @Test
    fun `fails with incorrect status`() {
        val exception = assertThrows<IllegalArgumentException> {
            val careTeam = CareTeam(
                status = Code("blah")
            )
            R4CareTeamValidator.validate(careTeam).alertIfErrors()
        }
        Assertions.assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'blah' is outside of required value set @ CareTeam.status",
            exception.message
        )
    }

    @Test
    fun `validates ok`() {
        val careTeam = CareTeam(
            status = CareTeamStatus.ACTIVE.asCode(),
            id = Id("test")
        )
        R4CareTeamValidator.validate(careTeam).alertIfErrors()
    }
}

class R4CareTeamParticipantValidatorTest {
    @Test
    fun `fails if onBehalfOf populated without practitioner`() {
        val exception = assertThrows<IllegalArgumentException> {
            val participant = CareTeamParticipant(
                onBehalfOf = Reference(display = FHIRString("Test"))
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
                onBehalfOf = Reference(display = FHIRString("Test")),
                member = Reference(display = FHIRString("not right type"))
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
                onBehalfOf = Reference(display = FHIRString("Test")),
                member = Reference(display = FHIRString("not right type"), reference = FHIRString("Garbage"))
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
            onBehalfOf = Reference(display = FHIRString("Test")),
            member = Reference(display = FHIRString("Test"), type = Uri("Practitioner"))
        )
        R4CareTeamParticipantValidator.validate(participant).alertIfErrors()
    }

    @Test
    fun `validates when both member and onbehalf of are absent`() {
        val participant = CareTeamParticipant(
            id = FHIRString("123")
        )
        R4CareTeamParticipantValidator.validate(participant).alertIfErrors()
    }

    @Test
    fun `validates when both member reference and onbehalf of are present`() {
        val participant = CareTeamParticipant(
            onBehalfOf = Reference(display = FHIRString("Test")),
            member = Reference(display = FHIRString("Test"), reference = FHIRString("ehr.com/Practitioner/123"))
        )
        R4CareTeamParticipantValidator.validate(participant).alertIfErrors()
    }
}
