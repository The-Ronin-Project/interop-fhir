package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.resource.RequestGroup
import com.projectronin.interop.fhir.r4.resource.RequestGroupAction
import com.projectronin.interop.fhir.r4.resource.RequestGroupRelatedAction
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

object R4RequestGroupValidator : R4ElementContainingValidator<RequestGroup>() {
    override fun validateElement(element: RequestGroup, parentContext: LocationContext?, validation: Validation) {
        // RequestGroup has no special Validation logic, but it should still evaluate its annotations and contained elements.
    }
}

object R4RequestGroupActionValidator : R4ElementContainingValidator<RequestGroupAction>() {
    private val acceptedTimingTypes = listOf(
        DynamicValueType.DATE_TIME,
        DynamicValueType.AGE,
        DynamicValueType.PERIOD,
        DynamicValueType.DURATION,
        DynamicValueType.RANGE,
        DynamicValueType.TIMING
    )
    private val invalidTimingError = InvalidDynamicValueError(RequestGroupAction::timing, acceptedTimingTypes)
    private val resourceOrActionError = FHIRError(
        code = "R4_REQGRAC_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "Must have resource or action but not both",
        location = LocationContext(RequestGroupAction::class)
    )

    override fun validateElement(element: RequestGroupAction, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.timing?.let {
                checkTrue(acceptedTimingTypes.contains(it.type), invalidTimingError, parentContext)
            }

            checkTrue(((element.resource != null) != element.action.isNotEmpty()), resourceOrActionError, parentContext)
        }
    }
}

object R4RequestGroupRelatedActionValidator : R4ElementContainingValidator<RequestGroupRelatedAction>() {
    private val acceptedOffsetTypes = listOf(
        DynamicValueType.DURATION,
        DynamicValueType.RANGE
    )
    private val invalidOffsetError = InvalidDynamicValueError(RequestGroupRelatedAction::offset, acceptedOffsetTypes)

    override fun validateElement(
        element: RequestGroupRelatedAction,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            element.offset?.let {
                checkTrue(acceptedOffsetTypes.contains(it.type), invalidOffsetError, parentContext)
            }
        }
    }
}
