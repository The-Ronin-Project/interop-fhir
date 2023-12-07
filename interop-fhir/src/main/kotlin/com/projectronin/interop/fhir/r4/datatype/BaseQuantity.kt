package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.Element

abstract class BaseQuantity<T : BaseQuantity<T>>(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    open val value: Decimal? = null,
    open val comparator: Code? = null,
    open val unit: FHIRString? = null,
    open val system: Uri? = null,
    open val code: Code? = null,
) : Element<T>
