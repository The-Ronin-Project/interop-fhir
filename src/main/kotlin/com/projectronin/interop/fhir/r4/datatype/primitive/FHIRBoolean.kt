package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator
import com.projectronin.interop.fhir.r4.datatype.Extension

data class FHIRBoolean(
    override val value: Boolean?,
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf()
) : Primitive<Boolean, FHIRBoolean> {
    @JsonCreator
    constructor(value: Boolean) : this(value, null, emptyList())

    companion object {
        val TRUE = FHIRBoolean(true)
        val FALSE = FHIRBoolean(false)
    }
}

fun Boolean.asFHIR(): FHIRBoolean = FHIRBoolean(this)
