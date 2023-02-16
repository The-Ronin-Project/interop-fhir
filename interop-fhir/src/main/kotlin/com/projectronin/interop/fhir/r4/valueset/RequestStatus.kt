package com.projectronin.interop.fhir.r4.valueset

import com.projectronin.interop.common.enums.CodedEnum

/**
 * See [FHIR Spec](http://hl7.org/fhir/valueset-request-status.html)
 */
enum class RequestStatus(override val code: String) : CodedEnum<RequestStatus> {
    /**
     * The request has been created but is not yet complete or ready for action.
     */
    DRAFT("draft"),

    /**
     * The request is in force and ready to be acted upon.
     */
    ACTIVE("active"),

    /**
     * The request (and any implicit authorization to act) has been temporarily withdrawn but is expected to resume in
     * the future.
     */
    ON_HOLD("on-hold"),

    /**
     * The request (and any implicit authorization to act) has been terminated prior to the known full completion of
     * the intended actions. No further activity should occur.
     */
    REVOKED("revoked"),

    /**
     * 	The activity described by the request has been fully performed. No further activity will occur.
     */
    COMPLETED("completed"),

    /**
     * This request should never have existed and should be considered 'void'. (It is possible that real-world
     * decisions were based on it. If real-world activity has occurred, the status should be "revoked" rather
     * than "entered-in-error".).
     */
    ENTERED_IN_ERROR("entered-in-error"),

    /**
     * The authoring/source system does not know which of the status values currently applies for this request.
     * Note: This concept is not to be used for "other" - one of the listed statuses is presumed to apply, but the
     * authoring/source system does not know which.
     */
    UNKNOWN("unknown")
}
