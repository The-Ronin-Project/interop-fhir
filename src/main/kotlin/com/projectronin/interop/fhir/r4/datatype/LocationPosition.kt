package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal

/**
 * The absolute geographic location.
 *
 * See [FHIR Spec](https://hl7.org/fhir/R4/location-definitions.html#Location.position)
 */
data class LocationPosition(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val longitude: Decimal,
    val latitude: Decimal,
    val altitude: Decimal? = null,
) : BackboneElement
