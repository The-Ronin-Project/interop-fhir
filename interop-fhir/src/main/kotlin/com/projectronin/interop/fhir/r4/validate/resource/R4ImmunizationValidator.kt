package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.Immunization
import com.projectronin.interop.fhir.r4.resource.ImmunizationEducation
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 Immunization](https://hl7.org/fhir/r4/immunization.html)
 */
object R4ImmunizationValidator : R4ElementContainingValidator<Immunization>() {
    override fun validateElement(
        element: Immunization,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        // Immunization has no special Validation logic, but it should still evaluate its annotations and contained elements.
    }
}

/**
 * Validator for the [R4 Immunization.Education](https://hl7.org/fhir/r4/immunization-definitions.html#Immunization.education).
 */
object R4ImmunizationEducationValidator : R4ElementContainingValidator<ImmunizationEducation>() {
    private val noDocumentTypeOrReferenceError =
        FHIRError(
            code = "R4_IMMEDU_001",
            severity = ValidationIssueSeverity.ERROR,
            description = "One of documentType or reference SHALL be present",
            location = LocationContext(ImmunizationEducation::class),
        )

    override fun validateElement(
        element: ImmunizationEducation,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        validation.apply {
            checkTrue(
                element.documentType != null || element.reference != null,
                noDocumentTypeOrReferenceError,
                parentContext,
            )
        }
    }
}
