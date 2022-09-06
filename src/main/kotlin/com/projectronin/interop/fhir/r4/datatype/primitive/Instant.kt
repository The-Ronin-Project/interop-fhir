package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator

/**
 * An instant in time in the format YYYY-MM-DDThh:mm:ss.sss+zz:zz (e.g. 2015-02-07T13:28:17.239+02:00 or 2017-01-01T00:00:00Z).
 * The time SHALL specified at least to the second and SHALL include a time zone.
 */
data class Instant @JsonCreator constructor(override val value: String) : Primitive<String, Instant>
