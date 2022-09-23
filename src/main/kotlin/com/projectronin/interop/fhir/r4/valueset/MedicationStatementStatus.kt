package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

/**
 * See [FHIR Spec](http://hl7.org/fhir/valueset-medication-statement-status.html)
 */
enum class MedicationStatementStatus(@JsonValue override val code: String) : CodedEnum<MedicationStatementStatus> {
    /**
     * The medication is still being taken.
     */
    ACTIVE("active"),

    /**
     * The medication is no longer being taken.
     */
    COMPLETED("completed"),

    /**
     * Some of the actions that are implied by the medication statement may have occurred. For example, the patient
     * may have taken some of the medication. Clinical decision support systems should take this status into account.
     */
    ENTERED_IN_ERROR("entered-in-error"),

    /**
     * The medication may be taken at some time in the future.
     */
    INTENDED("intended"),

    /**
     * Actions implied by the statement have been temporarily halted, but are expected to continue later.
     * May also be called 'suspended'.
     */
    ON_HOLD("on-hold"),

    /**
     * The state of the medication use is not currently known.
     */
    UNKNOWN("unknown"),

    /**
     * The medication was not consumed by the patient
     */
    NOT_TAKEN("not-taken"),

    /**
     * Actions implied by the statement have been permanently halted, before all of them occurred.
     * This should not be used if the statement was entered in error.
     */
    STOPPED("stopped");
}
