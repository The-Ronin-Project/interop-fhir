package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.CarePlan
import com.projectronin.interop.fhir.r4.resource.CarePlanActivity
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

object R4CarePlanValidator : R4ElementContainingValidator<CarePlan>() {
    override fun validateElement(element: CarePlan, parentContext: LocationContext?, validation: Validation) {
        // CarePlan has no special Validation logic, but it should still evaluate its annotations and contained elements.
    }
}

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
