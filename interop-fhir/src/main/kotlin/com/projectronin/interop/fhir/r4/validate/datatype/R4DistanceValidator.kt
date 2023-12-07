package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.Distance
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 Distance](http://hl7.org/fhir/R4/datatypes.html#Distance)
 */
object R4DistanceValidator : BaseR4QuantityValidator<Distance>() {
    private val requiredCodeError =
        FHIRError(
            code = "R4_DIST_001",
            severity = ValidationIssueSeverity.ERROR,
            description = "There SHALL be a code if there is a value",
            location = LocationContext(Distance::class),
        )
    private val invalidSystemError =
        FHIRError(
            code = "R4_DIST_002",
            severity = ValidationIssueSeverity.ERROR,
            description = "If system is present, it SHALL be UCUM",
            location = LocationContext(Distance::system),
        )

    override fun validateQuantity(
        quantity: Distance,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        validation.apply {
            checkTrue((quantity.code != null || quantity.value == null), requiredCodeError, parentContext)
            checkTrue(
                (quantity.system == null || quantity.system == CodeSystem.UCUM.uri),
                invalidSystemError,
                parentContext,
            )
        }
    }
}
