package com.projectronin.interop.fhir.r4.validate.datatype.medication

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.medication.Substitution
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Substitution](https://www.hl7.org/fhir/R4/medicationrequest-definitions.html#MedicationRequest.substitution)
 */
object R4SubstitutionValidator : R4ElementContainingValidator<Substitution>() {
    private val acceptedAllowedTypes = listOf(DynamicValueType.BOOLEAN, DynamicValueType.CODEABLE_CONCEPT)

    private val requiredAllowedError = RequiredFieldError(Substitution::allowed)
    private val invalidAllowedError = InvalidDynamicValueError(Substitution::allowed, acceptedAllowedTypes)

    override fun validateElement(element: Substitution, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.allowed, requiredAllowedError, parentContext)
            ifNotNull(element.allowed) {
                checkTrue(acceptedAllowedTypes.contains(element.allowed.type), invalidAllowedError, parentContext)
            }
        }
    }
}
