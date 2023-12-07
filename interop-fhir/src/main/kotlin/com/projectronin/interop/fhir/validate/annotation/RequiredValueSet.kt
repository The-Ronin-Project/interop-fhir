package com.projectronin.interop.fhir.validate.annotation

import com.projectronin.interop.common.enums.CodedEnum
import kotlin.reflect.KClass

/**
 * Indicates that the annotated type has a required ValueSet binding backed by the provided enum,
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequiredValueSet(
    val value: KClass<out CodedEnum<*>>,
)
