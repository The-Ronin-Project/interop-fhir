package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator
import com.projectronin.interop.fhir.r4.datatype.Extension
import java.math.BigDecimal

/**
 * 	Rational numbers that have a decimal representation.
 *
 * See [FHIR documentation](http://hl7.org/fhir/R4/datatypes.html#decimal)
 */
data class Decimal(
    override val value: BigDecimal?,
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf()
) : Primitive<BigDecimal, Decimal> {
    @JsonCreator
    @Deprecated("Use Decimal(BigDecimal) instead")
    constructor(value: Double?) : this(value?.toBigDecimal(), null, emptyList())

    @JsonCreator
    @Deprecated("Use Decimal(BigDecimal) instead")
    constructor(value: Number?) : this(value?.toDouble()?.toBigDecimal(), null, emptyList())

    @JsonCreator
    constructor(value: BigDecimal) : this(value, null, emptyList())
}
