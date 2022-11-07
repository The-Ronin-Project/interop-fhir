package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator
import com.projectronin.interop.fhir.r4.datatype.Extension

/**
 * A Uniform Resource Identifier Reference (RFC 3986). URIs can be absolute or relative, and may have an optional fragment identifier
 */
data class Uri @JsonCreator constructor(
    override val value: String?,
    override val id: String? = null,
    override val extension: List<Extension> = listOf()
) : Primitive<String, Uri> {
    @JsonCreator
    constructor(value: String) : this(value, null, emptyList())
}
