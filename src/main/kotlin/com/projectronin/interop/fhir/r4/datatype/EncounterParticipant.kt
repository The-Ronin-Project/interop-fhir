package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

/**
 * List of participants involved in the encounter.
 * See [R4 Encounter.participant](https://hl7.org/fhir/R4/encounter-definitions.html#Encounter.participant).
 */
@JsonSerialize(using = EncounterParticipantSerializer::class)
@JsonDeserialize(using = EncounterParticipantDeserializer::class)
data class EncounterParticipant(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val type: List<CodeableConcept> = listOf(),
    val period: Period? = null,
    val individual: Reference? = null,
) : BackboneElement<EncounterParticipant>

class EncounterParticipantSerializer : BaseFHIRSerializer<EncounterParticipant>(EncounterParticipant::class.java)
class EncounterParticipantDeserializer : BaseFHIRDeserializer<EncounterParticipant>(EncounterParticipant::class.java)
