package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.Appointment
import com.projectronin.interop.fhir.r4.resource.Participant
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.AppointmentStatus
import com.projectronin.interop.fhir.r4.valueset.ParticipantRequired
import com.projectronin.interop.fhir.r4.valueset.ParticipationStatus
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 Appointment](http://hl7.org/fhir/R4/appointment.html)
 */
object R4AppointmentValidator : R4ElementContainingValidator<Appointment>() {
    private val acceptedNullTimes =
        listOf(AppointmentStatus.PROPOSED, AppointmentStatus.CANCELLED, AppointmentStatus.WAITLIST)
    private val requiredCancelledReasons = listOf(AppointmentStatus.CANCELLED, AppointmentStatus.NOSHOW)

    private val requiredStatusError = RequiredFieldError(Appointment::status)

    private val startAndEndOrNeitherError = FHIRError(
        code = "R4_APPT_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "Either start and end are specified, or neither",
        location = LocationContext(Appointment::class)
    )
    private val startAndEndError = FHIRError(
        code = "R4_APPT_002",
        severity = ValidationIssueSeverity.ERROR,
        description = "Start and end can only be missing for appointments with the following statuses: ${acceptedNullTimes.joinToString { it.code }}",
        location = LocationContext(Appointment::class)
    )
    private val cancelationReasonError = FHIRError(
        code = "R4_APPT_003",
        severity = ValidationIssueSeverity.ERROR,
        description = "cancelationReason is only used for appointments that have the following statuses: ${requiredCancelledReasons.joinToString { it.code }}",
        location = LocationContext(Appointment::class)
    )
    private val positiveMinutesDurationError = FHIRError(
        code = "R4_APPT_004",
        severity = ValidationIssueSeverity.ERROR,
        description = "Appointment duration must be positive",
        location = LocationContext(Appointment::minutesDuration)
    )
    private val nonNegativePriorityError = FHIRError(
        code = "R4_APPT_005",
        severity = ValidationIssueSeverity.ERROR,
        description = "Priority cannot be negative",
        location = LocationContext(Appointment::priority)
    )
    private val requiredParticipantError = FHIRError(
        code = "R4_APPT_006",
        severity = ValidationIssueSeverity.ERROR,
        description = "At least one participant must be provided",
        location = LocationContext(Appointment::participant)
    )

    override fun validateElement(element: Appointment, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.status, requiredStatusError, parentContext)

            checkTrue(((element.start != null) == (element.end != null)), startAndEndOrNeitherError, parentContext)

            var codifiedStatus: AppointmentStatus? = null
            ifNotNull(element.status) {
                codifiedStatus = checkCodedEnum<AppointmentStatus>(
                    element.status,
                    InvalidValueSetError(Appointment::status, element.status.value),
                    parentContext
                )
            }

            ifNotNull(codifiedStatus) {
                if ((element.start == null) || (element.end == null)) {
                    checkTrue(acceptedNullTimes.contains(codifiedStatus), startAndEndError, parentContext)
                }

                element.cancelationReason?.let {
                    checkTrue(requiredCancelledReasons.contains(codifiedStatus), cancelationReasonError, parentContext)
                }
            }

            element.minutesDuration?.value?.let {
                checkTrue(it > 0, positiveMinutesDurationError, parentContext)
            }

            element.priority?.value?.let {
                checkTrue(it >= 0, nonNegativePriorityError, parentContext)
            }

            checkTrue(element.participant.isNotEmpty(), requiredParticipantError, parentContext)
        }
    }
}

/**
 * Validator for the [R4 Participant](http://hl7.org/fhir/R4/appointment-definitions.html#Appointment.participant).
 */
object R4ParticipantValidator : R4ElementContainingValidator<Participant>() {
    private val requiredStatusError = RequiredFieldError(Participant::status)
    private val typeOrActorError = FHIRError(
        code = "R4_PRTCPNT_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "Either the type or actor on the participant SHALL be specified",
        location = LocationContext(Participant::class)
    )

    override fun validateElement(element: Participant, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.required?.let { code ->
                checkCodedEnum<ParticipantRequired>(code, InvalidValueSetError(Participant::required, code.value), parentContext)
            }

            checkNotNull(element.status, requiredStatusError, parentContext)

            ifNotNull(element.status) {
                checkCodedEnum<ParticipationStatus>(
                    element.status,
                    InvalidValueSetError(Participant::status, element.status.value),
                    parentContext
                )
            }

            checkTrue((element.type.isNotEmpty() || element.actor != null), typeOrActorError, parentContext)
        }
    }
}