package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.common.enums.CodedEnum
import com.projectronin.interop.fhir.r4.resource.Appointment
import com.projectronin.interop.fhir.r4.resource.Participant
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.AppointmentStatus
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 Appointment](http://hl7.org/fhir/R4/appointment.html)
 */
object R4AppointmentValidator : R4ElementContainingValidator<Appointment>() {
    private val acceptedNullTimes =
        listOf(AppointmentStatus.PROPOSED, AppointmentStatus.CANCELLED, AppointmentStatus.WAITLIST)
    private val requiredCancelledReasons = listOf(AppointmentStatus.CANCELLED, AppointmentStatus.NOSHOW)

    private val startAndEndOrNeitherError =
        FHIRError(
            code = "R4_APPT_001",
            severity = ValidationIssueSeverity.ERROR,
            description = "Either start and end are specified, or neither",
            location = LocationContext(Appointment::class),
        )

    @Suppress("ktlint:standard:max-line-length")
    private val startAndEndError =
        FHIRError(
            code = "R4_APPT_002",
            severity = ValidationIssueSeverity.ERROR,
            description = "Start and end can only be missing for appointments with the following statuses: ${acceptedNullTimes.joinToString { it.code }}",
            location = LocationContext(Appointment::class),
        )

    @Suppress("ktlint:standard:max-line-length")
    private val cancelationReasonError =
        FHIRError(
            code = "R4_APPT_003",
            severity = ValidationIssueSeverity.ERROR,
            description = "cancelationReason is only used for appointments that have the following statuses: ${requiredCancelledReasons.joinToString { it.code }}",
            location = LocationContext(Appointment::class),
        )

    override fun validateElement(
        element: Appointment,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        validation.apply {
            checkTrue(((element.start != null) == (element.end != null)), startAndEndOrNeitherError, parentContext)

            val codifiedStatus = element.status?.value?.let { CodedEnum.byCode<AppointmentStatus>(it) }

            ifNotNull(codifiedStatus) {
                if ((element.start == null) || (element.end == null)) {
                    checkTrue(acceptedNullTimes.contains(codifiedStatus), startAndEndError, parentContext)
                }

                element.cancelationReason?.let {
                    checkTrue(requiredCancelledReasons.contains(codifiedStatus), cancelationReasonError, parentContext)
                }
            }
        }
    }
}

/**
 * Validator for the [R4 Participant](http://hl7.org/fhir/R4/appointment-definitions.html#Appointment.participant).
 */
object R4ParticipantValidator : R4ElementContainingValidator<Participant>() {
    private val typeOrActorError =
        FHIRError(
            code = "R4_PRTCPNT_001",
            severity = ValidationIssueSeverity.ERROR,
            description = "Either the type or actor on the participant SHALL be specified",
            location = LocationContext(Participant::class),
        )

    override fun validateElement(
        element: Participant,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        validation.apply {
            checkTrue((element.type.isNotEmpty() || element.actor != null), typeOrActorError, parentContext)
        }
    }
}
