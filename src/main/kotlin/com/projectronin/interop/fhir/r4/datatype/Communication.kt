package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

/**
 * A language which may be used to communicate with the patient about his or her health.
 */
@JsonSerialize(using = CommunicationSerializer::class)
@JsonDeserialize(using = CommunicationDeserializer::class)
data class Communication(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val language: CodeableConcept?,
    val preferred: FHIRBoolean? = null
) : BackboneElement<Communication>

class CommunicationSerializer : BaseFHIRSerializer<Communication>(Communication::class.java)
class CommunicationDeserializer : BaseFHIRDeserializer<Communication>(Communication::class.java)
