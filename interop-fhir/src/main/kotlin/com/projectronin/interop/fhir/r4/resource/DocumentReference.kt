package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.event.interop.internal.v1.ResourceType
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.Attachment
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.BackboneElement
import com.projectronin.interop.fhir.validate.annotation.RequiredField
import com.projectronin.interop.fhir.validate.annotation.SupportedReferenceTypes

/**
 * A reference to a document of any kind for any purpose. Provides metadata about the document so that the document can
 * be discovered and managed. The scope of a document is any seralized object with a mime-type, so includes formal patient
 * centric documents (CDA), cliical notes, scanned paper, and non-patient specific documents like policy text.
 */
@JsonSerialize(using = DocumentReferenceSerializer::class)
@JsonDeserialize(using = DocumentReferenceDeserializer::class)
@JsonTypeName("DocumentReference")
data class DocumentReference(
    override val id: Id? = null,
    override var meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<Resource<*>> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val masterIdentifier: Identifier? = null,
    val identifier: List<Identifier> = listOf(),
    @RequiredField
    val status: Code?,
    val docStatus: Code? = null,
    val type: CodeableConcept? = null,
    val category: List<CodeableConcept> = listOf(),
    @SupportedReferenceTypes(ResourceType.Patient, ResourceType.Practitioner, ResourceType.Group, ResourceType.Device)
    val subject: Reference? = null,
    val date: Instant? = null,
    @SupportedReferenceTypes(
        ResourceType.Practitioner,
        ResourceType.PractitionerRole,
        ResourceType.Organization,
        ResourceType.Device,
        ResourceType.Patient,
        ResourceType.RelatedPerson
    )
    val author: List<Reference> = listOf(),
    @SupportedReferenceTypes(ResourceType.Practitioner, ResourceType.PractitionerRole, ResourceType.Organization)
    val authenticator: Reference? = null,
    @SupportedReferenceTypes(ResourceType.Organization)
    val custodian: Reference? = null,
    val relatesTo: List<DocumentReferenceRelatesTo> = listOf(),
    val description: FHIRString? = null,
    val securityLabel: List<CodeableConcept> = listOf(),
    @RequiredField
    val content: List<DocumentReferenceContent> = listOf(),
    val context: DocumentReferenceContext? = null
) : DomainResource<DocumentReference> {
    override val resourceType: String = "DocumentReference"
}

class DocumentReferenceSerializer : BaseFHIRSerializer<DocumentReference>(DocumentReference::class.java)
class DocumentReferenceDeserializer : BaseFHIRDeserializer<DocumentReference>(DocumentReference::class.java)

/**
 * Relationships to other documents
 */
@JsonSerialize(using = DocumentReferenceRelatesToSerializer::class)
@JsonDeserialize(using = DocumentReferenceRelatesToDeserializer::class)
data class DocumentReferenceRelatesTo(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    @RequiredField
    val code: Code?,
    @RequiredField
    @SupportedReferenceTypes(ResourceType.DocumentReference)
    val target: Reference?
) : BackboneElement<DocumentReferenceRelatesTo>

class DocumentReferenceRelatesToSerializer :
    BaseFHIRSerializer<DocumentReferenceRelatesTo>(DocumentReferenceRelatesTo::class.java)

class DocumentReferenceRelatesToDeserializer :
    BaseFHIRDeserializer<DocumentReferenceRelatesTo>(DocumentReferenceRelatesTo::class.java)

/**
 * Document referenced
 */
@JsonSerialize(using = DocumentReferenceContentSerializer::class)
@JsonDeserialize(using = DocumentReferenceContentDeserializer::class)
data class DocumentReferenceContent(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    @RequiredField
    val attachment: Attachment?,
    val format: Coding? = null
) : BackboneElement<DocumentReferenceContent>

class DocumentReferenceContentSerializer :
    BaseFHIRSerializer<DocumentReferenceContent>(DocumentReferenceContent::class.java)

class DocumentReferenceContentDeserializer :
    BaseFHIRDeserializer<DocumentReferenceContent>(DocumentReferenceContent::class.java)

/**
 * Clinical context of document
 */
@JsonSerialize(using = DocumentReferenceContextSerializer::class)
@JsonDeserialize(using = DocumentReferenceContextDeserializer::class)
data class DocumentReferenceContext(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    @SupportedReferenceTypes(ResourceType.Encounter, ResourceType.EpisodeOfCare)
    val encounter: List<Reference> = listOf(),
    val event: List<CodeableConcept> = listOf(),
    val period: Period? = null,
    val facilityType: CodeableConcept? = null,
    val practiceSetting: CodeableConcept? = null,
    @SupportedReferenceTypes(ResourceType.Patient)
    val sourcePatientInfo: Reference? = null,
    val related: List<Reference> = listOf()
) : BackboneElement<DocumentReferenceContext>

class DocumentReferenceContextSerializer :
    BaseFHIRSerializer<DocumentReferenceContext>(DocumentReferenceContext::class.java)

class DocumentReferenceContextDeserializer :
    BaseFHIRDeserializer<DocumentReferenceContext>(DocumentReferenceContext::class.java)
