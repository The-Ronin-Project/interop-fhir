package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 Extension](https://hl7.org/fhir/R4/extensibility.html#Extension)
 */
object R4ExtensionValidator : R4ElementContainingValidator<Extension>() {
    private val requiredUrlError = RequiredFieldError(Extension::url)
    private val extensionOrValueError = FHIRError(
        code = "R4_EXT_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "Extension must have either extensions or value[x], not both",
        location = LocationContext(Extension::class)
    )

    override fun validateElement(element: Extension, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.url, requiredUrlError, parentContext)

            // This was previously removed, but with the new Validation service and flow, I've added it back in for us to track.
            checkTrue(((element.value == null) xor element.extension.isEmpty()), extensionOrValueError, parentContext)
        }
    }
}
