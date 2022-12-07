package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.ContactPoint
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.ContactPointSystem
import com.projectronin.interop.fhir.r4.valueset.ContactPointUse
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 ContactPoint](http://hl7.org/fhir/R4/datatypes.html#ContactPoint)
 */
object R4ContactPointValidator : R4ElementContainingValidator<ContactPoint>() {
    private val requiredSystemError = FHIRError(
        code = "R4_CNTCTPT_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "A system is required if a value is provided",
        location = LocationContext(ContactPoint::class)
    )

    override fun validateElement(element: ContactPoint, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.system?.let { code ->
                checkCodedEnum<ContactPointSystem>(
                    code,
                    InvalidValueSetError(ContactPoint::system, code.value),
                    parentContext
                )
            }

            element.use?.let { code ->
                checkCodedEnum<ContactPointUse>(
                    code,
                    InvalidValueSetError(ContactPoint::use, code.value),
                    parentContext
                )
            }

            checkTrue((element.system != null || element.value == null), requiredSystemError, parentContext)
        }
    }
}
