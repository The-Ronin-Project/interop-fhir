package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.Location
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.LocationMode
import com.projectronin.interop.fhir.r4.valueset.LocationStatus
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Location](http://hl7.org/fhir/R4/location.html)
 */
object R4LocationValidator : R4ElementContainingValidator<Location>() {
    private val invalidStatusError = InvalidValueSetError(Location::status)
    private val invalidModeError = InvalidValueSetError(Location::mode)

    override fun validateElement(element: Location, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.status?.let {
                checkCodedEnum<LocationStatus>(it, invalidStatusError, parentContext)
            }

            element.mode?.let {
                checkCodedEnum<LocationMode>(it, invalidModeError, parentContext)
            }
        }
    }
}
