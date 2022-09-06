package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.common.enums.CodedEnum
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.resource.Condition
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.ConditionClinicalStatusCodes
import com.projectronin.interop.fhir.r4.valueset.ConditionVerificationStatus
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 Condition](http://hl7.org/fhir/R4/condition.html)
 */
object R4ConditionValidator : R4ElementContainingValidator<Condition>() {
    private val acceptedOnsetTypes = listOf(
        DynamicValueType.DATE_TIME,
        DynamicValueType.AGE,
        DynamicValueType.PERIOD,
        DynamicValueType.RANGE,
        DynamicValueType.STRING
    )
    private val acceptedAbatementTypes = listOf(
        DynamicValueType.DATE_TIME,
        DynamicValueType.AGE,
        DynamicValueType.PERIOD,
        DynamicValueType.RANGE,
        DynamicValueType.STRING
    )
    private val acceptedAbatementClinicalCodes = listOf(
        ConditionClinicalStatusCodes.INACTIVE,
        ConditionClinicalStatusCodes.REMISSION,
        ConditionClinicalStatusCodes.RESOLVED
    )

    private val requiredSubjectError = RequiredFieldError(Condition::subject)
    private val invalidOnsetError = InvalidDynamicValueError(Condition::onset, acceptedOnsetTypes)
    private val invalidClinicalStatusError = InvalidValueSetError(Condition::clinicalStatus)
    private val invalidAbatementError = InvalidDynamicValueError(Condition::abatement, acceptedAbatementTypes)
    private val invalidVerificationStatusError = InvalidValueSetError(Condition::verificationStatus)

    private val invalidClinicalStatusForAbatementError = FHIRError(
        code = "R4_CND_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "If condition is abated, then clinicalStatus must be one of the following: ${acceptedAbatementClinicalCodes.joinToString { it.code }}",
        location = LocationContext(Condition::class)
    )
    private val clinicalStatusAndEnteredInErrorError = FHIRError(
        code = "R4_CND_002",
        severity = ValidationIssueSeverity.ERROR,
        description = "clinicalStatus SHALL NOT be present if verification Status is entered-in-error",
        location = LocationContext(Condition::class)
    )

    override fun validateElement(element: Condition, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.subject, requiredSubjectError, parentContext)

            element.onset?.let {
                checkTrue(acceptedOnsetTypes.contains(it.type), invalidOnsetError, parentContext)
            }

            val clinicalStatusCodes = element.clinicalStatus?.let { clinicalStatus ->
                clinicalStatus.coding.mapNotNull { coding ->
                    coding.code?.let { code -> runCatching { CodedEnum.byCode<ConditionClinicalStatusCodes>(code.value) }.getOrNull() }
                }
            } ?: emptyList()

            checkTrue(
                (element.clinicalStatus == null || clinicalStatusCodes.size == 1),
                invalidClinicalStatusError,
                parentContext
            )

            element.abatement?.let { data ->
                checkTrue(acceptedAbatementTypes.contains(data.type), invalidAbatementError, parentContext)
                checkTrue(
                    acceptedAbatementClinicalCodes.contains(clinicalStatusCodes.firstOrNull()),
                    invalidClinicalStatusForAbatementError,
                    parentContext
                )
            }

            val verificationStatusCodes = element.verificationStatus?.let { status ->
                status.coding.mapNotNull { coding ->
                    coding.code?.let { code -> runCatching { CodedEnum.byCode<ConditionVerificationStatus>(code.value) }.getOrNull() }
                }
            } ?: emptyList()

            checkTrue(
                (element.verificationStatus == null || verificationStatusCodes.size == 1),
                invalidVerificationStatusError,
                parentContext
            )

            checkTrue(
                (element.clinicalStatus == null || (verificationStatusCodes.firstOrNull() != ConditionVerificationStatus.ENTERED_IN_ERROR)),
                clinicalStatusAndEnteredInErrorError,
                parentContext
            )
        }
    }
}
