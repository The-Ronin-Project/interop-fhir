package com.projectronin.interop.fhir.r4.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.Element
import com.projectronin.interop.fhir.r4.datatype.Extension

/**
 * Defines additional data associated to a primitive.
 */
data class PrimitiveData(
    override val id: String? = null,
    override val extension: List<Extension> = listOf()
) : Element<PrimitiveData>
