package com.projectronin.interop.fhir.r4.datatype

/**
 * List of participants involved in the encounter.
 * See [R4 Encounter.participant](https://hl7.org/fhir/R4/encounter-definitions.html#Encounter.participant).
 */
data class EncounterParticipant(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val type: List<CodeableConcept> = listOf(),
    val period: Period? = null,
    val individual: Reference? = null,
) : BackboneElement<EncounterParticipant>
