package com.projectronin.interop.fhir.validate.annotation

import com.projectronin.event.interop.internal.v1.ResourceType

/**
 * Indicates that the annotated type is a Reference that only supports a limited set of reference types.
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class SupportedReferenceTypes(
    vararg val value: ResourceType
)
