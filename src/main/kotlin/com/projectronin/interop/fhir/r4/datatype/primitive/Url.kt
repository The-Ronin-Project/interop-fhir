package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator

/**
 * 	A Uniform Resource Locator (RFC 1738 ). Note URLs are accessed directly using the specified protocol.
 * 	Common URL protocols are http{s}:, ftp:, mailto: and mllp:, though many others are defined
 */
data class Url @JsonCreator constructor(override val value: String) : Primitive<String, Url>
