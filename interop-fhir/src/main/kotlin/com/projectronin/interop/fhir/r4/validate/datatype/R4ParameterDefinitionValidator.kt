package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.ParameterDefinition
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.OperationParameterUse
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 ParameterDefinition](http://hl7.org/fhir/R4/metadatatypes.html#ParameterDefinition).
 */
object R4ParameterDefinitionValidator : R4ElementContainingValidator<ParameterDefinition>() {
    override fun validateElement(
        element: ParameterDefinition,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            element.use?.let {
                checkCodedEnum<OperationParameterUse>(
                    element.use,
                    InvalidValueSetError(ParameterDefinition::use, element.use.value),
                    parentContext
                )
            }
        }
    }
}
