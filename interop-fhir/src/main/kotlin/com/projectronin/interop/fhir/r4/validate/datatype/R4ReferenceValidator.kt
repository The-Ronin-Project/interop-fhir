package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 Reference](http://hl7.org/fhir/R4/references.html#Reference).
 */
object R4ReferenceValidator : R4ElementContainingValidator<Reference>() {
    private val requiredDetailsError =
        FHIRError(
            code = "R4_REF_001",
            severity = ValidationIssueSeverity.ERROR,
            description = "At least one of reference, identifier and display SHALL be present (unless an extension is provided)",
            location = LocationContext(Reference::class),
        )

    override fun validateElement(
        element: Reference,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        validation.apply {
            checkTrue(
                (element.reference != null || element.identifier != null || element.display != null || element.extension.isNotEmpty()),
                requiredDetailsError,
                parentContext,
            )
        }
    }
}
