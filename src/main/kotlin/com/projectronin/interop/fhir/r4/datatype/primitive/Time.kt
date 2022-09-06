package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator

/**
 * 	A time during the day, in the format hh:mm:ss. There is no date specified. Seconds must be provided due to schema
 * 	type constraints but may be zero-filled and may be ignored at receiver discretion. The time "24:00" SHALL NOT be used.
 * 	A time zone SHALL NOT be present. Times can be converted to a Duration since midnight.
 */
data class Time @JsonCreator constructor(override val value: String) : Primitive<String, Time>
