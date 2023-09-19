package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

enum class RequestIntent(@JsonValue override val code: String) : CodedEnum<RequestIntent> {
    /**
     * The request is a suggestion made by someone/something that does not have an intention to ensure it occurs and
     * without providing an authorization to act.
     */
    PROPOSAL("proposal"),

    /**
     * 	The request represents an intention to ensure something occurs without providing an authorization for others to
     * 	act.
     */
    PLAN("plan"),

    /**
     * The request represents a legally binding instruction authored by a Patient or RelatedPerson.
     */
    DIRECTIVE("directive"),

    /**
     * The request represents a request/demand and authorization for action by a Practitioner.
     */
    ORDER("order"),

    /**
     * The request represents an original authorization for action.
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
     * An order created in fulfillment of a broader order that represents the authorization for a single activity
     * occurrence. E.g. The administration of a single dose of a drug.
     */
    INSTANCE_ORDER("instance-order"),

    /**
     * 	The request represents a component or option for a RequestGroup that establishes timing, conditionality and/or
     * 	other constraints among a set of requests. Refer to [[[RequestGroup]]] for additional information on how this
     * 	status is used.
     */
    OPTION("option")
}
