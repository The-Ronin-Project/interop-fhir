package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
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

/**
 * A booking of a healthcare event among patient(s), practitioner(s), related person(s) and/or device(s) for a specific
 * date/time. This may result in one or more Encounter(s).
 *
 * See [FHIR Spec](https://www.hl7.org/fhir/R4/appointment.html)
 */
@JsonTypeName("Appointment")
data class Appointment(
    override val id: Id? = null,
    override val meta: Meta? = null,
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
    val priority: Int? = null,
    val description: String? = null,
    val supportingInformation: List<Reference> = listOf(),
    val start: Instant? = null,
    val end: Instant? = null,
    val minutesDuration: Int? = null,
    val slot: List<Reference> = listOf(),
    val created: DateTime? = null,
    val comment: String? = null,
    val patientInstruction: String? = null,
    val basedOn: List<Reference> = listOf(),
    val participant: List<Participant>,
    val requestedPeriod: List<Period> = listOf()
) : DomainResource<Appointment> {
    override val resourceType: String = "Appointment"
}
