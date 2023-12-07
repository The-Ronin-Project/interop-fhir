package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Time
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.interop.fhir.r4.element.Element
import com.projectronin.interop.fhir.r4.valueset.UnitOfTime
import com.projectronin.interop.fhir.validate.annotation.RequiredValueSet
import com.projectronin.interop.fhir.validate.annotation.SupportedDynamicValueTypes

/**
 * Supports the Timing datatype: When both event and a repeating
 * specification are provided, the list of events should be understood as an interpretation of the information in the
 * repeat structure.
 */
@JsonDeserialize(using = TimingRepeatDeserializer::class)
@JsonSerialize(using = TimingRepeatSerializer::class)
data class TimingRepeat(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    @SupportedDynamicValueTypes(DynamicValueType.DURATION, DynamicValueType.RANGE, DynamicValueType.PERIOD)
    val bounds: DynamicValue<Any>? = null,
    val count: PositiveInt? = null,
    val countMax: PositiveInt? = null,
    val duration: Decimal? = null,
    val durationMax: Decimal? = null,
    @RequiredValueSet(UnitOfTime::class)
    val durationUnit: Code? = null,
    val frequency: PositiveInt? = null,
    val frequencyMax: PositiveInt? = null,
    val period: Decimal? = null,
    val periodMax: Decimal? = null,
    @RequiredValueSet(UnitOfTime::class)
    val periodUnit: Code? = null,
    val dayOfWeek: List<Code> = listOf(),
    val timeOfDay: List<Time> = listOf(),
    val `when`: List<Code> = listOf(),
    val offset: UnsignedInt? = null,
) : Element<TimingRepeat>

class TimingRepeatDeserializer : BaseFHIRDeserializer<TimingRepeat>(TimingRepeat::class.java)

class TimingRepeatSerializer : BaseFHIRSerializer<TimingRepeat>(TimingRepeat::class.java)
