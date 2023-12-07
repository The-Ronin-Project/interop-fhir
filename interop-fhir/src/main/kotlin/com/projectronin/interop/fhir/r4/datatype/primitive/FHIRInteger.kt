package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator
import com.projectronin.interop.fhir.r4.datatype.Extension

/**
 * A signed integer in the range −2,147,483,648..2,147,483,647 (32-bit; for larger values, use decimal)
 */
data class FHIRInteger(
    override val value: Int?,
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
) : Primitive<Int, FHIRInteger> {
    @JsonCreator
    constructor(value: Int) : this(value, null, emptyList())
}

fun Int.asFHIR(): FHIRInteger = FHIRInteger(this)
