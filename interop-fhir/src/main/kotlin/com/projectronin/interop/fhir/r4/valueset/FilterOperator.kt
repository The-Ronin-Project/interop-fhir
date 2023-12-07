package com.projectronin.interop.fhir.r4.valueset

import com.projectronin.interop.common.enums.CodedEnum

enum class FilterOperator(override val code: String) : CodedEnum<FilterOperator> {
    /**
     * The specified property of the code equals the provided value.
     */
    EQUALS("="),

    /**
     * Includes all concept ids that have a transitive is-a relationship with the concept Id provided as the value,
     * including the provided concept itself (include descendant codes and self).
     */
    IS_A("is-a"),

    /**
     * Includes all concept ids that have a transitive is-a relationship with the concept Id provided as the value,
     * excluding the provided concept itself i.e. include descendant codes only).
     */
    DESCENDENT_OF("descendent-of"),

    /**
     * The specified property of the code does not have an is-a relationship with the provided value.
     */
    NOT_IS_A("is-not-a"),

    /**
     * The specified property of the code does not have an is-a relationship with the provided value.
     */
    REGULAR_EXPRESSION("regex"),

    /**
     * The specified property of the code does not have an is-a relationship with the provided value.
     */
    IN_SET("in"),

    /**
     * The specified property of the code does not have an is-a relationship with the provided value.
     */
    NOT_IN_SET("not-in"),

    /**
     * Includes all concept ids that have a transitive is-a relationship from the concept Id provided as the value,
     * including the provided concept itself (i.e. include ancestor codes and self).
     */
    GENERALIZES("generalizes"),

    /**
     * The specified property of the code has at least one value (if the specified value is true; if the specified value is false, then matches when the specified property of the code has no values).
     */
    EXISTS("exists"),
}
