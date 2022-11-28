package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

/**
 * Link to another patient resource that concerns the same actual patient.
 */
@JsonSerialize(using = PatientLinkSerializer::class)
@JsonDeserialize(using = PatientLinkDeserializer::class)
data class PatientLink(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val other: Reference?,
    val type: Code?
) : BackboneElement<PatientLink>

class PatientLinkSerializer : BaseFHIRSerializer<PatientLink>(PatientLink::class.java)
class PatientLinkDeserializer : BaseFHIRDeserializer<PatientLink>(PatientLink::class.java)
