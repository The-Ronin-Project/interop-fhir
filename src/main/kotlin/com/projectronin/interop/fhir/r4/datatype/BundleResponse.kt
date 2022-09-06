package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.Resource

/**
 * Results of execution (transaction/batch/history)
 */
data class BundleResponse(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val status: String?,
    val location: Uri? = null,
    val etag: String? = null,
    val lastModified: Instant? = null,
    val outcome: Resource<*>? = null
) : BackboneElement<BundleResponse>
