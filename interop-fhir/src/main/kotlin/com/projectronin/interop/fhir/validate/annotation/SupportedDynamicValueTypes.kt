package com.projectronin.interop.fhir.validate.annotation

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType

/**
 * Indicates that the annotated type is a DynamicValue that only supports the provided [DynamicValueType]s
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class SupportedDynamicValueTypes(
    vararg val value: DynamicValueType
)
