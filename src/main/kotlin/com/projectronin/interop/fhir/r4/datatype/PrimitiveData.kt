package com.projectronin.interop.fhir.r4.datatype

/**
 * Defines additional data associated to a primitive.
 */
data class PrimitiveData(
    override val id: String? = null,
    override val extension: List<Extension> = listOf()
) : Element<PrimitiveData>
