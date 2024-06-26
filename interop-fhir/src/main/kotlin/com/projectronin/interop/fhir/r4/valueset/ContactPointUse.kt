package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

/**
 * See [FHIR Spec](https://www.hl7.org/fhir/valueset-contact-point-use.html)
 */
enum class ContactPointUse(
    @JsonValue override val code: String,
) : CodedEnum<ContactPointUse> {
    /**
     * A communication contact point at a home; attempted contacts for business purposes might intrude privacy and chances are one will contact family or other household members instead of the person one wishes to call. Typically used with urgent cases, or if no other contacts are available.
     */
    HOME("home"),

    /**
     * An office contact point. First choice for business related contacts during business hours.
     */
    WORK("work"),

    /**
     * A temporary contact point. The period can provide more detailed information.
     */
    TEMPORARY("temp"),

    /**
     * This contact point is no longer in use (or was never correct, but retained for records)
     */
    OLD("old"),

    /**
     * A telecommunication device that moves and stays with its owner. May have characteristics of all other use codes, suitable for urgent matters, not the first choice for routine business.
     */
    MOBILE("mobile"),
}
