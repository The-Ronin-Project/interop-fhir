package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

/**
 * A comparator-less Quantity.
 */
@JsonSerialize(using = SimpleQuantitySerializer::class)
@JsonDeserialize(using = SimpleQuantityDeserializer::class)
data class SimpleQuantity(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val value: Decimal? = null,
    override val unit: FHIRString? = null,
    override val system: Uri? = null,
    override val code: Code? = null
) : BaseQuantity<SimpleQuantity>(id, extension, value, null, unit, system, code)

class SimpleQuantitySerializer : BaseFHIRSerializer<SimpleQuantity>(SimpleQuantity::class.java)
class SimpleQuantityDeserializer : BaseFHIRDeserializer<SimpleQuantity>(SimpleQuantity::class.java)
