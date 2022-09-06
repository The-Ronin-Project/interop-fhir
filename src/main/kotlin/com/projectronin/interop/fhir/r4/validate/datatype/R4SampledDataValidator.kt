package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.SampledData
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 SampledData](http://hl7.org/fhir/R4/datatypes.html#SampledData).
 */
object R4SampledDataValidator : R4ElementContainingValidator<SampledData>() {
    private val requiredOriginError = RequiredFieldError(SampledData::origin)
    private val requiredPeriodError = RequiredFieldError(SampledData::period)
    private val requiredDimensionsError = RequiredFieldError(SampledData::dimensions)

    override fun validateElement(element: SampledData, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.origin, requiredOriginError, parentContext)
            checkNotNull(element.period, requiredPeriodError, parentContext)
            checkNotNull(element.dimensions, requiredDimensionsError, parentContext)
        }
    }
}
