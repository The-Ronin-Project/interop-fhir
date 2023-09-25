package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.MedicationAdministration
import com.projectronin.interop.fhir.r4.resource.MedicationAdministrationDosage
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 MedicationAdministration](https://www.hl7.org/fhir/R4/medicationrequest.html)
 */
object R4MedicationAdministrationValidator : R4ElementContainingValidator<MedicationAdministration>() {
    override fun validateElement(
        element: MedicationAdministration,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        // MedicationAdministration has no special Validation logic, but it should still evaluate its annotations and contained elements.
    }
}

object R4MedAdminDosageValidator : R4ElementContainingValidator<MedicationAdministrationDosage>() {
    private val requiredDoseOrRateError = FHIRError(
        code = "R4_MAD_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "If dosage is provided, it SHALL have at least one of dosage.dose or dosage.rate[x]",
        location = LocationContext(MedicationAdministration::dosage)
    )

    override fun validateElement(
        element: MedicationAdministrationDosage,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            checkTrue(
                (element.rate != null || element.dose != null),
                requiredDoseOrRateError,
                parentContext
            )
        }
    }
}
