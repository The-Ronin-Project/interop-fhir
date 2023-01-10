package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.fhir.r4.element.Element

/**
 * Interface defining a primitive datatype.
 */
interface Primitive<T, PT : Primitive<T, PT>> : Element<PT> {
    val value: T?
        @JsonValue
        get() = value

    fun hasPrimitiveData(): Boolean = id != null || extension.isNotEmpty()
}
