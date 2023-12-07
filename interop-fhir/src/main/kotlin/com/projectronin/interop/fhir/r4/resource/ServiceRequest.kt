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
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.RequestIntent
import com.projectronin.interop.fhir.r4.valueset.RequestPriority
import com.projectronin.interop.fhir.r4.valueset.RequestStatus
import com.projectronin.interop.fhir.validate.annotation.RequiredField
import com.projectronin.interop.fhir.validate.annotation.RequiredValueSet
import com.projectronin.interop.fhir.validate.annotation.SupportedDynamicValueTypes
import com.projectronin.interop.fhir.validate.annotation.SupportedReferenceTypes

/**
 * A record of a request for service such as diagnostic investigations, treatments, or operations to be performed.
 */
@JsonSerialize(using = ServiceRequestSerializer::class)
@JsonDeserialize(using = ServiceRequestDeserializer::class)
@JsonTypeName("ServiceRequest")
data class ServiceRequest(
    override val id: Id? = null,
    override var meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<Resource<*>> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: List<Identifier> = listOf(),
    val instantiatesCanonical: List<Canonical> = listOf(),
    val instantiatesUri: List<Uri> = listOf(),
    @SupportedReferenceTypes(ResourceType.CarePlan, ResourceType.ServiceRequest, ResourceType.MedicationRequest)
    val basedOn: List<Reference> = listOf(),
    @SupportedReferenceTypes(ResourceType.ServiceRequest)
    val replaces: List<Reference> = listOf(),
    val requisition: Identifier? = null,
    @RequiredField
    @RequiredValueSet(RequestStatus::class)
    val status: Code?,
    @RequiredField
    @RequiredValueSet(RequestIntent::class)
    val intent: Code?,
    val category: List<CodeableConcept> = listOf(),
    @RequiredValueSet(RequestPriority::class)
    val priority: Code? = null,
    val doNotPerform: FHIRBoolean? = null,
    val code: CodeableConcept? = null,
    val orderDetail: List<CodeableConcept> = listOf(),
    @SupportedDynamicValueTypes(DynamicValueType.QUANTITY, DynamicValueType.RATIO, DynamicValueType.RANGE)
    val quantity: DynamicValue<Any>? = null,
    @RequiredField
    @SupportedReferenceTypes(ResourceType.Patient, ResourceType.Group, ResourceType.Location, ResourceType.Device)
    val subject: Reference?,
    @SupportedReferenceTypes(ResourceType.Encounter)
    val encounter: Reference? = null,
    @SupportedDynamicValueTypes(DynamicValueType.DATE_TIME, DynamicValueType.PERIOD, DynamicValueType.TIMING)
    val occurrence: DynamicValue<Any>? = null,
    @SupportedDynamicValueTypes(DynamicValueType.BOOLEAN, DynamicValueType.CODEABLE_CONCEPT)
    val asNeeded: DynamicValue<Any>? = null,
    val authoredOn: DateTime? = null,
    @SupportedReferenceTypes(
        ResourceType.Practitioner,
        ResourceType.PractitionerRole,
        ResourceType.Organization,
        ResourceType.Patient,
        ResourceType.RelatedPerson,
        ResourceType.Device,
    )
    val requester: Reference? = null,
    val performerType: CodeableConcept? = null,
    @SupportedReferenceTypes(
        ResourceType.Practitioner,
        ResourceType.PractitionerRole,
        ResourceType.Organization,
        ResourceType.CareTeam,
        ResourceType.HealthcareService,
        ResourceType.Patient,
        ResourceType.Device,
        ResourceType.RelatedPerson,
    )
    val performer: List<Reference> = listOf(),
    val locationCode: List<CodeableConcept> = listOf(),
    @SupportedReferenceTypes(ResourceType.Location)
    val locationReference: List<Reference> = listOf(),
    val reasonCode: List<CodeableConcept> = listOf(),
    @SupportedReferenceTypes(
        ResourceType.Condition,
        ResourceType.Observation,
        ResourceType.DiagnosticReport,
        ResourceType.DocumentReference,
    )
    val reasonReference: List<Reference> = listOf(),
    @SupportedReferenceTypes(ResourceType.Coverage, ResourceType.ClaimResponse)
    val insurance: List<Reference> = listOf(),
    val supportingInfo: List<Reference> = listOf(),
    @SupportedReferenceTypes(ResourceType.Specimen)
    val specimen: List<Reference> = listOf(),
    val bodySite: List<CodeableConcept> = listOf(),
    val note: List<Annotation> = listOf(),
    val patientInstruction: FHIRString? = null,
    @SupportedReferenceTypes(ResourceType.Provenance)
    val relevantHistory: List<Reference> = listOf(),
) : DomainResource<ServiceRequest> {
    override val resourceType: String = "ServiceRequest"
}

class ServiceRequestSerializer : BaseFHIRSerializer<ServiceRequest>(ServiceRequest::class.java)

class ServiceRequestDeserializer : BaseFHIRDeserializer<ServiceRequest>(ServiceRequest::class.java)
