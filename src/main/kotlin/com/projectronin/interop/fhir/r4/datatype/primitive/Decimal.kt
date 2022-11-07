package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator
import com.projectronin.interop.fhir.r4.datatype.Extension

/**
 * 	Rational numbers that have a decimal representation.
 *
 * See [FHIR documentation](http://hl7.org/fhir/R4/datatypes.html#decimal)
 */
data class Decimal(
    override val value: Double?,
    override val id: String? = null,
    override val extension: List<Extension> = listOf()
) : Primitive<Double, Decimal> {
    @JsonCreator
    constructor(value: Double) : this(value, null, emptyList())
}
