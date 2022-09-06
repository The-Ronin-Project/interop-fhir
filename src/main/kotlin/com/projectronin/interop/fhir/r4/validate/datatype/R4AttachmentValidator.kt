package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Attachment
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 Attachment](http://hl7.org/fhir/R4/datatypes.html#Attachment).
 */
object R4AttachmentValidator : R4ElementContainingValidator<Attachment>() {
    private val requiredContentTypeError = FHIRError(
        code = "R4_ATTACH_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "If the Attachment has data, it SHALL have a contentType",
        location = LocationContext(Attachment::class)
    )

    override fun validateElement(element: Attachment, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkTrue((element.data == null || element.contentType != null), requiredContentTypeError, parentContext)
        }
    }
}
