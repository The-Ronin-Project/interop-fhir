package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.BackboneElement

/**
 * A booking of a healthcare event among patient(s), practitioner(s), related person(s) and/or device(s) for a specific
 * date/time. This may result in one or more Encounter(s).
 *
 * See [FHIR Spec](https://www.hl7.org/fhir/R4/appointment.html)
 */
@JsonSerialize(using = AppointmentSerializer::class)
@JsonDeserialize(using = AppointmentDeserializer::class)
@JsonTypeName("Appointment")
data class Appointment(
    override val id: Id? = null,
    override var meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<ContainedResource> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: List<Identifier> = listOf(),
    val status: Code?,
    val cancelationReason: CodeableConcept? = null,
    val serviceCategory: List<CodeableConcept> = listOf(),
    val serviceType: List<CodeableConcept> = listOf(),
    val specialty: List<CodeableConcept> = listOf(),
    val appointmentType: CodeableConcept? = null,
    val reasonCode: List<CodeableConcept> = listOf(),
    val reasonReference: List<Reference> = listOf(),
    val priority: FHIRInteger? = null,
    val description: FHIRString? = null,
    val supportingInformation: List<Reference> = listOf(),
    val start: Instant? = null,
    val end: Instant? = null,
    val minutesDuration: FHIRInteger? = null,
    val slot: List<Reference> = listOf(),
    val created: DateTime? = null,
    val comment: FHIRString? = null,
    val patientInstruction: FHIRString? = null,
    val basedOn: List<Reference> = listOf(),
    val participant: List<Participant>,
    val requestedPeriod: List<Period> = listOf()
) : DomainResource<Appointment> {
    override val resourceType: String = "Appointment"
}

class AppointmentSerializer : BaseFHIRSerializer<Appointment>(Appointment::class.java)
class AppointmentDeserializer : BaseFHIRDeserializer<Appointment>(Appointment::class.java)

/**
 * List of participants involved in the appointment.
 */
@JsonSerialize(using = ParticipantSerializer::class)
@JsonDeserialize(using = ParticipantDeserializer::class)
data class Participant(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val type: List<CodeableConcept> = listOf(),
    val actor: Reference? = null,
    val required: Code? = null,
    val status: Code?,
    val period: Period? = null
) : BackboneElement<Participant>

class ParticipantSerializer : BaseFHIRSerializer<Participant>(Participant::class.java)
class ParticipantDeserializer : BaseFHIRDeserializer<Participant>(Participant::class.java)
