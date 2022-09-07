package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

enum class ConceptMapMode(@JsonValue override val code: String) : CodedEnum<ConceptMapMode> {
    PROVIDED("provided"),
    FIXED("fixed"),
    OTHER_MAP("other-map")
}
