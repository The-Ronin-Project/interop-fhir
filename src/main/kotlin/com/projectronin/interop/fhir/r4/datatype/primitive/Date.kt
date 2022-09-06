package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator

/**
 * A date, or partial date (e.g. just year or year + month) as used in human communication. The format is YYYY, YYYY-MM,
 * or YYYY-MM-DD, e.g. 2018, 1973-06, or 1905-08-23. There SHALL be no time zone. Dates SHALL be valid dates
 */
data class Date @JsonCreator constructor(override val value: String) : Primitive<String, Date>
