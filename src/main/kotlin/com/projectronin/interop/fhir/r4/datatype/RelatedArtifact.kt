package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Url

/**
 * The RelatedArtifact structure defines resources related to a module such as previous and next versions of documents,
 * documentation, citations, etc. Note that the name resource here is being used in a more general sense than the
 * FHIR-specific Resource. The related resource may be a FHIR resource, or it may be another type of resource,
 * represented using the Attachment data type.
 */
data class RelatedArtifact(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    val type: Code?,
    val label: String? = null,
    val display: String? = null,
    val citation: Markdown? = null,
    val url: Url? = null,
    val document: Attachment? = null,
    val resource: Canonical? = null
) : Element<RelatedArtifact>
