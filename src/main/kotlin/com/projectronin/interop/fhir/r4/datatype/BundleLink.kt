package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

/**
 * A link that provides context to a bundle.
 */
data class BundleLink(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val relation: String?,
    val url: Uri?
) : BackboneElement<BundleLink>
