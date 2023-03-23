package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

enum class PublicationStatus(@JsonValue override val code: String) : CodedEnum<PublicationStatus> {
    /**
     * This resource is still under development and is not yet considered to be ready for normal use.
     */
    DRAFT("draft"),

    /**
     * This resource is ready for normal use.
     */
    ACTIVE("active"),

    /**
     * This resource has been withdrawn or superseded and should no longer be used.
     */
    RETIRED("retired"),

    /**
     * The authoring system does not know which of the status values currently applies for this resource.
     * Note: This concept is not to be used for "other" - one of the listed statuses is presumed to apply,
     * it's just not known which one.
     */
    UNKNOWN("unknown")
}
