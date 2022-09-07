package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

enum class MedicationStatementStatus(@JsonValue override val code: String) : CodedEnum<MedicationStatementStatus> {
    ACTIVE("active"),
    COMPLETED("completed"),
    ENTERED_IN_ERROR("entered-in-error"),
    INTENDED("intended"),
    ON_HOLD("on-hold"),
    UNKNOWN("unknown"),
    NOT_TAKEN("not-taken"),
}
