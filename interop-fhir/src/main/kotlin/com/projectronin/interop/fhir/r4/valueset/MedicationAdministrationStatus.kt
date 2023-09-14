package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

/**
 * See [FHIR Spec](http://hl7.org/fhir/valueset-medication-administration-status.html)
 */
enum class MedicationAdministrationStatus(@JsonValue override val code: String) : CodedEnum<MedicationAdministrationStatus> {
    /**
     * The administration has started but has not yet completed.
     */
    IN_PROGRESS("in-progress"),

    /**
     * The administration was terminated prior to any impact on the subject (though preparatory actions may have been taken)
     */
    NOT_DONE("not-done"),

    /**
     * The administration was entered in error and therefore nullified.
     */
    ENTERED_IN_ERROR("entered-in-error"),

    /**
     * All actions that are implied by the administration have occurred.
     */
    COMPLETED("completed"),

    /**
     * Actions implied by the administration have been temporarily halted, but are expected to continue later.
     * May also be called 'suspended'.
     */
    ON_HOLD("on-hold"),

    /**
     * The authoring system does not know which of the status values currently applies for this request.
     * Note: This concept is not to be used for 'other' - one of the listed statuses is presumed to apply,
     * it's just not known which one.
     */
    UNKNOWN("unknown"),

    /**
     * Actions implied by the administration have been permanently halted, before all of them occurred.
     */
    STOPPED("stopped");
}
