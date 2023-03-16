package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

enum class CareTeamStatus(@JsonValue override val code: String) : CodedEnum<CareTeamStatus> {
    PROPOSED("proposed"),
    ACTIVE("active"),
    SUSPENDED("suspended"),
    INACTIVE("inactive"),
    ENTERED_IN_ERROR("entered-in-error")
}
