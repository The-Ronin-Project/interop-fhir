package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

enum class RequestGroupRelatedActionRelationship(
    @JsonValue override val code: String,
) : CodedEnum<RequestGroupRelatedActionRelationship> {
    BEFORE_START("before-start"),
    BEFORE("before"),
    BEFORE_END("before-end"),
    CONCURRENT_WITH_START("concurrent-with-start"),
    CONCURRENT("concurrent"),
    CONCURRENT_WITH_END("concurrent-with-end"),
    AFTER_START("after-start"),
    AFTER("after"),
    AFTER_END("after-end"),
}
