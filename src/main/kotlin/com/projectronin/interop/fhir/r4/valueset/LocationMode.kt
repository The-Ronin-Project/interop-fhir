package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

/**
 * See [FHIR Spec](https://hl7.org/fhir/R4/valueset-location-mode.html)
 */
enum class LocationMode(@JsonValue override val code: String) : CodedEnum<LocationMode> {
    INSTANCE("instance"),
    KIND("kind")
}
