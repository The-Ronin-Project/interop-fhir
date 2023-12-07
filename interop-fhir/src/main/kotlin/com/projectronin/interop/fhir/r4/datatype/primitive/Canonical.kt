package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator
import com.projectronin.interop.fhir.r4.datatype.Extension

/**
 * A URI that refers to a resource by its canonical URL (resources with a url property). The canonical type differs from a
 * uri in that it has special meaning in this specification, and in that it may have a version appended, separated by a
 * vertical bar (|). Note that the type canonical is not used for the actual canonical URLs that are the target of these
 * references, but for the URIs that refer to them, and may have the version suffix in them. Like other URIs, elements
 * of type canonical may also have #fragment references
 */
data class Canonical(
    override val value: String?,
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
) : Primitive<String, Canonical> {
    @JsonCreator
    constructor(value: String) : this(value, null, emptyList())
}
