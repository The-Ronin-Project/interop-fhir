package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

enum class RequestGroupActionCardinalityBehavior(
    @JsonValue override val code: String,
) : CodedEnum<RequestGroupActionCardinalityBehavior> {
    SINGLE("single"),
    MULTIPLE("multiple"),
}
