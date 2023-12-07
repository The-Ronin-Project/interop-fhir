package com.projectronin.interop.fhir.r4.valueset

import com.projectronin.interop.common.enums.CodedEnum

/**
 * See [FHIR Spec](https://www.hl7.org/fhir/R4/valueset-request-priority.html)
 */
enum class RequestPriority(override val code: String) : CodedEnum<RequestPriority> {
    /**
     * The request has normal priority.
     */
    ROUTINE("routine"),

    /**
     * The request should be actioned promptly - higher priority than routine.
     */
    URGENT("urgent"),

    /**
     * The request should be actioned as soon as possible - higher priority than urgent.
     */
    ASAP("asap"),

    /**
     * The request should be actioned immediately - highest possible priority. E.g. an emergency.
     */
    STAT("stat"),
}
