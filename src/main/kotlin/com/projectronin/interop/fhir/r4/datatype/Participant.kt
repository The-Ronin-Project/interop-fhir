package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

/**
 * List of participants involved in the appointment.
 */
@JsonSerialize(using = ParticipantSerializer::class)
@JsonDeserialize(using = ParticipantDeserializer::class)
data class Participant(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val type: List<CodeableConcept> = listOf(),
    val actor: Reference? = null,
    val required: Code? = null,
    val status: Code?,
    val period: Period? = null
) : BackboneElement<Participant>

class ParticipantSerializer : BaseFHIRSerializer<Participant>(Participant::class.java)
class ParticipantDeserializer : BaseFHIRDeserializer<Participant>(Participant::class.java)
