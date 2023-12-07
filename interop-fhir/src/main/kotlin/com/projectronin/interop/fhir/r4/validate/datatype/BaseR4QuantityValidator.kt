package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.BaseQuantity
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Base validator for [R4 Quantity](https://hl7.org/fhir/R4/datatypes.html#Quantity)
 */
abstract class BaseR4QuantityValidator<T : BaseQuantity<T>> : R4ElementContainingValidator<T>() {
    private val requiredSystemError =
        FHIRError(
            code = "R4_QUAN_001",
            severity = ValidationIssueSeverity.ERROR,
            description = "If a code for the unit is present, the system SHALL also be present",
            location = LocationContext("Quantity", null),
        )

    /**
     * Validates the [quantity] for any implementation-specific validation.
     */
    protected open fun validateQuantity(
        quantity: T,
        parentContext: LocationContext?,
        validation: Validation,
    ) {}

    override fun validateElement(
        element: T,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        validation.apply {
            checkTrue((element.code == null || element.system != null), requiredSystemError, parentContext)

            validateQuantity(element, parentContext, this)
        }
    }
}
