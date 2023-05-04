package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

enum class RequestGroupActionSelectionBehavior(@JsonValue override val code: String) : CodedEnum<RequestGroupActionSelectionBehavior> {
    ANY("any"),
    ALL("all"),
    ALL_OR_NONE("all-or-none"),
    EXACTLY_ONE("exactly-one"),
    AT_MOST_ONE("at-most-one"),
    ONE_OR_NONE("one-or-none")
}
