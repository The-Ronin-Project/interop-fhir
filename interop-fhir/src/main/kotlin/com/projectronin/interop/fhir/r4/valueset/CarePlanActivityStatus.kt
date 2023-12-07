package com.projectronin.interop.fhir.r4.valueset

import com.projectronin.interop.common.enums.CodedEnum

/**
 * See [FHIR Spec](http://hl7.org/fhir/valueset-care-plan-activity-status.html)
 */
enum class CarePlanActivityStatus(override val code: String) : CodedEnum<CarePlanActivityStatus> {
    /**
     * Care plan activity is planned but no action has yet been taken.
     */
    NOT_STARTED("not-started"),

    /**
     * Appointment or other booking has occurred but activity has not yet begun.
     */
    SCHEDULED("scheduled"),

    /**
     * Care plan activity has been started but is not yet complete.
     */
    IN_PROGRESS("in-progress"),

    /**
     * Care plan activity was started but has temporarily ceased with an expectation of resumption at a future time.
     */
    ON_HOLD("on-hold"),

    /**
     * Care plan activity has been completed (more or less) as planned.
     */
    COMPLETED("completed"),

    /**
     * The planned care plan activity has been withdrawn.
     */
    CANCELLED("cancelled"),

    /**
     * The planned care plan activity has been ended prior to completion after the activity was started.
     */
    STOPPED("stopped"),

    /**
     * The current state of the care plan activity is not known. Note: This concept is not to be used for "other" -
     * one of the listed statuses is presumed to apply, but the authoring/source system does not know which one.
     */
    UNKNOWN("unknown"),

    /**
     * Care plan activity was entered in error and voided.
     */
    ENTERED_IN_ERROR("entered-in-error"),
}
