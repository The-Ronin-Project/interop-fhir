package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Annotation](http://hl7.org/fhir/R4/datatypes.html#Annotation)
 */
object R4AnnotationValidator : R4ElementContainingValidator<Annotation>() {
    private val acceptedAuthorTypes = listOf(DynamicValueType.REFERENCE, DynamicValueType.STRING)

    private val invalidAuthorError = InvalidDynamicValueError(Annotation::author, acceptedAuthorTypes)

    override fun validateElement(element: Annotation, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.author?.let { author ->
                checkTrue(acceptedAuthorTypes.contains(author.type), invalidAuthorError, parentContext)
            }
        }
    }
}
