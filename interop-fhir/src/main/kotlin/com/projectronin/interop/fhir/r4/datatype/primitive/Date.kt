package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator
import com.projectronin.interop.fhir.r4.datatype.Extension

/**
 * A date, or partial date (e.g. just year or year + month) as used in human communication. The format is YYYY, YYYY-MM,
 * or YYYY-MM-DD, e.g. 2018, 1973-06, or 1905-08-23. There SHALL be no time zone. Dates SHALL be valid dates
 */
data class Date(
    override val value: String?,
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf()
) : Primitive<String, Date> {
    @JsonCreator
    constructor(value: String) : this(value, null, emptyList())
}
