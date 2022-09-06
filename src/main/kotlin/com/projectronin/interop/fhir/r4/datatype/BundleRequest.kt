package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

/**
 * Additional execution information (transaction/batch/history)
 */
data class BundleRequest(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val method: Code?,
    val url: Uri?,
    val ifNoneMatch: String? = null,
    val ifModifiedSince: Instant? = null,
    val ifMatch: String? = null,
    val ifNoneExist: String? = null
) : BackboneElement<BundleRequest>
