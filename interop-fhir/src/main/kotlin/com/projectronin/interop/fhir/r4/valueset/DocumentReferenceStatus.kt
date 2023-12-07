package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

/**
 * See [FHIR Spec](https://hl7.org/fhir/r4/valueset-document-reference-status.html)
 */
enum class DocumentReferenceStatus(
    @JsonValue override val code: String,
) : CodedEnum<DocumentReferenceStatus> {
    /**
     * This is the current reference for this document.
     */
    CURRENT("current"),

    /**
     * This reference has been superseded by another reference.
     */
    SUPERSEDED("superseded"),

    /**
     * This reference was created in error.
     */
    ENTERED_IN_ERROR("entered-in-error"),
}
