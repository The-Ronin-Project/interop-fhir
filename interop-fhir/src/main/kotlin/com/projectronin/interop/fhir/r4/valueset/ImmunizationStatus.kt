package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

/**
 * See [FHIR Spec](https://hl7.org/fhir/r4/valueset-immunization-status.html)
 */
enum class ImmunizationStatus(@JsonValue override val code: String) : CodedEnum<ImmunizationStatus> {
    /**
     * The event has now concluded.
     */
    COMPLETED("completed"),

    /**
     * This electronic record should never have existed, though it is possible that real-world decisions were based on it. (If real-world activity has occurred, the status should be "stopped" rather than "entered-in-error".).
     */
    ENTERED_IN_ERROR("entered-in-error"),

    /**
     * The event was terminated prior to any activity beyond preparation. I.e. The 'main' activity has not yet begun. The boundary between preparatory and the 'main' activity is context-specific.
     */
    NOT_DONE("not-done")
}
