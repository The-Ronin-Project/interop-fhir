package com.projectronin.interop.fhir.r4.element

import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.validate.Validatable

/**
 * The base definition for all elements contained inside a resource.
 *
 * See [FHIR Spec](https://hl7.org/fhir/R4/element.html)
 */
interface Element<T : Element<T>> : Validatable<T> {
    val id: FHIRString?
    val extension: List<Extension>
}
