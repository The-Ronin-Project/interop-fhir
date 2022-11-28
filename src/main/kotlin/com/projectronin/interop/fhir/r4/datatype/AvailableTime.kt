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
 * Times the practitioner is available or performing this role at the location and/or healthcareservice.
 */
@JsonSerialize(using = AvailableTimeSerializer::class)
@JsonDeserialize(using = AvailableTimeDeserializer::class)
data class AvailableTime(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val daysOfWeek: List<Code> = listOf(),
    val allDay: FHIRBoolean? = null,
    val availableStartTime: Time? = null,
    val availableEndTime: Time? = null
) : BackboneElement<AvailableTime>

class AvailableTimeSerializer : BaseFHIRSerializer<AvailableTime>(AvailableTime::class.java)
class AvailableTimeDeserializer : BaseFHIRDeserializer<AvailableTime>(AvailableTime::class.java)
