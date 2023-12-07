package com.projectronin.interop.fhir.r4.valueset

import com.projectronin.interop.common.enums.CodedEnum

/**
 * See [FHIR Spec](https://www.hl7.org/fhir/R4/valueset-medicationrequest-intent.html)
 */
enum class MedicationRequestIntent(override val code: String) : CodedEnum<MedicationRequestIntent> {
    /**
     * The request is a suggestion made by someone/something that doesn't have an intention to ensure it occurs and
     * without providing an authorization to act
     */
    PROPOSAL("proposal"),

    /**
     * The request represents an intention to ensure something occurs without providing an authorization for others to act.
     */
    PLAN("plan"),

    /**
     * The request represents a request/demand and authorization for action
     */
    ORDER("order"),

    /**
     * The request represents the original authorization for the medication request.
     */
    ORIGINAL_ORDER("original-order"),

    /**
     * The request represents an automatically generated supplemental authorization for action based on a parent
     * authorization together with initial results of the action taken against that parent authorization.
     */
    REFLEX_ORDER("reflex-order"),

    /**
     * The request represents the view of an authorization instantiated by a fulfilling system representing the details
     * of the fulfiller's intention to act upon a submitted order.
     */
    FILLER_ORDER("filler-order"),

    /**
     * The request represents an instance for the particular order, for example a medication administration record.
     */
    INSTANCE_ORDER("instance-order"),

    /**
     * The request represents a component or option for a RequestGroup that establishes timing, conditionality and/or
     * other constraints among a set of requests.
     */
    OPTION("option"),
}
