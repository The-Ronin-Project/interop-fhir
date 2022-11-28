package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

/**
 * List of past encounter statuses.
 * See [R4 Encounter.statusHistory](https://hl7.org/fhir/R4/encounter-definitions.html#Encounter.statusHistory).
 */
@JsonSerialize(using = EncounterStatusHistorySerializer::class)
@JsonDeserialize(using = EncounterStatusHistoryDeserializer::class)
data class EncounterStatusHistory(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val status: Code?,
    val period: Period?,
) : BackboneElement<EncounterStatusHistory>

class EncounterStatusHistorySerializer : BaseFHIRSerializer<EncounterStatusHistory>(EncounterStatusHistory::class.java)
class EncounterStatusHistoryDeserializer :
    BaseFHIRDeserializer<EncounterStatusHistory>(EncounterStatusHistory::class.java)
