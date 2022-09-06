package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator

/**
 * Any combination of upper- or lower-case ASCII letters ('A'..'Z', and 'a'..'z', numerals ('0'..'9'), '-' and '.'
 */
data class Id @JsonCreator constructor(override val value: String) : Primitive<String, Id>
