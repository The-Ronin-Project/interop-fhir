package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator
import com.projectronin.interop.fhir.r4.datatype.Extension

/**
 * A date, date-time or partial date (e.g. just year or year + month) as used in human communication. The format is
 * YYYY, YYYY-MM, YYYY-MM-DD or YYYY-MM-DDThh:mm:ss+zz:zz, e.g. 2018, 1973-06, 1905-08-23, 2015-02-07T13:28:17-05:00 or
 * 2017-01-01T00:00:00.000Z. If hours and minutes are specified, a time zone SHALL be populated. Seconds must be provided
 * due to schema type constraints but may be zero-filled and may be ignored at receiver discretion. Dates SHALL be valid
 * dates. The time "24:00" is not allowed. Leap Seconds are allowed.
 */
data class DateTime(
    override val value: String?,
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
) : Primitive<String, DateTime> {
    @JsonCreator
    constructor(value: String) : this(value, null, emptyList())
}
