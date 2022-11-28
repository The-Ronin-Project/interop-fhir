package com.projectronin.interop.fhir.stu3.datatype

import com.projectronin.interop.fhir.r4.datatype.Element
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.validate.Validatable

/**
 * The base definition for all elements contained inside a resource.
 *
 * See [FHIR Spec](https://hl7.org/fhir/R4/element.html)
 */
interface STU3Element<T : STU3Element<T>> : Validatable<T> {
    val id: FHIRString?
    val extension: List<Extension>

    fun transformToR4(): Element<*>
}
