package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

/**
 * See [FHIR Spec](http://hl7.org/fhir/R4/valueset-trigger-type.html)
 */
enum class TriggerType(
    @JsonValue override val code: String,
) : CodedEnum<TriggerType> {
    NAMED_EVENT("named-event"),
    PERIODIC("periodic"),
    DATA_CHANGED("data-changed"),
    DATA_ADDED("data-added"),
    DATA_MODIFIED("data-modified"),
    DATA_REMOVED("data-removed"),
    DATA_ACCESSED("data-accessed"),
    DATA_ACCESS_ENDED("data-access-ended"),
}
