package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.RequestGroup
import com.projectronin.interop.fhir.r4.resource.RequestGroupAction
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

object R4RequestGroupValidator : R4ElementContainingValidator<RequestGroup>() {
    override fun validateElement(
        element: RequestGroup,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        // RequestGroup has no special Validation logic, but it should still evaluate its annotations and contained elements.
    }
}

object R4RequestGroupActionValidator : R4ElementContainingValidator<RequestGroupAction>() {
    private val resourceOrActionError =
        FHIRError(
            code = "R4_REQGRAC_001",
            severity = ValidationIssueSeverity.ERROR,
            description = "Must have resource or action but not both",
            location = LocationContext(RequestGroupAction::class),
        )

    override fun validateElement(
        element: RequestGroupAction,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        validation.apply {
            checkTrue(((element.resource != null) != element.action.isNotEmpty()), resourceOrActionError, parentContext)
        }
    }
}
