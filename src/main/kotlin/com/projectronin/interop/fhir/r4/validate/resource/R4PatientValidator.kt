package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.resource.Patient
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.AdministrativeGender
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Patient](http://hl7.org/fhir/R4/patient.html)
 */
object R4PatientValidator : R4ElementContainingValidator<Patient>() {
    private val acceptedDeceasedTypes = listOf(DynamicValueType.BOOLEAN, DynamicValueType.DATE_TIME)
    private val acceptedMultipleBirthTypes = listOf(DynamicValueType.BOOLEAN, DynamicValueType.INTEGER)

    private val invalidGenderError = InvalidValueSetError(Patient::gender)
    private val invalidDeceasedError = InvalidDynamicValueError(Patient::deceased, acceptedDeceasedTypes)
    private val invalidMultipleBirthError = InvalidDynamicValueError(Patient::multipleBirth, acceptedMultipleBirthTypes)

    override fun validateElement(element: Patient, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.gender?.let {
                checkCodedEnum<AdministrativeGender>(it, invalidGenderError, parentContext)
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
