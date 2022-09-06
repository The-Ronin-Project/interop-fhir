package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator

/**
 * A Uniform Resource Identifier Reference (RFC 3986). URIs can be absolute or relative, and may have an optional fragment identifier
 */
data class Uri @JsonCreator constructor(override val value: String) : Primitive<String, Uri>
