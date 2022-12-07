package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.ParameterDefinition
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.OperationParameterUse
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 ParameterDefinition](http://hl7.org/fhir/R4/metadatatypes.html#ParameterDefinition).
 */
object R4ParameterDefinitionValidator : R4ElementContainingValidator<ParameterDefinition>() {
    private val requiredUseError = RequiredFieldError(ParameterDefinition::use)
    private val requiredTypeError = RequiredFieldError(ParameterDefinition::type)

    override fun validateElement(
        element: ParameterDefinition,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            checkNotNull(element.use, requiredUseError, parentContext)
            ifNotNull(element.use) {
                checkCodedEnum<OperationParameterUse>(
                    element.use,
                    InvalidValueSetError(ParameterDefinition::use, element.use.value),
                    parentContext
                )
            }

            checkNotNull(element.type, requiredTypeError, parentContext)
        }
    }
}
