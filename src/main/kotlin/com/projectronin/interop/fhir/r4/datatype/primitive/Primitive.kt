package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.fhir.validate.Validatable

/**
 * Interface defining a primitive datatype.
 */
interface Primitive<T, PT : Primitive<T, PT>> : Validatable<PT> {
    val value: T
        @JsonValue
        get() = value
}
