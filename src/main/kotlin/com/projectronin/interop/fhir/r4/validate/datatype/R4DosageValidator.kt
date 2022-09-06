package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Dosage
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Dosage](http://hl7.org/fhir/R4/dosage.html#Dosage).
 */
object R4DosageValidator : R4ElementContainingValidator<Dosage>() {
    private val acceptedAsNeededTypes = listOf(DynamicValueType.BOOLEAN, DynamicValueType.CODEABLE_CONCEPT)

    private val invalidAsNeededError = InvalidDynamicValueError(Dosage::asNeeded, acceptedAsNeededTypes)

    override fun validateElement(element: Dosage, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.asNeeded?.let { asNeeded ->
                checkTrue(acceptedAsNeededTypes.contains(asNeeded.type), invalidAsNeededError, parentContext)
            }
        }
    }
}
