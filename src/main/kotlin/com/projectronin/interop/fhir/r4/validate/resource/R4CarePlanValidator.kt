package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.CarePlan
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.CarePlanIntent
import com.projectronin.interop.fhir.r4.valueset.RequestStatus
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

object R4CarePlanValidator : R4ElementContainingValidator<CarePlan>() {

    private val requiredIntentError = RequiredFieldError(CarePlan::intent)
    private val requiredStatusError = RequiredFieldError(CarePlan::status)
    private val requiredSubjectError = RequiredFieldError(CarePlan::subject)

    override fun validateElement(element: CarePlan, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.intent, requiredIntentError, parentContext)
            ifNotNull(element.intent) {
                checkCodedEnum<CarePlanIntent>(
                    element.intent,
                    InvalidValueSetError(CarePlan::intent, element.intent.value),
                    parentContext
                )
            }

            checkNotNull(element.status, requiredStatusError, parentContext)
            ifNotNull(element.status) {
                checkCodedEnum<RequestStatus>(
                    element.status,
                    InvalidValueSetError(CarePlan::status, element.status.value),
                    parentContext
                )
            }

            checkNotNull(element.subject, requiredSubjectError, parentContext)
        }
    }
}
