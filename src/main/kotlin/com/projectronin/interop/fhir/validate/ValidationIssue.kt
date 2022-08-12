package com.projectronin.interop.fhir.validate

data class ValidationIssue(
    val severity: ValidationIssueSeverity,
    val code: String,
    val description: String,
    val location: String
) {
    override fun toString(): String {
        return "${severity.name} $code: $description @ $location"
    }
}

enum class ValidationIssueSeverity {
    ERROR, WARNING
}
