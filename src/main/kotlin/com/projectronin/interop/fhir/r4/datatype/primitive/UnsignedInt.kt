package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator

/**
 * Any non-negative integer in the range 0..2,147,483,647
 *
 * See [FHIR documentation](http://hl7.org/fhir/R4/datatypes.html#unsignedInt)
 */
data class UnsignedInt @JsonCreator constructor(override val value: Int) : Primitive<Int, UnsignedInt>
