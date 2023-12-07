package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator
import com.projectronin.interop.fhir.r4.datatype.Extension

/**
 * 	A Uniform Resource Locator (RFC 1738 ). Note URLs are accessed directly using the specified protocol.
 * 	Common URL protocols are http{s}:, ftp:, mailto: and mllp:, though many others are defined
 */
data class Url(
    override val value: String?,
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
) : Primitive<String, Url> {
    @JsonCreator
    constructor(value: String) : this(value, null, emptyList())
}
