package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.DoseAndRate
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 DoseAndRate](http://hl7.org/fhir/R4/dosage-definitions.html#Dosage.doseAndRate).
 */
object R4DoseAndRateValidator : R4ElementContainingValidator<DoseAndRate>() {
    private val acceptedDoseTypes = listOf(DynamicValueType.RANGE, DynamicValueType.QUANTITY)
    private val acceptedRateTypes = listOf(DynamicValueType.RATIO, DynamicValueType.RANGE, DynamicValueType.QUANTITY)

    private val invalidDoseError = InvalidDynamicValueError(DoseAndRate::dose, acceptedDoseTypes)
    private val invalidRateError = InvalidDynamicValueError(DoseAndRate::rate, acceptedRateTypes)

    override fun validateElement(element: DoseAndRate, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.dose?.let { dose ->
                checkTrue(acceptedDoseTypes.contains(dose.type), invalidDoseError, parentContext)
            }
            element.rate?.let { rate ->
                checkTrue(acceptedRateTypes.contains(rate.type), invalidRateError, parentContext)
            }
        }
    }
}
