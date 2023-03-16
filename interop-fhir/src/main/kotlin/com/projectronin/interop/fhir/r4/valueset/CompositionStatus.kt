package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

/**
 * See [FHIR Spec](https://hl7.org/fhir/r4/valueset-composition-status.html)
 */
enum class CompositionStatus(@JsonValue override val code: String) : CodedEnum<CompositionStatus> {
    /**
     * This is a preliminary composition or document (also known as initial or interim). The content may be incomplete or unverified.
     */
    PRELIMINARY("preliminary"),

    /**
     * This version of the composition is complete and verified by an appropriate person and no further work is planned.
     * Any subsequent updates would be on a new version of the composition.
     */
    FINAL("final"),

    /**
     * The composition content or the referenced resources have been modified (edited or added to) subsequent to being
     * released as "final" and the composition is complete and verified by an authorized person.
     */
    AMENDED("amended"),

    /**
     * The composition or document was originally created/issued in error, and this is an amendment that marks that the
     * entire series should not be considered as valid.
     */
    ENTERED_IN_ERROR("entered-in-error")
}
