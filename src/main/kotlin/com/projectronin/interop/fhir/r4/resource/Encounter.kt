package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.Duration
import com.projectronin.interop.fhir.r4.datatype.EncounterClassHistory
import com.projectronin.interop.fhir.r4.datatype.EncounterDiagnosis
import com.projectronin.interop.fhir.r4.datatype.EncounterHospitalization
import com.projectronin.interop.fhir.r4.datatype.EncounterLocation
import com.projectronin.interop.fhir.r4.datatype.EncounterParticipant
import com.projectronin.interop.fhir.r4.datatype.EncounterStatusHistory
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

/**
 * An interaction between a patient and healthcare provider(s) for the purpose of providing healthcare service(s)
 * or assessing the health status of a patient. May be [partOf] another Encounter. Encounter [subject] is the Patient.
 * Unlike Appointment, Encounter [participant] lists only participants other than the [subject] Patient.
 *
 * See [FHIR Spec](https://www.hl7.org/fhir/R4/encounter.html)
 */
@JsonTypeName("Encounter")
data class Encounter(
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
    val statusHistory: List<EncounterStatusHistory> = listOf(),
    val `class`: Coding?,
    val classHistory: List<EncounterClassHistory> = listOf(),
    val type: List<CodeableConcept> = listOf(),
    val serviceType: CodeableConcept? = null,
    val priority: CodeableConcept? = null,
    val subject: Reference? = null,
    val episodeOfCare: List<Reference> = listOf(),
    val basedOn: List<Reference> = listOf(),
    val participant: List<EncounterParticipant> = listOf(),
    val appointment: List<Reference> = listOf(),
    val period: Period? = null,
    val length: Duration? = null,
    val reasonCode: List<CodeableConcept> = listOf(),
    val reasonReference: List<Reference> = listOf(),
    val diagnosis: List<EncounterDiagnosis> = listOf(),
    val account: List<Reference> = listOf(),
    val hospitalization: EncounterHospitalization? = null,
    val location: List<EncounterLocation> = listOf(),
    val serviceProvider: Reference? = null,
    val partOf: Reference? = null,
) : DomainResource<Encounter> {
    override val resourceType: String = "Encounter"
}
