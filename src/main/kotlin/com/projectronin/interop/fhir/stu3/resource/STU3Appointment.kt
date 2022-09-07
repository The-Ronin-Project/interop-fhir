package com.projectronin.interop.fhir.stu3.resource

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
import com.projectronin.interop.fhir.r4.resource.ContainedResource
import com.projectronin.interop.fhir.r4.resource.Appointment as R4Appointment

/**
 * A booking of a healthcare event among patient(s), practitioner(s), related person(s) and/or device(s) for a specific
 * date/time. This may result in one or more Encounter(s).
 *
 * See [FHIR Spec](https://hl7.org/fhir/STU3/appointment.html)
 */
@JsonTypeName("Appointment")
data class STU3Appointment(
    override val id: Id? = null,
    override val meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<ContainedResource> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: List<Identifier> = listOf(),
    val reason: List<CodeableConcept> = listOf(),
    val indication: List<Reference> = listOf(),
    val incomingReferral: List<Reference> = listOf(),
    val status: Code?,
    val serviceCategory: CodeableConcept? = null,
    val serviceType: List<CodeableConcept> = listOf(),
    val specialty: List<CodeableConcept> = listOf(),
    val appointmentType: CodeableConcept? = null,
    val priority: Int? = null,
    val description: String? = null,
    val supportingInformation: List<Reference> = listOf(),
    val start: Instant? = null,
    val end: Instant? = null,
    val minutesDuration: Int? = null,
    val slot: List<Reference> = listOf(),
    val created: DateTime? = null,
    val comment: String? = null,
    val participant: List<Participant>,
    val requestedPeriod: List<Period> = listOf()
) : STU3DomainResource<STU3Appointment> {
    override val resourceType: String = "Appointment"

    override fun transformToR4(): R4Appointment {
        return R4Appointment(
            identifier = identifier,
            id = id,
            meta = meta,
            implicitRules = implicitRules,
            language = language,
            text = text,
            contained = contained,
            extension = extension,
            modifierExtension = modifierExtension,
            status = status,
            serviceCategory = serviceCategory?.let { listOf(serviceCategory) } ?: listOf(),
            specialty = specialty,
            appointmentType = appointmentType,
            priority = priority,
            description = description,
            supportingInformation = supportingInformation,
            start = start,
            end = end,
            minutesDuration = minutesDuration,
            slot = slot,
            created = created,
            comment = comment,
            participant = participant,
            requestedPeriod = requestedPeriod,
            reasonCode = reason,
            reasonReference = indication,
            basedOn = incomingReferral
        )
    }
}