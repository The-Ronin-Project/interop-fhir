package com.projectronin.interop.fhir.r4.datatype

data class CareTeamParticipant(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val role: List<CodeableConcept> = listOf(),
    val member: Reference? = null,
    val onBehalfOf: Reference? = null,
    val period: Period? = null,
) : BackboneElement<CareTeamParticipant>
