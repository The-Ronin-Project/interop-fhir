package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

@JsonSerialize(using = CareTeamParticipantSerializer::class)
@JsonDeserialize(using = CareTeamParticipantDeserializer::class)
data class CareTeamParticipant(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val role: List<CodeableConcept> = listOf(),
    val member: Reference? = null,
    val onBehalfOf: Reference? = null,
    val period: Period? = null,
) : BackboneElement<CareTeamParticipant>

class CareTeamParticipantSerializer : BaseFHIRSerializer<CareTeamParticipant>(CareTeamParticipant::class.java)
class CareTeamParticipantDeserializer : BaseFHIRDeserializer<CareTeamParticipant>(CareTeamParticipant::class.java)
