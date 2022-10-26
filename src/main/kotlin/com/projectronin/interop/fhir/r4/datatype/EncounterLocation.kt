package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.Code

/**
 * List of locations where the patient has been.
 * See [R4 Encounter.location](https://hl7.org/fhir/R4/encounter-definitions.html#Encounter.location).
 */
data class EncounterLocation(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val location: Reference?,
    val status: Code? = null,
    val physicalType: CodeableConcept? = null,
    val period: Period? = null,
) : BackboneElement<EncounterLocation>
