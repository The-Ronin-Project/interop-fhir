package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

/**
 * An amount of currency.
 */
@JsonSerialize(using = MoneySerializer::class)
@JsonDeserialize(using = MoneyDeserializer::class)
data class Money(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val value: Decimal? = null,
    val currency: Code? = null
) : Element<Money>

class MoneySerializer : BaseFHIRSerializer<Money>(Money::class.java)
class MoneyDeserializer : BaseFHIRDeserializer<Money>(Money::class.java)
