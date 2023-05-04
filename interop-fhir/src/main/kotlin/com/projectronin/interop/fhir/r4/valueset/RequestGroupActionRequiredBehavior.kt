package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

enum class RequestGroupActionRequiredBehavior(@JsonValue override val code: String) : CodedEnum<RequestGroupActionRequiredBehavior> {
    MUST("must"),
    COULD("could"),
    MUST_UNLESS_DOCUMENTED("must-unless-documented")
}
