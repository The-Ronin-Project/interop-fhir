package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

/**
 * See [FHIR Spec](https://www.hl7.org/fhir/R4/valueset-medication-status.html)
 */
enum class MedicationStatus(
    @JsonValue override val code: String,
) : CodedEnum<MedicationStatus> {
    /**
     * The medication is available for use.
     */
    ACTIVE("active"),

    /**
     * The medication is not available for use.
     */
    INACTIVE("inactive"),

    /**
     * The medication was entered in error.
     */
    ENTERED_IN_ERROR("entered-in-error"),
}
