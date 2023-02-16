package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

/**
 * See [FHIR Spec](http://hl7.org/fhir/ValueSet/location-status.html)
 */
enum class LocationStatus(@JsonValue override val code: String) : CodedEnum<LocationStatus> {
    ACTIVE("active"),
    SUSPENDED("suspended"),
    INACTIVE("inactive")
}
