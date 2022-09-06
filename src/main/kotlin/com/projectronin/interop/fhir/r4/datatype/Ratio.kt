package com.projectronin.interop.fhir.r4.datatype

/**
 * A relationship between two Quantity values expressed as a numerator and a denominator. The Ratio datatype should only
 * be used to express a relationship of two numbers if the relationship cannot be suitably expressed using a Quantity
 * and a common unit. Where the denominator value is known to be fixed to "1", Quantity should be used instead of Ratio.
 */
data class Ratio(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    val numerator: Quantity? = null,
    val denominator: Quantity? = null
) : Element<Ratio>
