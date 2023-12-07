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
 * Defined variation of [Quantity] for describing a Duration.
 */
@JsonSerialize(using = DurationSerializer::class)
@JsonDeserialize(using = DurationDeserializer::class)
data class Duration(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val value: Decimal? = null,
    override val comparator: Code? = null,
    override val unit: FHIRString? = null,
    override val system: Uri? = null,
    override val code: Code? = null,
) : BaseQuantity<Duration>(id, extension, value, comparator, unit, system, code)

class DurationSerializer : BaseFHIRSerializer<Duration>(Duration::class.java)

class DurationDeserializer : BaseFHIRDeserializer<Duration>(Duration::class.java)
