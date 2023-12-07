package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

enum class RequestGroupConditionKind(
    @JsonValue override val code: String,
) : CodedEnum<RequestGroupConditionKind> {
    APPLICABILITY("applicability"),
    START("start"),
    STOP("stop"),
}
