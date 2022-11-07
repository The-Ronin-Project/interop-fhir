package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator
import com.projectronin.interop.fhir.r4.datatype.Extension

/**
 * Any combination of upper- or lower-case ASCII letters ('A'..'Z', and 'a'..'z', numerals ('0'..'9'), '-' and '.'
 */
data class Id(
    override val value: String?,
    override val id: String? = null,
    override val extension: List<Extension> = listOf()
) : Primitive<String, Id> {
    @JsonCreator
    constructor(value: String) : this(value, null, emptyList())
}
