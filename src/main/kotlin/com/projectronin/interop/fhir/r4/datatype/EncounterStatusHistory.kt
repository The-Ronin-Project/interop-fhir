package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.Code

/**
 * List of past encounter statuses.
 * See [R4 Encounter.statusHistory](https://hl7.org/fhir/R4/encounter-definitions.html#Encounter.statusHistory).
 */
data class EncounterStatusHistory(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val status: Code?,
    val period: Period?,
) : BackboneElement<EncounterStatusHistory>
