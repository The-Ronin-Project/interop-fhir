package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.DateFilter
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 DateFilter](http://hl7.org/fhir/R4/metadatatypes-definitions.html#DataRequirement.dateFilter).
 */
object R4DateFilterValidator : R4ElementContainingValidator<DateFilter>() {
    private val acceptedValueTypes =
        listOf(DynamicValueType.DATE_TIME, DynamicValueType.PERIOD, DynamicValueType.DURATION)

    private val pathOrSearchParamError = FHIRError(
        code = "R4_DTFILT_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "Either a path or a searchParam must be provided, but not both",
        location = LocationContext(DateFilter::class)
    )
    private val invalidValueError = InvalidDynamicValueError(DateFilter::value, acceptedValueTypes)

    override fun validateElement(element: DateFilter, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkTrue(((element.path == null) xor (element.searchParam == null)), pathOrSearchParamError, parentContext)

            element.value?.let { value ->
                checkTrue(acceptedValueTypes.contains(value.type), invalidValueError, parentContext)
            }
        }
    }
}
