package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

/**
 * A measured amount (or an amount that can potentially be measured).
 */
data class Quantity(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val value: Double? = null,
    override val comparator: Code? = null,
    override val unit: String? = null,
    override val system: Uri? = null,
    override val code: Code? = null
) : BaseQuantity<Quantity>(id, extension, value, comparator, unit, system, code)
