package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.Duration
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 Duration](http://hl7.org/fhir/R4/datatypes.html#Duration)
 */
object R4DurationValidator : BaseR4QuantityValidator<Duration>() {
    private val requiredCodeError = FHIRError(
        code = "R4_DUR_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "There SHALL be a code if there is a value",
        location = LocationContext(Duration::class)
    )
    private val invalidSystemError = FHIRError(
        code = "R4_DUR_002",
        severity = ValidationIssueSeverity.ERROR,
        description = "If system is present, it SHALL be UCUM",
        location = LocationContext(Duration::system)
    )

    override fun validateQuantity(quantity: Duration, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkTrue((quantity.code != null || quantity.value == null), requiredCodeError, parentContext)
            checkTrue(
                (quantity.system == null || quantity.system == CodeSystem.UCUM.uri),
                invalidSystemError,
                parentContext
            )
        }
    }
}
