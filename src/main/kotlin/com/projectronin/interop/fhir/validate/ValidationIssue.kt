package com.projectronin.interop.fhir.validate

import com.projectronin.interop.fhir.r4.validate.FHIRError

data class ValidationIssue(
    val severity: ValidationIssueSeverity,
    val code: String,
    val description: String,
    val location: String? = null
) {
    override fun toString(): String {
        return "${severity.name} $code: $description ${location?.let { "@ $it" }}"
    }

    constructor(errorEnum: FHIRError, location: String? = null) : this(
        severity = errorEnum.severity,
        code = errorEnum.toString(),
        description = errorEnum.description,
        location = location ?: errorEnum.location
    )
}

enum class ValidationIssueSeverity {
    ERROR, WARNING
}
