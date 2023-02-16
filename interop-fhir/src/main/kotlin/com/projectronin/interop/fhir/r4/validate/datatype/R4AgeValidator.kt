package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.Age
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 Age](http://hl7.org/fhir/R4/datatypes.html#Age)
 */
object R4AgeValidator : BaseR4QuantityValidator<Age>() {
    private val requiredCodeError = FHIRError(
        code = "R4_AGE_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "There SHALL be a code if there is a value",
        location = LocationContext(Age::class)
    )
    private val invalidSystemError = FHIRError(
        code = "R4_AGE_002",
        severity = ValidationIssueSeverity.ERROR,
        description = "If system is present, it SHALL be UCUM",
        location = LocationContext(Age::system)
    )
    private val positiveValueError = FHIRError(
        code = "R4_AGE_003",
        severity = ValidationIssueSeverity.ERROR,
        description = "If value is present, it SHALL be positive",
        location = LocationContext(Age::value)
    )

    override fun validateQuantity(quantity: Age, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkTrue((quantity.code != null || quantity.value == null), requiredCodeError, parentContext)
            checkTrue(
                (quantity.system == null || quantity.system == CodeSystem.UCUM.uri),
                invalidSystemError,
                parentContext
            )
            checkTrue((quantity.value?.value == null || quantity.value.value > 0), positiveValueError, parentContext)
        }
    }
}
