package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.CareTeam
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.CareTeamStatus
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

object R4CareTeamValidator : R4ElementContainingValidator<CareTeam>() {
    override fun validateElement(element: CareTeam, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            ifNotNull(element.status) {
                checkCodedEnum<CareTeamStatus>(
                    element.status!!,
                    InvalidValueSetError(CareTeam::status, element.status.value),
                    parentContext
                )
            }
        }
    }
}
