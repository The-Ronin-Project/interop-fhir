package com.projectronin.interop.fhir.validate

data class ValidationIssue(
    val severity: ValidationIssueSeverity,
    val code: String,
    val description: String,
    val location: LocationContext? = null,
    val metadata: List<IssueMetadata>? = listOf<IssueMetadata>()
) {
    override fun toString(): String {
        return "${severity.name} $code: $description${location?.let { " @ $it" } ?: ""}"
    }
}

enum class ValidationIssueSeverity {
    ERROR, WARNING
}
