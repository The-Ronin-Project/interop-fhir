package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

/**
 * A set of ordered Quantity values defined by a low and high limit. A Range specifies a set of possible values;
 * usually, one value from the range applies (e.g. "give the patient between 2 and 4 tablets"). Ranges are typically used in instructions.
 */
@JsonSerialize(using = RangeSerializer::class)
@JsonDeserialize(using = RangeDeserializer::class)
data class Range(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val low: SimpleQuantity? = null,
    val high: SimpleQuantity? = null
) : Element<Range>

class RangeSerializer : BaseFHIRSerializer<Range>(Range::class.java)
class RangeDeserializer : BaseFHIRDeserializer<Range>(Range::class.java)
