package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.IdentifierUse
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 Identifier](http://hl7.org/fhir/R4/datatypes.html#Identifier).
 */
object R4IdentifierValidator : R4ElementContainingValidator<Identifier>() {
    private val invalidUseError = InvalidValueSetError(Identifier::use)
    private val invalidAssignerError = FHIRError(
        code = "R4_IDENT_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "Identifier assigner reference must be to an Organization",
        location = LocationContext("Identifier", "assigner.type")
    )

    override fun validateElement(element: Identifier, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.use?.let {
                checkCodedEnum<IdentifierUse>(it, invalidUseError, parentContext)
            }

            element.assigner?.type?.let {
                checkTrue(it.value == "Organization", invalidAssignerError, parentContext)
            }
        }
    }
}
