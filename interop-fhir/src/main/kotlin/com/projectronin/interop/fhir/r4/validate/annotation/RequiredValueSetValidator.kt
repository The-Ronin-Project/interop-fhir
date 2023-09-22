package com.projectronin.interop.fhir.r4.validate.annotation

import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validatable
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.annotation.RequiredValueSet
import com.projectronin.interop.fhir.validate.append
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.isSubclassOf

/**
 * Validator for [RequiredValueSet]
 */
class RequiredValueSetValidator : BaseValidationAnnotationValidator<RequiredValueSet>(RequiredValueSet::class) {
    override fun <T : Validatable<T>> validateSupportedAnnotations(
        annotations: List<RequiredValueSet>,
        property: KProperty1<T, *>,
        kotlinType: KClass<*>,
        element: T,
        elementName: String,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        val enumClass = annotations.singleOrNull()?.value ?: return

        if (kotlinType.isSubclassOf(Code::class)) {
            val code = property.get(element) as? Code
            code?.let {
                validation.checkCodedEnum(
                    code,
                    enumClass,
                    InvalidValueSetError(LocationContext(elementName, property.name), code.value),
                    parentContext
                )
            }
        } else if (kotlinType.isSubclassOf(Collection::class)) {
            (property.get(element) as? Collection<*>)?.let { collection ->
                collection.filterIsInstance<Code>()
                    .forEachIndexed { index, code ->
                        code.let {
                            val indexedContext =
                                parentContext.append(LocationContext(elementName, "${property.name}[$index]"))

                            validation.checkCodedEnum(
                                code,
                                enumClass,
                                InvalidValueSetError(indexedContext, code.value),
                                parentContext
                            )
                        }
                    }
            }
        }
    }
}
