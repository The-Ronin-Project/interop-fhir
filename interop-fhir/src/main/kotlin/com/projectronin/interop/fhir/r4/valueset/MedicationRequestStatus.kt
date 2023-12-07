package com.projectronin.interop.fhir.r4.valueset

import com.projectronin.interop.common.enums.CodedEnum

/**
 * See [FHIR Spec](https://www.hl7.org/fhir/R4/valueset-medicationrequest-status.html)
 */
enum class MedicationRequestStatus(override val code: String) : CodedEnum<MedicationRequestStatus> {
    /**
     * The prescription is 'actionable', but not all actions that are implied by it have occurred yet.
     */
    ACTIVE("active"),

    /**
     * Actions implied by the prescription are to be temporarily halted, but are expected to continue later. May also
     * be called 'suspended'.
     */
    ON_HOLD("on-hold"),

    /**
     * The prescription has been withdrawn before any administrations have occurred
     */
    CANCELLED("cancelled"),

    /**
     * All actions that are implied by the prescription have occurred.
     */
    COMPLETED("completed"),

    /**
     * Some of the actions that are implied by the medication request may have occurred. For example, the medication may
     * have been dispensed and the patient may have taken some of the medication. Clinical decision support systems
     * should take this status into account
     */
    ENTERED_IN_ERROR("entered-in-error"),

    /**
     * Actions implied by the prescription are to be permanently halted, before all of the administrations occurred.
     * This should not be used if the original order was entered in error
     */
    STOPPED("stopped"),

    /**
     * 	The prescription is not yet 'actionable', e.g. it is a work in progress, requires sign-off, verification or
     * 	needs to be run through decision support process.
     */
    DRAFT("draft"),

    /**
     * 	The authoring/source system does not know which of the status values currently applies for this observation.
     * 	Note: This concept is not to be used for 'other' - one of the listed statuses is presumed to apply, but the
     * 	authoring/source system does not know which.
     */
    UNKNOWN("unknown"),
}
