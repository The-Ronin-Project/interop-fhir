package com.projectronin.interop.fhir.validate

import com.projectronin.interop.common.enums.CodedEnum
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Validation can be utilized to capture multiple failure conditions and report them in a single instance.
 */
class Validation {
    private val issues = mutableListOf<ValidationIssue>()

    /**
     * Checks that the [value] is true. If not, the [error] is logged, using an optional [lazyDescription].
     */
    fun checkTrue(
        value: Boolean,
        error: FHIRError,
        parentContext: LocationContext?,
        lazyDescription: (() -> String)? = null
    ) {
        if (!value) {
            issues.add(error.toValidationIssue(lazyDescription?.invoke(), parentContext))
        }
    }

    /**
     * Checks that the [value] is not null. If not, the [error] is logged, using an optional [lazyDescription].
     */
    @OptIn(ExperimentalContracts::class)
    fun checkNotNull(
        value: Any?,
        error: FHIRError,
        parentContext: LocationContext?,
        lazyDescription: (() -> String)? = null
    ) {
        // Providing this contract, copied from requireNotNull, allows type-safe smart casts in consuming code.
        contract {
            returns() implies (value != null)
        }
        if (value == null) {
            issues.add(error.toValidationIssue(lazyDescription?.invoke(), parentContext))
        }
    }

    /**
     * Checks that the [code] is a valid code for enum [T], returning the enum if valid.
     * If not, null is returned, and the [error] is logged using an optional [lazyDescription].
     */
    inline fun <reified T> checkCodedEnum(
        code: Code,
        error: InvalidValueSetError,
        parentContext: LocationContext?,
        noinline lazyDescription: (() -> String)? = null
    ): T? where T : Enum<T>, T : CodedEnum<T> {
        return code.value?.let {
            val codified = runCatching { CodedEnum.byCode<T>(it) }.getOrNull()
            checkNotNull(codified, error, parentContext, lazyDescription)
            codified
        }
    }

    @OptIn(ExperimentalContracts::class)
    fun checkNotNull(value: Any?, issueToLog: ValidationIssue) {
        // Providing this contract, copied from requireNotNull, allows type-safe smart casts in consuming code.
        contract {
            returns() implies (value != null)
        }
        if (value == null) {
            issues.add(issueToLog)
        }
    }

    /**
     * If the [value] is not null, then the [block] can be run to continue specific validation in a null-safe manner.
     */
    fun ifNotNull(value: Any?, block: Validation.() -> Unit) {
        value?.let {
            this.apply(block)
        }
    }

    /**
     * True if at least one issue was identified with an Error severity.
     */
    fun hasErrors(): Boolean = issues.any { it.severity == ValidationIssueSeverity.ERROR }

    /**
     * True if any issues were identified, regardless of severity.
     */
    fun hasIssues(): Boolean = issues.isNotEmpty()

    /**
     * List of all issues encountered
     */
    fun issues(): List<ValidationIssue> = issues

    /**
     * Throws an [IllegalArgumentException] if any errors were captured during validation.
     */
    fun alertIfErrors() {
        val errors = issues.filter { it.severity == ValidationIssueSeverity.ERROR }
        if (errors.isNotEmpty()) {
            throw IllegalArgumentException(
                errors.joinToString(separator = "\n", prefix = "Encountered validation error(s):\n")
            )
        }
    }

    /**
     * Merges the [other] Validation with this instance.
     */
    fun merge(other: Validation) {
        issues.addAll(other.issues)
    }
}

/**
 * Simple function for creating an initial [Validation] with the [block] applied to it.
 */
fun validation(block: Validation.() -> Unit) = Validation().apply(block)

/**
 * Creates a [Validation], defines it with the [block] and throws an exception if any errors were encountered.
 */
fun validateAndAlert(block: Validation.() -> Unit) = validation(block).alertIfErrors()
