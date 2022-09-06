package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.LocationPosition
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 LocationPosition](http://hl7.org/fhir/R4/location-definitions.html#Location.position)
 */
object R4LocationPositionValidator : R4ElementContainingValidator<LocationPosition>() {
    private val requiredLongitudeError = RequiredFieldError(LocationPosition::longitude)
    private val requiredLatitudeError = RequiredFieldError(LocationPosition::latitude)

    override fun validateElement(element: LocationPosition, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.longitude, requiredLongitudeError, parentContext)
            checkNotNull(element.latitude, requiredLatitudeError, parentContext)
        }
    }
}
