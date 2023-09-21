package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.DataRequirement
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 DataRequirement](http://hl7.org/fhir/R4/metadatatypes.html#DataRequirement).
 */
object R4DataRequirementValidator : R4ElementContainingValidator<DataRequirement>() {
    private val acceptedSubjectTypes = listOf(DynamicValueType.CODEABLE_CONCEPT, DynamicValueType.REFERENCE)

    private val invalidSubjectError = InvalidDynamicValueError(DataRequirement::subject, acceptedSubjectTypes)

    override fun validateElement(element: DataRequirement, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.subject?.let { subject ->
                checkTrue(acceptedSubjectTypes.contains(subject.type), invalidSubjectError, parentContext)
            }
        }
    }
}
