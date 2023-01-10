package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.CodeFilter
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 Contributor](http://hl7.org/fhir/R4/metadatatypes-definitions.html#DataRequirement.codeFilter).
 */
object R4CodeFilterValidator : R4ElementContainingValidator<CodeFilter>() {
    private val pathOrSearchParamError = FHIRError(
        code = "R4_CDFILT_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "Either a path or a searchParam must be provided, but not both",
        location = LocationContext(CodeFilter::class)
    )

    override fun validateElement(element: CodeFilter, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkTrue(((element.path == null) xor (element.searchParam == null)), pathOrSearchParamError, parentContext)
        }
    }
}
