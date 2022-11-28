package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator
import com.projectronin.interop.fhir.r4.datatype.Extension

/**
 * Any positive integer in the range 1..2,147,483,647
 */
data class PositiveInt(
    override val value: Int?,
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf()
) : Primitive<Int, PositiveInt> {
    @JsonCreator
    constructor(value: Int) : this(value, null, emptyList())
}
