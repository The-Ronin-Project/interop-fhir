package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

/**
 * See [FHIR Spec](http://hl7.org/fhir/R4/valueset-observation-status.html)
 */
enum class ObservationStatus constructor(
    @JsonValue override val code: String,
) : CodedEnum<ObservationStatus> {
    REGISTERED("registered"),
    PRELIMINARY("preliminary"),
    FINAL("final"),
    AMENDED("amended"),
    CORRECTED("corrected"),
    CANCELLED("cancelled"),
    ENTERED_IN_ERROR("entered-in-error"),
    UNKNOWN("unknown"),
}
