package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

@Deprecated("Use RequestStatus")
enum class RequestGroupStatus(@JsonValue override val code: String) : CodedEnum<RequestGroupStatus> {
    DRAFT("draft"),
    ACTIVE("active"),
    ON_HOLD("on-hold"),
    REVOKED("revoked"),
    COMPLETED("completed"),
    ENTERED_IN_ERROR("entered-in-error"),
    UNKNOWN("unknown")
}
