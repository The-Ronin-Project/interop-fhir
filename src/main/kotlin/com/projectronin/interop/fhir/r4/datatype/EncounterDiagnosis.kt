package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt

/**
 * List of diagnoses relevant to this encounter.
 * See [R4 Encounter.diagnosis](https://hl7.org/fhir/R4/encounter-definitions.html#Encounter.diagnosis).
 */
data class EncounterDiagnosis(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val condition: Reference?,
    val use: CodeableConcept? = null,
    val rank: PositiveInt? = null,
) : BackboneElement<EncounterDiagnosis>
