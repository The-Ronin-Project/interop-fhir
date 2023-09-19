package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

@Deprecated("Use RequestIntent")
enum class RequestGroupIntent(@JsonValue override val code: String) : CodedEnum<RequestGroupIntent> {
    PROPOSAL("proposal"),
    PLAN("plan"),
    DIRECTIVE("directive"),
    ORDER("order"),
    ORIGINAL_ORDER("original-order"),
    REFLEX_ORDER("reflex-order"),
    FILLER_ORDER("filler-order"),
    INSTANCE_ORDER("instance-order"),
    OPTION("option")
}
