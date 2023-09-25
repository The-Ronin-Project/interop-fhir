package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.Patient
import com.projectronin.interop.fhir.r4.resource.PatientContact
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 Patient](http://hl7.org/fhir/R4/patient.html)
 */
object R4PatientValidator : R4ElementContainingValidator<Patient>() {
    override fun validateElement(element: Patient, parentContext: LocationContext?, validation: Validation) {
        // Patient has no special Validation logic, but it should still evaluate its annotations and contained elements.
    }
}

/**
 * Validator for the [R4 Contact](http://hl7.org/fhir/R4/patient-definitions.html#Patient.contact).
 */
object R4PatientContactValidator : R4ElementContainingValidator<PatientContact>() {
    private val missingDetailsError = FHIRError(
        code = "R4_CNTCT_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "contact SHALL at least contain a contact's details or a reference to an organization",
        location = LocationContext(PatientContact::class)
    )

    override fun validateElement(element: PatientContact, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkTrue(
                (element.name != null || element.telecom.isNotEmpty() || element.address != null || element.organization != null),
                missingDetailsError,
                parentContext
            )
        }
    }
}
