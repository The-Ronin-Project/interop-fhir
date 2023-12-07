package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

/**
 * See [FHIR Spec](https://www.hl7.org/fhir/R4/valueset-encounter-status.html)
 */
enum class EncounterStatus(
    @JsonValue override val code: String,
) : CodedEnum<EncounterStatus> {
    PLANNED("planned"),
    ARRIVED("arrived"),
    TRIAGED("triaged"),
    IN_PROGRESS("in-progress"),
    ONLEAVE("onleave"),
    FINISHED("finished"),
    CANCELLED("cancelled"),
    ENTERED_IN_ERROR("entered-in-error"),
    UNKNOWN("unknown"),
}
