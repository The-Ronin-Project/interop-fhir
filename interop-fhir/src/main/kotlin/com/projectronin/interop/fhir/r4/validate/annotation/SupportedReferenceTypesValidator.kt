package com.projectronin.interop.fhir.r4.validate.annotation

import com.projectronin.event.interop.internal.v1.ResourceType
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.validate.InvalidReferenceType
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validatable
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.annotation.SupportedReferenceTypes
import com.projectronin.interop.fhir.validate.append
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.isSubclassOf

/**
 * Validator for [SupportedReferenceTypes].
 */
class SupportedReferenceTypesValidator :
    BaseValidationAnnotationValidator<SupportedReferenceTypes>(SupportedReferenceTypes::class) {
    override fun <T : Validatable<T>> validateSupportedAnnotations(
        annotations: List<SupportedReferenceTypes>,
        property: KProperty1<T, *>,
        kotlinType: KClass<*>,
        element: T,
        elementName: String,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        val supportedTypes = annotations.flatMap { it.value.toList() }
        if (supportedTypes.isNotEmpty()) {
            if (kotlinType.isSubclassOf(Reference::class)) {
                validateRequiredReferenceTypes(
                    property.get(element) as? Reference,
                    supportedTypes,
                    parentContext.append(LocationContext(elementName, property.name)),
                    validation
                )
            } else if (kotlinType.isSubclassOf(DynamicValue::class)) {
                val dynamicValue = property.get(element) as? DynamicValue<*>
                dynamicValue?.let {
                    if (it.type == DynamicValueType.REFERENCE) {
                        validateRequiredReferenceTypes(
                            it.value as? Reference,
                            supportedTypes,
                            parentContext.append(LocationContext(elementName, property.name)),
                            validation
                        )
                    }
                }
            } else if (kotlinType.isSubclassOf(Collection::class)) {
                (property.get(element) as? Collection<*>)?.let { collection ->
                    collection.filterIsInstance<Reference>()
                        .forEachIndexed { index, reference ->
                            val indexedContext =
                                parentContext.append(LocationContext(elementName, "${property.name}[$index]"))

                            validateRequiredReferenceTypes(reference, supportedTypes, indexedContext, validation)
                        }
                }
            }
        }
    }

    private fun validateRequiredReferenceTypes(
        reference: Reference?,
        supportedTypes: List<ResourceType>,
        context: LocationContext,
        validation: Validation
    ) {
        reference?.let {
            reference.decomposedType().asResourceType()?.let { resourceType ->
                validation.checkTrue(
                    resourceType in supportedTypes,
                    InvalidReferenceType(Reference::reference, supportedTypes),
                    context
                )
            }
        }
    }

    private fun String?.asResourceType(): ResourceType? = this?.let { ResourceType.valueOf(it) }
}
