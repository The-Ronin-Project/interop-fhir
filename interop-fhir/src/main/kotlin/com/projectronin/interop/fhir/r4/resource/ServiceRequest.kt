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
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

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
    val basedOn: List<Reference> = listOf(),
    val replaces: List<Reference> = listOf(),
    val requisition: Identifier? = null,
    val status: Code?,
    val intent: Code?,
    val category: List<CodeableConcept> = listOf(),
    val priority: Code? = null,
    val doNotPerform: FHIRBoolean? = null,
    val code: CodeableConcept? = null,
    val orderDetail: List<CodeableConcept> = listOf(),
    val quantity: DynamicValue<Any>? = null,
    val subject: Reference?,
    val encounter: Reference? = null,
    val occurrence: DynamicValue<Any>? = null,
    val asNeeded: DynamicValue<Any>? = null,
    val authoredOn: DateTime? = null,
    val requester: Reference? = null,
    val performerType: CodeableConcept? = null,
    val performer: List<Reference> = listOf(),
    val locationCode: List<CodeableConcept> = listOf(),
    val locationReference: List<Reference> = listOf(),
    val reasonCode: List<CodeableConcept> = listOf(),
    val reasonReference: List<Reference> = listOf(),
    val insurance: List<Reference> = listOf(),
    val supportingInfo: List<Reference> = listOf(),
    val specimen: List<Reference> = listOf(),
    val bodySite: List<CodeableConcept> = listOf(),
    val note: List<Annotation> = listOf(),
    val patientInstruction: FHIRString? = null,
    val relevantHistory: List<Reference> = listOf()
) : DomainResource<ServiceRequest> {
    override val resourceType: String = "ServiceRequest"
}

class ServiceRequestSerializer : BaseFHIRSerializer<ServiceRequest>(ServiceRequest::class.java)
class ServiceRequestDeserializer : BaseFHIRDeserializer<ServiceRequest>(ServiceRequest::class.java)
