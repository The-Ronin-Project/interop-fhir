package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.CareTeamParticipant
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

object R4CareTeamParticipantValidator : R4ElementContainingValidator<CareTeamParticipant>() {
    private val onBehalfOfError = FHIRError(
        code = "R4_CRTMPRTCPNT_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "CareTeam.participant.onBehalfOf can only be populated when CareTeam.participant.member is a Practitioner",
        location = LocationContext(CareTeamParticipant::class)
    )
    override fun validateElement(
        element: CareTeamParticipant,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            ifNotNull(element.onBehalfOf) {
                val isPractitioner = element.member?.let {
                    it.type?.value == "Practitioner" ||
                        it.reference?.contains("Practitioner/") == true
                } ?: false
                checkTrue(isPractitioner, onBehalfOfError, parentContext)
            }
        }
    }
}
