package com.projectronin.interop.fhir.stu3.element

import com.projectronin.interop.fhir.r4.datatype.Extension

/**
 * The base definition for complex elements defined as part of a resource definition - that is, elements that have children that are defined in the resource.
 *
 * See [FHIR Spec](https://hl7.org/fhir/STU3/backboneelement.html)
 */
interface STU3BackboneElement<T : STU3BackboneElement<T>> : STU3Element<T> {
    val modifierExtension: List<Extension>
}
