package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.resource.RequestGroup
import com.projectronin.interop.fhir.r4.resource.RequestGroupAction
import com.projectronin.interop.fhir.r4.resource.RequestGroupCondition
import com.projectronin.interop.fhir.r4.resource.RequestGroupRelatedAction
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.RequestGroupActionCardinalityBehavior
import com.projectronin.interop.fhir.r4.valueset.RequestGroupActionGroupingBehavior
import com.projectronin.interop.fhir.r4.valueset.RequestGroupActionPrecheckBehavior
import com.projectronin.interop.fhir.r4.valueset.RequestGroupActionRequiredBehavior
import com.projectronin.interop.fhir.r4.valueset.RequestGroupActionSelectionBehavior
import com.projectronin.interop.fhir.r4.valueset.RequestGroupConditionKind
import com.projectronin.interop.fhir.r4.valueset.RequestGroupIntent
import com.projectronin.interop.fhir.r4.valueset.RequestGroupPriority
import com.projectronin.interop.fhir.r4.valueset.RequestGroupRelatedActionRelationship
import com.projectronin.interop.fhir.r4.valueset.RequestGroupStatus
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

object R4RequestGroupValidator : R4ElementContainingValidator<RequestGroup>() {
    private val requiredStatusError = RequiredFieldError(RequestGroup::status)
    private val requiredIntentError = RequiredFieldError(RequestGroup::intent)

    override fun validateElement(element: RequestGroup, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            // status
            checkNotNull(element.status, requiredStatusError, parentContext)
            ifNotNull(element.status) {
                checkCodedEnum<RequestGroupStatus>(
                    element.status,
                    InvalidValueSetError(RequestGroup::status, element.status.value),
                    parentContext
                )
            }

            // intent
            checkNotNull(element.intent, requiredIntentError, parentContext)
            ifNotNull(element.intent) {
                checkCodedEnum<RequestGroupIntent>(
                    element.intent,
                    InvalidValueSetError(RequestGroup::status, element.intent.value),
                    parentContext
                )
            }

            // priority
            element.priority?.let { code ->
                checkCodedEnum<RequestGroupPriority>(
                    code,
                    InvalidValueSetError(RequestGroup::priority, code.value),
                    parentContext
                )
            }
        }
    }
}

object R4RequestGroupActionValidator : R4ElementContainingValidator<RequestGroupAction>() {
    private val acceptedTimingTypes = listOf(
        DynamicValueType.DATE_TIME,
        DynamicValueType.AGE,
        DynamicValueType.PERIOD,
        DynamicValueType.DURATION,
        DynamicValueType.RANGE,
        DynamicValueType.TIMING
    )
    private val invalidTimingError = InvalidDynamicValueError(RequestGroupAction::timing, acceptedTimingTypes)
    private val resourceOrActionError = FHIRError(
        code = "R4_REQGRAC_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "Must have resource or action but not both",
        location = LocationContext(RequestGroupAction::class)
    )
    override fun validateElement(element: RequestGroupAction, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            // priority
            element.priority?.let { code ->
                checkCodedEnum<RequestGroupPriority>(
                    code,
                    InvalidValueSetError(RequestGroupAction::priority, code.value),
                    parentContext
                )
            }

            // timing
            element.timing?.let {
                checkTrue(acceptedTimingTypes.contains(it.type), invalidTimingError, parentContext)
            }

            // groupingBehavior
            element.groupingBehavior?.let { code ->
                checkCodedEnum<RequestGroupActionGroupingBehavior>(
                    code,
                    InvalidValueSetError(RequestGroupAction::groupingBehavior, code.value),
                    parentContext
                )
            }

            // selectionBehavior
            element.selectionBehavior?.let { code ->
                checkCodedEnum<RequestGroupActionSelectionBehavior>(
                    code,
                    InvalidValueSetError(RequestGroupAction::selectionBehavior, code.value),
                    parentContext
                )
            }

            // requiredBehavior
            element.requiredBehavior?.let { code ->
                checkCodedEnum<RequestGroupActionRequiredBehavior>(
                    code,
                    InvalidValueSetError(RequestGroupAction::requiredBehavior, code.value),
                    parentContext
                )
            }

            // precheckBehavior
            element.precheckBehavior?.let { code ->
                checkCodedEnum<RequestGroupActionPrecheckBehavior>(
                    code,
                    InvalidValueSetError(RequestGroupAction::precheckBehavior, code.value),
                    parentContext
                )
            }

            // cardinalityBehavior
            element.cardinalityBehavior?.let { code ->
                checkCodedEnum<RequestGroupActionCardinalityBehavior>(
                    code,
                    InvalidValueSetError(RequestGroupAction::cardinalityBehavior, code.value),
                    parentContext
                )
            }
            checkTrue(((element.resource != null) != element.action.isNotEmpty()), resourceOrActionError, parentContext)
        }
    }
}

object R4RequestGroupConditionValidator : R4ElementContainingValidator<RequestGroupCondition>() {
    private val requiredKindError = RequiredFieldError(RequestGroupCondition::kind)

    override fun validateElement(element: RequestGroupCondition, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            // kind
            checkNotNull(element.kind, requiredKindError, parentContext)
            ifNotNull(element.kind) {
                checkCodedEnum<RequestGroupConditionKind>(
                    element.kind,
                    InvalidValueSetError(RequestGroupCondition::kind, element.kind.value),
                    parentContext
                )
            }
        }
    }
}
object R4RequestGroupRelatedActionValidator : R4ElementContainingValidator<RequestGroupRelatedAction>() {
    private val requiredActionIdError = RequiredFieldError(RequestGroupRelatedAction::actionId)
    private val requiredRelationshipError = RequiredFieldError(RequestGroupRelatedAction::relationship)
    private val acceptedOffsetTypes = listOf(
        DynamicValueType.DURATION,
        DynamicValueType.RANGE
    )
    private val invalidOffsetError = InvalidDynamicValueError(RequestGroupRelatedAction::offset, acceptedOffsetTypes)

    override fun validateElement(element: RequestGroupRelatedAction, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            // actionId
            checkNotNull(element.actionId, requiredActionIdError, parentContext)

            // relationship
            checkNotNull(element.relationship, requiredRelationshipError, parentContext)
            ifNotNull(element.relationship) {
                checkCodedEnum<RequestGroupRelatedActionRelationship>(
                    element.relationship,
                    InvalidValueSetError(RequestGroupRelatedAction::relationship, element.relationship.value),
                    parentContext
                )
            }

            // offset
            element.offset?.let {
                checkTrue(acceptedOffsetTypes.contains(it.type), invalidOffsetError, parentContext)
            }
        }
    }
}