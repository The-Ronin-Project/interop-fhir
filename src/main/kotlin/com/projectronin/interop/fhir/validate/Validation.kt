package com.projectronin.interop.fhir.validate

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Validation can be utilized to capture multiple failure conditions and report them in a single instance.
 */
class Validation {
    private val exceptions = mutableListOf<Exception>()
    private val issues = mutableListOf<ValidationIssue>()

    /**
     * Checks that the [value] is true. If not, the [lazyMessage] is used to produce an error.
     */
    fun check(value: Boolean, lazyMessage: () -> Any) {
        try {
            require(value, lazyMessage)
        } catch (e: Exception) {
            exceptions.add(e)
        }
    }

    fun checkTrue(value: Boolean, issueToLog: ValidationIssue) {
        try {
            require(value)
        } catch (e: Exception) {
            issues.add(issueToLog)
        }
    }

    /**
     * Validates that the [value] is not null. If null, the [lazyMessage] is used to produce an error.
     */
    @OptIn(ExperimentalContracts::class)
    fun notNull(value: Any?, lazyMessage: () -> Any) {
        // Providing this contract, copied from requireNotNull, allows type-safe smart casts in consuming code.
        contract {
            returns() implies (value != null)
        }
        try {
            requireNotNull(value, lazyMessage)
        } catch (e: Exception) {
            exceptions.add(e)
        }
    }

    @OptIn(ExperimentalContracts::class)
    fun checkNotNull(value: Any?, issueToLog: ValidationIssue) {
        // Providing this contract, copied from requireNotNull, allows type-safe smart casts in consuming code.
        contract {
            returns() implies (value != null)
        }
        try {
            requireNotNull(value)
        } catch (e: Exception) {
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
     * Retrieves the current List of exceptions.
     */
    fun errors(): List<Exception> = exceptions
    fun issues(): List<ValidationIssue> = issues

    /**
     * Throws an [IllegalArgumentException] if any errors were captured during validation.
     */
    fun alertIfErrors() {
        val errors = issues.filter { it.severity == ValidationIssueSeverity.ERROR }
        if (errors.isNotEmpty()) throw IllegalArgumentException(
            errors.joinToString(separator = "\n", prefix = "Encountered validation error(s):\n")
        )

        when (exceptions.size) {
            0 -> return
            1 -> throw exceptions[0]
            else -> {
                val joinedMessage = exceptions.joinToString(separator = "\n") { it.message ?: it.toString() }
                throw IllegalArgumentException("Encountered multiple validation errors:\n$joinedMessage")
            }
        }
    }

    /**
     * Merges the [other] Validation with this instance.
     */
    fun merge(other: Validation) {
        exceptions.addAll(other.errors())
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
