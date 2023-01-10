package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.Duration
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.BackboneElement

/**
 * An interaction between a patient and healthcare provider(s) for the purpose of providing healthcare service(s)
 * or assessing the health status of a patient. May be [partOf] another Encounter. Encounter [subject] is the Patient.
 * Unlike Appointment, Encounter [participant] lists only participants other than the [subject] Patient.
 *
 * See [FHIR Spec](https://www.hl7.org/fhir/R4/encounter.html)
 */
@JsonSerialize(using = EncounterSerializer::class)
@JsonDeserialize(using = EncounterDeserializer::class)
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

class EncounterSerializer : BaseFHIRSerializer<Encounter>(Encounter::class.java)
class EncounterDeserializer : BaseFHIRDeserializer<Encounter>(Encounter::class.java)

/**
 * List of past encounter classes.
 * See [R4 Encounter.classHistory](https://hl7.org/fhir/R4/encounter-definitions.html#Encounter.classHistory).
 */
@JsonSerialize(using = EncounterClassHistorySerializer::class)
@JsonDeserialize(using = EncounterClassHistoryDeserializer::class)
data class EncounterClassHistory(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val `class`: Code?,
    val period: Period?,
) : BackboneElement<EncounterClassHistory>

class EncounterClassHistorySerializer : BaseFHIRSerializer<EncounterClassHistory>(EncounterClassHistory::class.java)
class EncounterClassHistoryDeserializer : BaseFHIRDeserializer<EncounterClassHistory>(EncounterClassHistory::class.java)

/**
 * List of diagnoses relevant to this encounter.
 * See [R4 Encounter.diagnosis](https://hl7.org/fhir/R4/encounter-definitions.html#Encounter.diagnosis).
 */
@JsonSerialize(using = EncounterDiagnosisSerializer::class)
@JsonDeserialize(using = EncounterDiagnosisDeserializer::class)
data class EncounterDiagnosis(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val condition: Reference?,
    val use: CodeableConcept? = null,
    val rank: PositiveInt? = null,
) : BackboneElement<EncounterDiagnosis>

class EncounterDiagnosisSerializer : BaseFHIRSerializer<EncounterDiagnosis>(EncounterDiagnosis::class.java)
class EncounterDiagnosisDeserializer : BaseFHIRDeserializer<EncounterDiagnosis>(EncounterDiagnosis::class.java)

/**
 * Details about the admission to a healthcare service.
 * See [R4 Encounter.hospitalization](https://hl7.org/fhir/R4/encounter-definitions.html#Encounter.hospitalization).
 */
@JsonSerialize(using = EncounterHospitalizationSerializer::class)
@JsonDeserialize(using = EncounterHospitalizationDeserializer::class)
data class EncounterHospitalization(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val preAdmissionIdentifier: Identifier? = null,
    val origin: Reference? = null,
    val admitSource: CodeableConcept? = null,
    val reAdmission: CodeableConcept? = null,
    val dietPreference: List<CodeableConcept> = listOf(),
    val specialCourtesy: List<CodeableConcept> = listOf(),
    val specialArrangement: List<CodeableConcept> = listOf(),
    val destination: Reference? = null,
    val dischargeDisposition: CodeableConcept? = null,
) : BackboneElement<EncounterHospitalization>

class EncounterHospitalizationSerializer :
    BaseFHIRSerializer<EncounterHospitalization>(EncounterHospitalization::class.java)

class EncounterHospitalizationDeserializer :
    BaseFHIRDeserializer<EncounterHospitalization>(EncounterHospitalization::class.java)

/**
 * List of locations where the patient has been.
 * See [R4 Encounter.location](https://hl7.org/fhir/R4/encounter-definitions.html#Encounter.location).
 */
@JsonSerialize(using = EncounterLocationSerializer::class)
@JsonDeserialize(using = EncounterLocationDeserializer::class)
data class EncounterLocation(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val location: Reference?,
    val status: Code? = null,
    val physicalType: CodeableConcept? = null,
    val period: Period? = null,
) : BackboneElement<EncounterLocation>

class EncounterLocationSerializer : BaseFHIRSerializer<EncounterLocation>(EncounterLocation::class.java)
class EncounterLocationDeserializer : BaseFHIRDeserializer<EncounterLocation>(EncounterLocation::class.java)

/**
 * List of participants involved in the encounter.
 * See [R4 Encounter.participant](https://hl7.org/fhir/R4/encounter-definitions.html#Encounter.participant).
 */
@JsonSerialize(using = EncounterParticipantSerializer::class)
@JsonDeserialize(using = EncounterParticipantDeserializer::class)
data class EncounterParticipant(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val type: List<CodeableConcept> = listOf(),
    val period: Period? = null,
    val individual: Reference? = null,
) : BackboneElement<EncounterParticipant>

class EncounterParticipantSerializer : BaseFHIRSerializer<EncounterParticipant>(EncounterParticipant::class.java)
class EncounterParticipantDeserializer : BaseFHIRDeserializer<EncounterParticipant>(EncounterParticipant::class.java)

/**
 * List of past encounter statuses.
 * See [R4 Encounter.statusHistory](https://hl7.org/fhir/R4/encounter-definitions.html#Encounter.statusHistory).
 */
@JsonSerialize(using = EncounterStatusHistorySerializer::class)
@JsonDeserialize(using = EncounterStatusHistoryDeserializer::class)
data class EncounterStatusHistory(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val status: Code?,
    val period: Period?,
) : BackboneElement<EncounterStatusHistory>

class EncounterStatusHistorySerializer : BaseFHIRSerializer<EncounterStatusHistory>(EncounterStatusHistory::class.java)
class EncounterStatusHistoryDeserializer :
    BaseFHIRDeserializer<EncounterStatusHistory>(EncounterStatusHistory::class.java)
