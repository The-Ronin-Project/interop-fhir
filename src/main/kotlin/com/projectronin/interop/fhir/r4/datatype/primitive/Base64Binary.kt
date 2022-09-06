package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator

/**
 * A stream of bytes, base64 encoded (RFC 4648)
 */
data class Base64Binary @JsonCreator constructor(override val value: String) : Primitive<String, Base64Binary>
