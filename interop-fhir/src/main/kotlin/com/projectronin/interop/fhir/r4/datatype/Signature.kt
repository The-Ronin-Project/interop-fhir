package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.event.interop.internal.v1.ResourceType
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Base64Binary
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.element.Element
import com.projectronin.interop.fhir.validate.annotation.SupportedReferenceTypes

/**
 * A Signature holds an electronic representation of a signature and its supporting context in a FHIR accessible form.
 * The signature may either be a cryptographic type (XML DigSig or a JWS), which is able to provide non-repudiation proof,
 * or it may be a graphical image that represents a signature or a signature process.
 */
@JsonSerialize(using = SignatureSerializer::class)
@JsonDeserialize(using = SignatureDeserializer::class)
data class Signature(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val type: List<Coding>,
    val `when`: Instant?,
    @SupportedReferenceTypes(
        ResourceType.Practitioner,
        ResourceType.PractitionerRole,
        ResourceType.RelatedPerson,
        ResourceType.Patient,
        ResourceType.Device,
        ResourceType.Organization
    )
    val who: Reference?,
    @SupportedReferenceTypes(
        ResourceType.Practitioner,
        ResourceType.PractitionerRole,
        ResourceType.RelatedPerson,
        ResourceType.Patient,
        ResourceType.Device,
        ResourceType.Organization
    )
    val onBehalfOf: Reference? = null,
    val targetFormat: Code? = null,
    val sigFormat: Code? = null,
    val data: Base64Binary? = null
) : Element<Signature>

class SignatureSerializer : BaseFHIRSerializer<Signature>(Signature::class.java)
class SignatureDeserializer : BaseFHIRDeserializer<Signature>(Signature::class.java)
