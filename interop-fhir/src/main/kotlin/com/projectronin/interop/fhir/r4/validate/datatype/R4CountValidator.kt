package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.Count
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity
import java.math.RoundingMode

/**
 * Validator for the [R4 Count](http://hl7.org/fhir/R4/datatypes.html#Count)
 */
object R4CountValidator : BaseR4QuantityValidator<Count>() {
    private val requiredCodeError =
        FHIRError(
            code = "R4_CNT_001",
            severity = ValidationIssueSeverity.ERROR,
            description = "There SHALL be a code with a value of \"1\" if there is a value",
            location = LocationContext(Count::class),
        )
    private val invalidSystemError =
        FHIRError(
            code = "R4_CNT_002",
            severity = ValidationIssueSeverity.ERROR,
            description = "If system is present, it SHALL be UCUM",
            location = LocationContext(Count::system),
        )
    private val invalidNumberError =
        FHIRError(
            code = "R4_CNT_003",
            severity = ValidationIssueSeverity.ERROR,
            description = "If present, the value SHALL be a whole number",
            location = LocationContext(Count::value),
        )

    override fun validateQuantity(
        quantity: Count,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        validation.apply {
            checkTrue(
                ((quantity.code != null && quantity.code.value == "1") || quantity.value == null),
                requiredCodeError,
                parentContext,
            )
            checkTrue(
                (quantity.system == null || quantity.system == CodeSystem.UCUM.uri),
                invalidSystemError,
                parentContext,
            )
            checkTrue(
                (
                    quantity.value?.value == null ||
                        quantity.value.value.setScale(0, RoundingMode.CEILING) ==
                        quantity.value.value.setScale(
                            0,
                            RoundingMode.FLOOR,
                        )
                ),
                invalidNumberError,
                parentContext,
            )
        }
    }
}
