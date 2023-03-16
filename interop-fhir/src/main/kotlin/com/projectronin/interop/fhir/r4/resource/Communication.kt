package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
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

@JsonSerialize(using = CommunicationSerializer::class)
@JsonDeserialize(using = CommunicationDeserializer::class)
@JsonTypeName("Communication")
data class Communication(
    override val id: Id? = null,
    override val meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<ContainedResource> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: List<Identifier> = listOf(),
    val instantiatesCanonical: List<Canonical> = listOf(),
    val instantiatesUri: List<Uri> = listOf(),
    val basedOn: List<Reference> = listOf(),
    val partOf: List<Reference> = listOf(),
    val inResponseTo: List<Reference> = listOf(),
    val status: Code?,
    val statusReason: CodeableConcept? = null,
    val category: List<CodeableConcept> = listOf(),
    val priority: Code? = null,
    val medium: List<CodeableConcept> = listOf(),
    val subject: Reference? = null,
    val topic: CodeableConcept? = null,
    val about: List<Reference> = listOf(),
    val encounter: Reference? = null,
    val sent: DateTime? = null,
    val received: DateTime? = null,
    val recipient: List<Reference> = listOf(),
    val sender: Reference? = null,
    val reasonCode: List<CodeableConcept> = listOf(),
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
    val content: DynamicValue<Any>?
) : BackboneElement<CommunicationPayload>

class CommunicationPayloadSerializer : BaseFHIRSerializer<CommunicationPayload>(CommunicationPayload::class.java)
class CommunicationPayloadDeserializer : BaseFHIRDeserializer<CommunicationPayload>(CommunicationPayload::class.java)
