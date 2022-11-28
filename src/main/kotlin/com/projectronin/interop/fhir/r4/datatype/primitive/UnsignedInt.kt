package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator
import com.projectronin.interop.fhir.r4.datatype.Extension

/**
 * Any non-negative integer in the range 0..2,147,483,647
 *
 * See [FHIR documentation](http://hl7.org/fhir/R4/datatypes.html#unsignedInt)
 */
data class UnsignedInt(
    override val value: Int?,
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf()
) : Primitive<Int, UnsignedInt> {
    @JsonCreator
    constructor(value: Int) : this(value, null, emptyList())
}
