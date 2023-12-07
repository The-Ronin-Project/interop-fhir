package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.element.Element

/**
 * A relationship between two Quantity values expressed as a numerator and a denominator. The Ratio datatype should only
 * be used to express a relationship of two numbers if the relationship cannot be suitably expressed using a Quantity
 * and a common unit. Where the denominator value is known to be fixed to "1", Quantity should be used instead of Ratio.
 */
@JsonSerialize(using = RatioSerializer::class)
@JsonDeserialize(using = RatioDeserializer::class)
data class Ratio(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val numerator: Quantity? = null,
    val denominator: Quantity? = null,
) : Element<Ratio>

class RatioSerializer : BaseFHIRSerializer<Ratio>(Ratio::class.java)

class RatioDeserializer : BaseFHIRDeserializer<Ratio>(Ratio::class.java)
