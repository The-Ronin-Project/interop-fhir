package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.PractitionerRole
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 PractitionerRole](http://hl7.org/fhir/R4/practitionerrole.html)
 */
object R4PractitionerRoleValidator : R4ElementContainingValidator<PractitionerRole>() {
    override fun validateElement(element: PractitionerRole, parentContext: LocationContext?, validation: Validation) {
        // PractitionerRole has no special Validation logic, but it should still evaluate its annotations and contained elements.
    }
}
