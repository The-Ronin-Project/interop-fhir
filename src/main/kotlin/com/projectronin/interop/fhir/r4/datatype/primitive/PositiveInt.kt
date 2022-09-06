package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator

/**
 * Any positive integer in the range 1..2,147,483,647
 */
data class PositiveInt @JsonCreator constructor(override val value: Int) : Primitive<Int, PositiveInt>
