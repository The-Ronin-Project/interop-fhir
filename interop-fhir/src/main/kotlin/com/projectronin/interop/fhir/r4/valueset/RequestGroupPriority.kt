package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

@Deprecated("Use RequestPriority")
enum class RequestGroupPriority(
    @JsonValue override val code: String,
) : CodedEnum<RequestGroupPriority> {
    ROUTINE("routine"),
    URGENT("urgent"),
    ASAP("asap"),
    STAT("stat"),
}
