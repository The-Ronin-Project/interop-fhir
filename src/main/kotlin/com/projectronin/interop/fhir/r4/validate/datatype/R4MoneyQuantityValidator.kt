package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.MoneyQuantity
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 MoneyQuantity](http://hl7.org/fhir/R4/datatypes.html#MoneyVariations)
 */
object R4MoneyQuantityValidator : BaseR4QuantityValidator<MoneyQuantity>() {
    private val requiredCodeError = FHIRError(
        code = "R4_MONQUAN_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "There SHALL be a code if there is a value",
        location = LocationContext(MoneyQuantity::class)
    )
    private val invalidSystemError = FHIRError(
        code = "R4_MONQUAN_002",
        severity = ValidationIssueSeverity.ERROR,
        description = "If system is present, it SHALL be CURRENCY",
        location = LocationContext(MoneyQuantity::system)
    )

    override fun validateQuantity(quantity: MoneyQuantity, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkTrue((quantity.code != null || quantity.value == null), requiredCodeError, parentContext)
            checkTrue(
                (quantity.system == null || quantity.system == CodeSystem.CURRENCY.uri),
                invalidSystemError,
                parentContext
            )
        }
    }
}
