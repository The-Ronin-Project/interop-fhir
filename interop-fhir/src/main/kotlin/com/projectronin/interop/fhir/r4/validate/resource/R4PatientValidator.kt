package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.resource.Patient
import com.projectronin.interop.fhir.r4.resource.PatientContact
import com.projectronin.interop.fhir.r4.resource.PatientLink
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.AdministrativeGender
import com.projectronin.interop.fhir.r4.valueset.LinkType
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 Patient](http://hl7.org/fhir/R4/patient.html)
 */
object R4PatientValidator : R4ElementContainingValidator<Patient>() {
    private val acceptedDeceasedTypes = listOf(DynamicValueType.BOOLEAN, DynamicValueType.DATE_TIME)
    private val acceptedMultipleBirthTypes = listOf(DynamicValueType.BOOLEAN, DynamicValueType.INTEGER)

    private val invalidDeceasedError = InvalidDynamicValueError(Patient::deceased, acceptedDeceasedTypes)
    private val invalidMultipleBirthError = InvalidDynamicValueError(Patient::multipleBirth, acceptedMultipleBirthTypes)

    override fun validateElement(element: Patient, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.gender?.let { code ->
                checkCodedEnum<AdministrativeGender>(
                    code,
                    InvalidValueSetError(Patient::gender, code.value),
                    parentContext
                )
            }

            element.deceased?.let { data ->
                checkTrue(acceptedDeceasedTypes.contains(data.type), invalidDeceasedError, parentContext)
            }
            element.multipleBirth?.let { data ->
                checkTrue(acceptedMultipleBirthTypes.contains(data.type), invalidMultipleBirthError, parentContext)
            }
        }
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
            element.gender?.let { code ->
                checkCodedEnum<AdministrativeGender>(
                    code,
                    InvalidValueSetError(PatientContact::gender, code.value),
                    parentContext
                )
            }

            checkTrue(
                (element.name != null || element.telecom.isNotEmpty() || element.address != null || element.organization != null),
                missingDetailsError,
                parentContext
            )
        }
    }
}

/**
 * Validator for the [R4 PatientLink](http://hl7.org/fhir/R4/patient-definitions.html#Patient.link).
 */
object R4PatientLinkValidator : R4ElementContainingValidator<PatientLink>() {
    override fun validateElement(element: PatientLink, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.type?.let {
                checkCodedEnum<LinkType>(
                    element.type,
                    InvalidValueSetError(PatientLink::type, element.type.value),
                    parentContext
                )
            }
        }
    }
}
