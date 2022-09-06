package com.projectronin.interop.fhir.validate

import kotlin.jvm.internal.CallableReference
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

/**
 * A LocationContext provides information about the current location for a validation.
 * The Location is determined by an element and optional field, where a field may be a period-delimited hierarchy.
 * A field may also carry index information in the form of `[index]`.
 */
data class LocationContext constructor(
    val element: String,
    val field: String?
) {
    /**
     * Creates a LocationContext based off the [clazz]. No [field] will be included.
     */
    constructor(clazz: KClass<*>) : this(clazz.simpleName!!, null)

    /**
     * Creates a LocationContext based off the [method] where the [element] is the method's Class and [field] is the method itself.
     */
    constructor(method: KProperty1<*, *>) : this(
        ((method as CallableReference).owner as KClass<*>).simpleName!!,
        method.name
    )

    override fun toString(): String = field?.let { "$element.$it" } ?: element
}

/**
 * Appends the [context] to this potentially null LocationContext. If this is a null instance, the provided context is returned.
 */
fun LocationContext?.append(context: LocationContext): LocationContext {
    if (this == null) {
        return context
    }

    val newField = context.field?.let { if (field.isNullOrEmpty()) it else "$field.$it" } ?: field
    return LocationContext(element, newField)
}
