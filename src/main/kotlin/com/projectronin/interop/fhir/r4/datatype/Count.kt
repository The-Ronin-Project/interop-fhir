package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

/**
 * Defined variation of [Quantity] for describing Counts.
 */
data class Count(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val value: Double? = null,
    override val comparator: Code? = null,
    override val unit: String? = null,
    override val system: Uri? = null,
    override val code: Code? = null
) : BaseQuantity<Count>(id, extension, value, comparator, unit, system, code)
