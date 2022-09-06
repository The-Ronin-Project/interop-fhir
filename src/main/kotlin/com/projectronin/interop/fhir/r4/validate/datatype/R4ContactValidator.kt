package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Contact
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.AdministrativeGender
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 Contact](http://hl7.org/fhir/R4/patient-definitions.html#Patient.contact).
 */
object R4ContactValidator : R4ElementContainingValidator<Contact>() {
    private val invalidGenderError = InvalidValueSetError(Contact::gender)
    private val missingDetailsError = FHIRError(
        code = "R4_CNTCT_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "contact SHALL at least contain a contact's details or a reference to an organization",
        location = LocationContext(Contact::class)
    )

    override fun validateElement(element: Contact, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.gender?.let {
                checkCodedEnum<AdministrativeGender>(it, invalidGenderError, parentContext)
            }

            checkTrue(
                (element.name != null || element.telecom.isNotEmpty() || element.address != null || element.organization != null),
                missingDetailsError,
                parentContext
            )
        }
    }
}
