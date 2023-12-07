package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

enum class ConceptMapStatus(
    @JsonValue override val code: String,
) : CodedEnum<ConceptMapStatus> {
    DRAFT("draft"),
    ACTIVE("active"),
    RETIRED("retired"),
    UNKNOWN("unknown"),
}
