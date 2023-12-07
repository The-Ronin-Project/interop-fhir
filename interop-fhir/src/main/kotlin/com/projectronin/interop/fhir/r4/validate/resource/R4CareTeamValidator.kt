package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.CareTeam
import com.projectronin.interop.fhir.r4.resource.CareTeamParticipant
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

object R4CareTeamValidator : R4ElementContainingValidator<CareTeam>() {
    override fun validateElement(
        element: CareTeam,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        // CareTeam has no special Validation logic, but it should still evaluate its annotations and contained elements.
    }
}

object R4CareTeamParticipantValidator : R4ElementContainingValidator<CareTeamParticipant>() {
    private val onBehalfOfError =
        FHIRError(
            code = "R4_CRTMPRTCPNT_001",
            severity = ValidationIssueSeverity.ERROR,
            description = "CareTeam.participant.onBehalfOf can only be populated when CareTeam.participant.member is a Practitioner",
            location = LocationContext(CareTeamParticipant::class),
        )

    override fun validateElement(
        element: CareTeamParticipant,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        validation.apply {
            ifNotNull(element.onBehalfOf) {
                val isPractitioner =
                    element.member?.let {
                        it.type?.value == "Practitioner" ||
                            it.reference?.value?.contains("Practitioner/") == true
                    } ?: false
                checkTrue(isPractitioner, onBehalfOfError, parentContext)
            }
        }
    }
}
