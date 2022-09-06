package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

abstract class BaseQuantity<T : BaseQuantity<T>>(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    open val value: Double? = null,
    open val comparator: Code? = null,
    open val unit: String? = null,
    open val system: Uri? = null,
    open val code: Code? = null
) : Element<T>
