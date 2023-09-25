package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.ValueSet
import com.projectronin.interop.fhir.r4.resource.ValueSetContains
import com.projectronin.interop.fhir.r4.resource.ValueSetInclude
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

object R4ValueSetValidator : R4ElementContainingValidator<ValueSet>() {
    override fun validateElement(element: ValueSet, parentContext: LocationContext?, validation: Validation) {
        // ValueSet has no special Validation logic, but it should still evaluate its annotations and contained elements.
    }
}

object R4ValueSetIncludeValidator : R4ElementContainingValidator<ValueSetInclude>() {
    private val requiredValueSetOrSystemError = FHIRError(
        code = "R4_VALSTINC_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "A value set include/exclude SHALL have a value set or a system",
        location = LocationContext(ValueSetInclude::class)
    )
    private val missingRequiredFilterError = FHIRError(
        code = "R4_VALSTINC_002",
        severity = ValidationIssueSeverity.ERROR,
        description = "A value set with concepts or filters SHALL include a system",
        location = LocationContext(ValueSetInclude::class)
    )
    private val conflictingPropertiesError = FHIRError(
        code = "R4_VALSTINC_003",
        severity = ValidationIssueSeverity.ERROR,
        description = "Cannot have both concept and filter",
        location = LocationContext(ValueSetInclude::class)
    )

    override fun validateElement(
        element: ValueSetInclude,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            checkTrue(
                element.valueSet.isNotEmpty() || element.system != null,
                requiredValueSetOrSystemError,
                parentContext
            )
            checkTrue(
                (element.concept.isEmpty() && element.filter.isEmpty()) || element.system != null,
                missingRequiredFilterError,
                parentContext
            )
            checkTrue(
                element.concept.isEmpty() || element.filter.isEmpty(),
                conflictingPropertiesError,
                parentContext
            )
        }
    }
}

object R4ValueSetContainsValidator : R4ElementContainingValidator<ValueSetContains>() {
    private val requiredCodeOrDisplayError = FHIRError(
        code = "R4_VALSTCON_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "SHALL have a code or a display",
        location = LocationContext(ValueSetContains::class)
    )
    private val missingRequiredCodeError = FHIRError(
        code = "R4_VALSTCON_002",
        severity = ValidationIssueSeverity.ERROR,
        description = "Must have a code if not abstract",
        location = LocationContext(ValueSetContains::class)
    )
    private val missingRequiredSystemError = FHIRError(
        code = "R4_VALSTCON_003",
        severity = ValidationIssueSeverity.ERROR,
        description = "Must have a system if a code is present",
        location = LocationContext(ValueSetContains::class)
    )

    override fun validateElement(
        element: ValueSetContains,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            if (element.code == null) {
                checkTrue(element.display != null, requiredCodeOrDisplayError, parentContext)
                checkTrue(element.abstract?.value == true, missingRequiredCodeError, parentContext)
            } else {
                checkTrue(element.system != null, missingRequiredSystemError, parentContext)
            }
        }
    }
}
