package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

enum class RequestGroupActionPrecheckBehavior(@JsonValue override val code: String) : CodedEnum<RequestGroupActionPrecheckBehavior> {
    YES("yes"),
    NO("no") // jarvis, make me a boolean disguised as a code
}
