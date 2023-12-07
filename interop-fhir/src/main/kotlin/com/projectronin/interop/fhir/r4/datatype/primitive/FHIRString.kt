package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator
import com.projectronin.interop.fhir.r4.datatype.Extension

/**
 * A sequence of Unicode characters
 */
data class FHIRString(
    override val value: String?,
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
) : Primitive<String, FHIRString> {
    @JsonCreator
    constructor(value: String) : this(value, null, emptyList())

    @JsonCreator
    constructor(value: Int) : this(value.toString(), null, emptyList())
}

fun String.asFHIR(): FHIRString = FHIRString(this)

fun List<String>.asFHIR(): List<FHIRString> = this.map { it.asFHIR() }
