package com.projectronin.interop.fhir.r4.valueset

import com.projectronin.interop.common.enums.CodedEnum

/**
 * See [FHIR Spec](http://hl7.org/fhir/valueset-care-plan-intent.html)
 */
enum class CarePlanIntent(override val code: String) : CodedEnum<CarePlanIntent> {
    /**
     * The request is a suggestion made by someone/something that does not have an intention to ensure it occurs and
     * without providing an authorization to act.
     */
    PROPOSAL("proposal"),

    /**
     * The request represents an intention to ensure something occurs without providing an authorization for others to act.
     */
    PLAN("plan"),

    /**
     * The request represents a request/demand and authorization for action by a Practitioner.
     */
    ORDER("order"),

    /**
     * The request represents a component or option for a RequestGroup that establishes timing, conditionality and/or
     * other constraints among a set of requests. Refer to [[[RequestGroup]]] for additional information on how this
     * status is used.
     */
    OPTION("option"),
}
