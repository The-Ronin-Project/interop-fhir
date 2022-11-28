package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

/**
 * The absolute geographic location.
 */
@JsonSerialize(using = LocationPositionSerializer::class)
@JsonDeserialize(using = LocationPositionDeserializer::class)
data class LocationPosition(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val longitude: Decimal?,
    val latitude: Decimal?,
    val altitude: Decimal? = null,
) : BackboneElement<LocationPosition>

class LocationPositionSerializer : BaseFHIRSerializer<LocationPosition>(LocationPosition::class.java)
class LocationPositionDeserializer : BaseFHIRDeserializer<LocationPosition>(LocationPosition::class.java)
