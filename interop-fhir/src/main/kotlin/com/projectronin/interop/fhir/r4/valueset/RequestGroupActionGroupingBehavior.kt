package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

enum class RequestGroupActionGroupingBehavior(@JsonValue override val code: String) : CodedEnum<RequestGroupActionGroupingBehavior> {
    VISUAL_GROUP("visual-group"),
    LOGICAL_GROUP("logical-group"),
    SENTENCE_GROUP("sentence-group")
}
