package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.Code

/**
 * List of past encounter classes.
 * See [R4 Encounter.classHistory](https://hl7.org/fhir/R4/encounter-definitions.html#Encounter.classHistory).
 */
data class EncounterClassHistory(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val `class`: Code?,
    val period: Period?,
) : BackboneElement<EncounterClassHistory>
