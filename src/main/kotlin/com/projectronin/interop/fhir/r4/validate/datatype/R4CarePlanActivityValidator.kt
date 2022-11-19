package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.CarePlanActivity
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

object R4CarePlanActivityValidator : R4ElementContainingValidator<CarePlanActivity>() {

    private val referenceOrDetailError = FHIRError(
        code = "R4_CRPLNACT_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "Provide a reference or detail, not both",
        location = LocationContext(CarePlanActivity::class)
    )
    override fun validateElement(
        element: CarePlanActivity,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            checkTrue(
                (element.reference == null || element.detail == null),
                referenceOrDetailError,
                parentContext
            )
        }
    }
}
