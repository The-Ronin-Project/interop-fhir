package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.ServiceRequest
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 ServiceRequest](http://hl7.org/fhir/R4/servicerequest.html)
 */
object R4ServiceRequestValidator : R4ElementContainingValidator<ServiceRequest>() {
    private val orderDetailWithNoCodeError =
        FHIRError(
            code = "R4_PRR_001",
            severity = ValidationIssueSeverity.ERROR,
            description = "orderDetail SHALL only be present if code is present",
            location = LocationContext(ServiceRequest::orderDetail),
        )

    override fun validateElement(
        element: ServiceRequest,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        validation.apply {
            checkTrue(element.orderDetail.isEmpty() || element.code != null, orderDetailWithNoCodeError, parentContext)
        }
    }
}
