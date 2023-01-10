package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.element.BackboneElement

/**
 * Describes the occurrence of an event that may occur multiple times. Timing schedules are used for specifying when
 * events are expected or requested to occur and may also be used to represent the summary of a past or ongoing event.
 * For simplicity, the definitions of Timing components are expressed as 'future' events, but such components can also
 * be used to describe historic or ongoing events. A Timing schedule can be a list of events and/or criteria for when
 * the event happens, which can be expressed in a structured form and/or as a code. When both event and a repeating
 * specification are provided, the list of events should be understood as an interpretation of the information in the
 * repeat structure.
 */
@JsonSerialize(using = TimingSerializer::class)
@JsonDeserialize(using = TimingDeserializer::class)
data class Timing(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val event: List<DateTime> = listOf(),
    val repeat: TimingRepeat? = null,
    val code: CodeableConcept? = null
) : BackboneElement<Timing>

class TimingSerializer : BaseFHIRSerializer<Timing>(Timing::class.java)
class TimingDeserializer : BaseFHIRDeserializer<Timing>(Timing::class.java)
