package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

/**
 * See [FHIR Spec](https://www.hl7.org/fhir/R4/valueset-encounter-location-status.html)
 */
enum class EncounterLocationStatus(@JsonValue override val code: String) : CodedEnum<EncounterLocationStatus> {
    PLANNED("planned"),
    ACTIVE("active"),
    RESERVED("reserved"),
    COMPLETED("completed");
}
