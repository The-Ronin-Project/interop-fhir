package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator
import com.projectronin.interop.fhir.r4.datatype.Extension

/**
 * A stream of bytes, base64 encoded (RFC 4648)
 */
data class Base64Binary(
    override val value: String?,
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf()
) : Primitive<String, Base64Binary> {
    @JsonCreator
    constructor(value: String) : this(value, null, emptyList())
}
