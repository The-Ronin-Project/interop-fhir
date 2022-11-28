package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

/**
 * List of past encounter classes.
 * See [R4 Encounter.classHistory](https://hl7.org/fhir/R4/encounter-definitions.html#Encounter.classHistory).
 */
@JsonSerialize(using = EncounterClassHistorySerializer::class)
@JsonDeserialize(using = EncounterClassHistoryDeserializer::class)
data class EncounterClassHistory(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val `class`: Code?,
    val period: Period?,
) : BackboneElement<EncounterClassHistory>

class EncounterClassHistorySerializer : BaseFHIRSerializer<EncounterClassHistory>(EncounterClassHistory::class.java)
class EncounterClassHistoryDeserializer : BaseFHIRDeserializer<EncounterClassHistory>(EncounterClassHistory::class.java)
