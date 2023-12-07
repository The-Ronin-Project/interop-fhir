package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

enum class EventStatus(
    @JsonValue override val code: String,
) : CodedEnum<EventStatus> {
    /**
     * The core event has not started yet, but some staging activities have begun (e.g. surgical suite preparation).
     * Preparation stages may be tracked for billing purposes.
     */
    PREPARATION("preparation"),

    /**
     * The event is currently occurring.
     */
    IN_PROGRESS("in-progress"),

    /**
     * The event was terminated prior to any activity beyond preparation. I.e. The 'main' activity has not yet begun.
     * The boundary between preparatory and the 'main' activity is context-specific.
     */
    NOT_DONE("not-done"),

    /**
     * The event has been temporarily stopped but is expected to resume in the future.
     */
    ON_HOLD("on-hold"),

    /**
     * The event was terminated prior to the full completion of the intended activity but after at least some of
     * the 'main' activity (beyond preparation) has occurred.
     */
    STOPPED("stopped"),

    /**
     * The event has now concluded.
     */
    COMPLETED("completed"),

    /**
     * This electronic record should never have existed, though it is possible that real-world decisions were based on
     * it. (If real-world activity has occurred, the status should be "stopped" rather than "entered-in-error".).
     */
    ENTERED_IN_ERROR("entered-in-error"),

    /**
     * The authoring/source system does not know which of the status values currently applies for this event.
     * Note: This concept is not to be used for "other" - one of the listed statuses is presumed to apply,
     * but the authoring/source system does not know which.
     */
    UNKNOWN("unknown"),
}
