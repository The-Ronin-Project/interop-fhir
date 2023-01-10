package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Expression
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 Expression](https://hl7.org/fhir/R4/metadatatypes.html#Expression).
 */
object R4ExpressionValidator : R4ElementContainingValidator<Expression>() {
    private val requiredLanguageError = RequiredFieldError(Expression::language)
    private val expressionOrReferenceError = FHIRError(
        code = "R4_EXPR_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "An expression or a reference must be provided",
        location = LocationContext(Expression::class)
    )

    override fun validateElement(element: Expression, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.language, requiredLanguageError, parentContext)

            checkTrue(
                (element.expression != null || element.reference != null),
                expressionOrReferenceError,
                parentContext
            )
        }
    }
}
