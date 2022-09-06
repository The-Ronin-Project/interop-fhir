package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Ratio
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 Ratio](http://hl7.org/fhir/R4/datatypes.html#Ratio).
 */
object R4RatioValidator : R4ElementContainingValidator<Ratio>() {
    private val requiredDenominatorError = FHIRError(
        code = "R4_RATIO_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "denominator required when numerator present",
        location = LocationContext(Ratio::class)
    )
    private val requiredNumeratorError = FHIRError(
        code = "R4_RATIO_002",
        severity = ValidationIssueSeverity.ERROR,
        description = "numerator required when denominator present",
        location = LocationContext(Ratio::class)
    )
    private val numeratorOrExtensionError = FHIRError(
        code = "R4_RATIO_003",
        severity = ValidationIssueSeverity.ERROR,
        description = "extension required if no numerator and denominator",
        location = LocationContext(Ratio::class)
    )

    override fun validateElement(element: Ratio, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.numerator?.let {
                checkNotNull(element.denominator, requiredDenominatorError, parentContext)
            }
            element.denominator?.let {
                checkNotNull(element.numerator, requiredNumeratorError, parentContext)
            }

            // We've already checked numerator and denominator, so we either have both or we have neither.
            checkTrue(
                (element.numerator != null || element.extension.isNotEmpty()),
                numeratorOrExtensionError,
                parentContext
            )
        }
    }
}
