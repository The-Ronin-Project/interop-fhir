package com.projectronin.interop.fhir.r4.valueset

import com.projectronin.interop.common.enums.CodedEnum

/**
 * See [FHIR Spec](http://hl7.org/fhir/R4/valueset-condition-ver-status.html)
 */
enum class ConditionVerificationStatus(override val code: String) : CodedEnum<ConditionVerificationStatus> {
    UNCONFIRMED("unconfirmed"),
    PROVISIONAL("provisional"),
    DIFFERENTIAL("differential"),
    CONFIRMED("confirmed"),
    REFUTED("refuted"),
    ENTERED_IN_ERROR("entered-in-error"),
}
