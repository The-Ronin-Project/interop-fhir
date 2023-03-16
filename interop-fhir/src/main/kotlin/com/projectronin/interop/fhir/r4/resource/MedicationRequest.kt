package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Dosage
import com.projectronin.interop.fhir.r4.datatype.Duration
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.Element

/**
 * An order or request for both supply of the medication and the instructions for administration of the medication to a
 * patient. The resource is called "MedicationRequest" rather than "MedicationPrescription" or "MedicationOrder" to
 * generalize the use across inpatient and outpatient settings, including care plans, etc., and to harmonize with
 * workflow patterns.
 */
@JsonDeserialize(using = MedicationRequestDeserializer::class)
@JsonSerialize(using = MedicationRequestSerializer::class)
@JsonTypeName("MedicationRequest")
data class MedicationRequest(
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
    val statusReason: CodeableConcept? = null,
    val intent: Code?,
    val category: List<CodeableConcept> = listOf(),
    val priority: Code? = null,
    val doNotPerform: FHIRBoolean? = null,
    val reported: DynamicValue<Any>? = null,
    val medication: DynamicValue<Any>?,
    val subject: Reference?,
    val encounter: Reference? = null,
    val supportingInformation: List<Reference> = listOf(),
    val authoredOn: DateTime? = null,
    val requester: Reference? = null,
    val performer: Reference? = null,
    val performerType: CodeableConcept? = null,
    val recorder: Reference? = null,
    val reasonCode: List<CodeableConcept> = listOf(),
    val reasonReference: List<Reference> = listOf(),
    val instantiatesCanonical: List<Canonical> = listOf(),
    val instantiatesUri: List<Uri> = listOf(),
    val basedOn: List<Reference> = listOf(),
    val groupIdentifier: Identifier? = null,
    val courseOfTherapyType: CodeableConcept? = null,
    val insurance: List<Reference> = listOf(),
    val note: List<Annotation> = listOf(),
    val dosageInformation: List<Dosage> = listOf(),
    val dispenseRequest: DispenseRequest? = null,
    val substitution: Substitution? = null,
    val priorPrescription: Reference? = null,
    val detectedIssue: List<Reference> = listOf(),
    val eventHistory: List<Reference> = listOf()
) : DomainResource<MedicationRequest> {
    override val resourceType: String = "MedicationRequest"
}

class MedicationRequestSerializer : BaseFHIRSerializer<MedicationRequest>(MedicationRequest::class.java)
class MedicationRequestDeserializer : BaseFHIRDeserializer<MedicationRequest>(MedicationRequest::class.java)

/**
 * Medication supply authorization
 */
@JsonDeserialize(using = DispenseRequestDeserializer::class)
@JsonSerialize(using = DispenseRequestSerializer::class)
data class DispenseRequest(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val initialFill: InitialFill? = null,
    val dispenseInterval: Duration? = null,
    val validityPeriod: Period? = null,
    val numberOfRepeatsAllowed: UnsignedInt? = null,
    val quantity: SimpleQuantity? = null,
    val expectedSupplyDuration: Duration? = null,
    val performer: Reference? = null
) : Element<DispenseRequest>

class DispenseRequestSerializer : BaseFHIRSerializer<DispenseRequest>(DispenseRequest::class.java)
class DispenseRequestDeserializer : BaseFHIRDeserializer<DispenseRequest>(DispenseRequest::class.java)

/**
 * First fill details
 */
@JsonDeserialize(using = InitialFillDeserializer::class)
@JsonSerialize(using = InitialFillSerializer::class)
data class InitialFill(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val quantity: SimpleQuantity? = null,
    val duration: Duration? = null
) : Element<InitialFill>

class InitialFillSerializer : BaseFHIRSerializer<InitialFill>(InitialFill::class.java)
class InitialFillDeserializer : BaseFHIRDeserializer<InitialFill>(InitialFill::class.java)

/**
 * Any restrictions on medication substitution
 */
@JsonDeserialize(using = SubstitutionDeserializer::class)
@JsonSerialize(using = SubstitutionSerializer::class)
data class Substitution(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val allowed: DynamicValue<Any>?,
    val reason: CodeableConcept? = null
) : Element<Substitution>

class SubstitutionSerializer : BaseFHIRSerializer<Substitution>(Substitution::class.java)
class SubstitutionDeserializer : BaseFHIRDeserializer<Substitution>(Substitution::class.java)
