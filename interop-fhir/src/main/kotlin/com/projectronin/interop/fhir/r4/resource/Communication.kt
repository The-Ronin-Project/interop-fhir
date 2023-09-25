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
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.BackboneElement
import com.projectronin.interop.fhir.r4.valueset.EventStatus
import com.projectronin.interop.fhir.r4.valueset.RequestPriority
import com.projectronin.interop.fhir.validate.annotation.RequiredField
import com.projectronin.interop.fhir.validate.annotation.RequiredValueSet
import com.projectronin.interop.fhir.validate.annotation.SupportedDynamicValueTypes
import com.projectronin.interop.fhir.validate.annotation.SupportedReferenceTypes

@JsonSerialize(using = CommunicationSerializer::class)
@JsonDeserialize(using = CommunicationDeserializer::class)
@JsonTypeName("Communication")
data class Communication(
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
    val basedOn: List<Reference> = listOf(),
    val partOf: List<Reference> = listOf(),
    @SupportedReferenceTypes(ResourceType.Communication)
    val inResponseTo: List<Reference> = listOf(),
    @RequiredField
    @RequiredValueSet(EventStatus::class)
    val status: Code?,
    val statusReason: CodeableConcept? = null,
    val category: List<CodeableConcept> = listOf(),
    @RequiredValueSet(RequestPriority::class)
    val priority: Code? = null,
    val medium: List<CodeableConcept> = listOf(),
    @SupportedReferenceTypes(ResourceType.Patient, ResourceType.Group)
    val subject: Reference? = null,
    val topic: CodeableConcept? = null,
    val about: List<Reference> = listOf(),
    @SupportedReferenceTypes(ResourceType.Encounter)
    val encounter: Reference? = null,
    val sent: DateTime? = null,
    val received: DateTime? = null,
    @SupportedReferenceTypes(
        ResourceType.Device,
        ResourceType.Organization,
        ResourceType.Patient,
        ResourceType.Practitioner,
        ResourceType.PractitionerRole,
        ResourceType.RelatedPerson,
        ResourceType.Group,
        ResourceType.CareTeam,
        ResourceType.HealthcareService
    )
    val recipient: List<Reference> = listOf(),
    @SupportedReferenceTypes(
        ResourceType.Device,
        ResourceType.Organization,
        ResourceType.Patient,
        ResourceType.Practitioner,
        ResourceType.PractitionerRole,
        ResourceType.RelatedPerson,
        ResourceType.HealthcareService
    )
    val sender: Reference? = null,
    val reasonCode: List<CodeableConcept> = listOf(),
    @SupportedReferenceTypes(
        ResourceType.Condition,
        ResourceType.Observation,
        ResourceType.DiagnosticReport,
        ResourceType.DocumentReference
    )
    val reasonReference: List<Reference> = listOf(),
    val payload: List<CommunicationPayload> = listOf(),
    val note: List<Annotation> = listOf()
) : DomainResource<Communication> {
    override val resourceType: String = "Communication"
}

class CommunicationSerializer : BaseFHIRSerializer<Communication>(Communication::class.java)
class CommunicationDeserializer : BaseFHIRDeserializer<Communication>(Communication::class.java)

@JsonSerialize(using = CommunicationPayloadSerializer::class)
@JsonDeserialize(using = CommunicationPayloadDeserializer::class)
data class CommunicationPayload(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    @RequiredField
    @SupportedDynamicValueTypes(DynamicValueType.STRING, DynamicValueType.ATTACHMENT, DynamicValueType.REFERENCE)
    val content: DynamicValue<Any>?
) : BackboneElement<CommunicationPayload>

class CommunicationPayloadSerializer : BaseFHIRSerializer<CommunicationPayload>(CommunicationPayload::class.java)
class CommunicationPayloadDeserializer : BaseFHIRDeserializer<CommunicationPayload>(CommunicationPayload::class.java)
