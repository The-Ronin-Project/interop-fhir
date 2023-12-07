package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

enum class ConceptMapEquivalence(
    @JsonValue override val code: String,
) : CodedEnum<ConceptMapEquivalence> {
    RELATEDTO("relatedto"),
    EQUIVALENT("equivalent"),
    EQUAL("equal"),
    WIDER("wider"),
    SUBSUMES("subsumes"),
    NARROWER("narrower"),
    SPECIALIZES("specializes"),
    INEXACT("inexact"),
    UNMATCHED("unmatched"),
    DISJOINT("disjoint"),
}
