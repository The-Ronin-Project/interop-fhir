package com.projectronin.interop.fhir.validate.annotation

/**
 * Indicates that the annotated type is required. For most elements, this indicates that it is non-null; however, when
 * placed on a Collection, this annotation also assumes the provided Collection is non-empty.
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequiredField
