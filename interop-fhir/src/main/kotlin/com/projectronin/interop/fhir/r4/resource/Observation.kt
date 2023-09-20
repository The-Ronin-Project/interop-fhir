package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.event.interop.internal.v1.ResourceType
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Range
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.BackboneElement
import com.projectronin.interop.fhir.validate.annotation.SupportedReferenceTypes

/**
 * Measurements and simple assertions.
 *
 * See [FHIR Spec](https://www.hl7.org/fhir/observation.html)
 */
@JsonDeserialize(using = ObservationDeserializer::class)
@JsonSerialize(using = ObservationSerializer::class)
@JsonTypeName("Observation")
data class Observation(
    override val id: Id? = null,
    override var meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<Resource<*>> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: List<Identifier> = listOf(),
    @SupportedReferenceTypes(
        ResourceType.CarePlan,
        ResourceType.DeviceRequest,
        ResourceType.ImmunizationRecommendation,
        ResourceType.MedicationRequest,
        ResourceType.NutritionOrder,
        ResourceType.ServiceRequest
    )
    val basedOn: List<Reference> = listOf(),
    @SupportedReferenceTypes(
        ResourceType.MedicationAdministration,
        ResourceType.MedicationDispense,
        ResourceType.MedicationStatement,
        ResourceType.Procedure,
        ResourceType.Immunization,
        ResourceType.ImagingStudy
    )
    val partOf: List<Reference> = listOf(),
    val status: Code?,
    val category: List<CodeableConcept> = listOf(),
    val code: CodeableConcept?,
    @SupportedReferenceTypes(ResourceType.Patient, ResourceType.Group, ResourceType.Device, ResourceType.Location)
    val subject: Reference? = null,
    val focus: List<Reference> = listOf(),
    @SupportedReferenceTypes(ResourceType.Encounter)
    val encounter: Reference? = null,
    val effective: DynamicValue<Any>? = null,
    val issued: Instant? = null,
    @SupportedReferenceTypes(
        ResourceType.Practitioner,
        ResourceType.PractitionerRole,
        ResourceType.Organization,
        ResourceType.CareTeam,
        ResourceType.Patient,
        ResourceType.RelatedPerson
    )
    val performer: List<Reference> = listOf(),
    val note: List<Annotation> = listOf(),
    val value: DynamicValue<Any>? = null,
    val dataAbsentReason: CodeableConcept? = null,
    val interpretation: List<CodeableConcept> = listOf(),
    val bodySite: CodeableConcept? = null,
    val method: CodeableConcept? = null,
    @SupportedReferenceTypes(ResourceType.Specimen)
    val specimen: Reference? = null,
    @SupportedReferenceTypes(ResourceType.Device, ResourceType.DeviceMetric)
    val device: Reference? = null,
    val referenceRange: List<ObservationReferenceRange> = listOf(),
    @SupportedReferenceTypes(
        ResourceType.Observation,
        ResourceType.QuestionnaireResponse,
        ResourceType.MolecularSequence
    )
    val hasMember: List<Reference> = listOf(),
    @SupportedReferenceTypes(
        ResourceType.DocumentReference,
        ResourceType.ImagingStudy,
        ResourceType.Media,
        ResourceType.QuestionnaireResponse,
        ResourceType.Observation,
        ResourceType.MolecularSequence
    )
    val derivedFrom: List<Reference> = listOf(),
    val component: List<ObservationComponent> = listOf()
) : DomainResource<Observation> {
    override val resourceType: String = "Observation"
}

class ObservationDeserializer : BaseFHIRDeserializer<Observation>(Observation::class.java)
class ObservationSerializer : BaseFHIRSerializer<Observation>(Observation::class.java)

/**
 * An Observation.component provides component results as type of component observation (code / type) and actual component result.
 */
@JsonDeserialize(using = ObservationComponentDeserializer::class)
@JsonSerialize(using = ObservationComponentSerializer::class)
data class ObservationComponent(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val code: CodeableConcept?,
    val value: DynamicValue<Any>? = null,
    val dataAbsentReason: CodeableConcept? = null,
    val interpretation: List<CodeableConcept> = listOf(),
    val referenceRange: List<ObservationReferenceRange> = listOf()
) : BackboneElement<ObservationComponent>

class ObservationComponentDeserializer : BaseFHIRDeserializer<ObservationComponent>(ObservationComponent::class.java)
class ObservationComponentSerializer : BaseFHIRSerializer<ObservationComponent>(ObservationComponent::class.java)

/**
 * An Observation.referenceRange provides guide for interpretation.
 * Rule: Must have at least a low or a high or text.
 */
@JsonSerialize(using = ObservationReferenceRangeSerializer::class)
@JsonDeserialize(using = ObservationReferenceRangeDeserializer::class)
data class ObservationReferenceRange(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val low: SimpleQuantity? = null,
    val high: SimpleQuantity? = null,
    val type: CodeableConcept? = null,
    val appliesTo: List<CodeableConcept> = listOf(),
    val age: Range? = null,
    val text: FHIRString? = null
) : BackboneElement<ObservationReferenceRange>

class ObservationReferenceRangeSerializer :
    BaseFHIRSerializer<ObservationReferenceRange>(ObservationReferenceRange::class.java)

class ObservationReferenceRangeDeserializer :
    BaseFHIRDeserializer<ObservationReferenceRange>(ObservationReferenceRange::class.java)
