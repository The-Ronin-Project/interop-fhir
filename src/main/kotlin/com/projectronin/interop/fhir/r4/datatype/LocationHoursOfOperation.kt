package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.Time
import com.projectronin.interop.fhir.r4.valueset.DayOfWeek

/**
 * What days/times during a week is this location usually open.
 *
 * See [FHIR Spec](https://www.hl7.org/fhir/location-definitions.html#Location.hoursOfOperation)
 */
data class LocationHoursOfOperation(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val daysOfWeek: List<DayOfWeek> = listOf(),
    val allDay: Boolean? = null,
    val openingTime: Time? = null,
    val closingTime: Time? = null
) : BackboneElement
