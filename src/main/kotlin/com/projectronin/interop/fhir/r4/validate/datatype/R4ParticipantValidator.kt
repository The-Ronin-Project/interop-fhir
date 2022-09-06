package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Participant
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.ParticipantRequired
import com.projectronin.interop.fhir.r4.valueset.ParticipationStatus
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 Participant](http://hl7.org/fhir/R4/appointment-definitions.html#Appointment.participant).
 */
object R4ParticipantValidator : R4ElementContainingValidator<Participant>() {
    private val invalidRequiredError = InvalidValueSetError(Participant::required)
    private val requiredStatusError = RequiredFieldError(Participant::status)
    private val invalidStatusError = InvalidValueSetError(Participant::status)
    private val typeOrActorError = FHIRError(
        code = "R4_PRTCPNT_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "Either the type or actor on the participant SHALL be specified",
        location = LocationContext(Participant::class)
    )

    override fun validateElement(element: Participant, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.required?.let {
                checkCodedEnum<ParticipantRequired>(it, invalidRequiredError, parentContext)
            }

            checkNotNull(element.status, requiredStatusError, parentContext)

            ifNotNull(element.status) {
                checkCodedEnum<ParticipationStatus>(element.status, invalidStatusError, parentContext)
            }

            checkTrue((element.type.isNotEmpty() || element.actor != null), typeOrActorError, parentContext)
        }
    }
}
