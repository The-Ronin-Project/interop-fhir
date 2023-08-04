package com.projectronin.interop.fhir.validate

import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.primitive.Primitive
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

/**
 * Defines a FHIR Error.
 */
open class FHIRError(
    val code: String,
    val severity: ValidationIssueSeverity,
    val description: String,
    val location: LocationContext?,
    val metadata: List<IssueMetadata>? = listOf<IssueMetadata>()
) {
    /**
     * Creates a [ValidationIssue] based off this error.
     * If an [overriddenDescription] is provided, it will be used instead of the error's description.
     * If a [parentContext] is provided, the [location] will be treated as a child element.
     */
    fun toValidationIssue(overriddenDescription: String? = null, parentContext: LocationContext? = null) =
        ValidationIssue(
            code = code,
            severity = severity,
            description = overriddenDescription ?: description,
            location = location?.let { parentContext.append(it) } ?: parentContext,
            metadata = metadata
        )
}

/**
 * Defines an error for required field.
 */
class RequiredFieldError(actualLocation: LocationContext) :
    FHIRError(
        "REQ_FIELD",
        ValidationIssueSeverity.ERROR,
        "${actualLocation.field} is a required element",
        actualLocation
    ) {
    /**
     * Creates a RequiredFieldError based off an explicit property.
     */
    constructor(actualLocation: KProperty1<*, *>) : this(LocationContext(actualLocation))
}

/**
 * Defines an error for an invalid primitive.
 */
class InvalidPrimitiveError(primitiveType: KClass<out Primitive<*, *>>) : FHIRError(
    "R4_INV_PRIM",
    ValidationIssueSeverity.ERROR,
    "Supplied value is not valid for a ${primitiveType.simpleName}",
    null
)

/**
 * Defines an error for an invalid dynamic value type.
 */
class InvalidDynamicValueError(
    actualLocation: LocationContext,
    validTypes: List<DynamicValueType>
) :
    FHIRError(
        "INV_DYN_VAL",
        ValidationIssueSeverity.ERROR,
        "${actualLocation.field} can only be one of the following: ${validTypes.joinToString { it.code }}",
        actualLocation
    ) {
    /**
     * Creates an InvalidDynamicValueError based off an explicit property.
     */
    constructor(actualLocation: KProperty1<*, DynamicValue<*>?>, validTypes: List<DynamicValueType>) : this(
        LocationContext(actualLocation),
        validTypes
    )
}

/**
 * Defines an error for a value outside of a required value set.
 */
class InvalidValueSetError(actualLocation: LocationContext, value: String) : FHIRError(
    "INV_VALUE_SET",
    ValidationIssueSeverity.ERROR,
    "${
    if (value.contains(",")) {
        "'${value.replace(", ", "', '")}' are"
    } else {
        "'$value' is"
    }
    } outside of required value set",
    actualLocation
) {
    /**
     * Creates an InvalidValueSetError based off an explicit property.
     */
    constructor(actualLocation: KProperty1<*, *>, value: String?) : this(
        LocationContext(actualLocation),
        value ?: "null"
    )
}
