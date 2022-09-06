package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Time

/**
 * What days/times during a week is this location usually open.
 */
data class LocationHoursOfOperation(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val daysOfWeek: List<Code> = listOf(),
    val allDay: Boolean? = null,
    val openingTime: Time? = null,
    val closingTime: Time? = null
) : BackboneElement<LocationHoursOfOperation>
