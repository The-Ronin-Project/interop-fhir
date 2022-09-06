package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal

/**
 * Search related information
 */
data class BundleSearch(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val mode: Code? = null,
    val score: Decimal? = null
) : BackboneElement<BundleSearch>
