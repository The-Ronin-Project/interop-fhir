package com.projectronin.interop.fhir.r4.resource.base

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Participant
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.ContainedResource
import com.projectronin.interop.fhir.r4.valueset.AppointmentStatus
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.validation

/**
 * Base class representing a FHIR R4 Appointment.
 *
 * See [FHIR Spec](https://www.hl7.org/fhir/R4/appointment.html)
 */
abstract class BaseAppointment {
    val resourceType: String = "Appointment"

    abstract val id: Id?
    abstract val meta: Meta?
    abstract val implicitRules: Uri?
    abstract val language: Code?
    abstract val text: Narrative?
    abstract val contained: List<ContainedResource>
    abstract val extension: List<Extension>
    abstract val modifierExtension: List<Extension>
    abstract val identifier: List<Identifier>
    abstract val status: AppointmentStatus
    abstract val cancelationReason: CodeableConcept?
    abstract val serviceCategory: List<CodeableConcept>
    abstract val serviceType: List<CodeableConcept>
    abstract val specialty: List<CodeableConcept>
    abstract val appointmentType: CodeableConcept?
    abstract val reasonCode: List<CodeableConcept>
    abstract val reasonReference: List<Reference>
    abstract val priority: Int?
    abstract val description: String?
    abstract val supportingInformation: List<Reference>
    abstract val start: Instant?
    abstract val end: Instant?
    abstract val minutesDuration: Int?
    abstract val slot: List<Reference>
    abstract val created: DateTime?
    abstract val comment: String?
    abstract val patientInstruction: String?
    abstract val basedOn: List<Reference>
    abstract val participant: List<Participant>
    abstract val requestedPeriod: List<Period>

    open fun validate(): Validation = validation {
        check(((start != null) == (end != null))) {
            "Either start and end are specified, or neither"
        }

        if ((start == null) || (end == null)) {
            check(
                listOf(
                    AppointmentStatus.PROPOSED,
                    AppointmentStatus.CANCELLED,
                    AppointmentStatus.WAITLIST
                ).contains(status)
            ) {
                "Only proposed or cancelled appointments can be missing start/end dates"
            }
        }

        cancelationReason?.let {
            check(listOf(AppointmentStatus.CANCELLED, AppointmentStatus.NOSHOW).contains(status)) {
                "cancelationReason is only used for appointments that have been cancelled, or no-show"
            }
        }

        minutesDuration?.let {
            check(it > 0) {
                "Appointment duration must be positive"
            }
        }

        priority?.let {
            check(it >= 0) {
                "Priority cannot be negative"
            }
        }

        check(participant.isNotEmpty()) {
            "At least one participant must be provided"
        }
    }
}
