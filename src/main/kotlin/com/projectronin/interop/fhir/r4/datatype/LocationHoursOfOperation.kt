package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Time

/**
 * What days/times during a week is this location usually open.
 */
@JsonSerialize(using = LocationHoursOfOperationSerializer::class)
@JsonDeserialize(using = LocationHoursOfOperationDeserializer::class)
data class LocationHoursOfOperation(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val daysOfWeek: List<Code> = listOf(),
    val allDay: FHIRBoolean? = null,
    val openingTime: Time? = null,
    val closingTime: Time? = null
) : BackboneElement<LocationHoursOfOperation>

class LocationHoursOfOperationSerializer :
    BaseFHIRSerializer<LocationHoursOfOperation>(LocationHoursOfOperation::class.java)

class LocationHoursOfOperationDeserializer :
    BaseFHIRDeserializer<LocationHoursOfOperation>(LocationHoursOfOperation::class.java)
