package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.common.enums.CodedEnum
import com.projectronin.interop.fhir.r4.resource.Condition
import com.projectronin.interop.fhir.r4.resource.ConditionEvidence
import com.projectronin.interop.fhir.r4.resource.ConditionStage
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.ConditionClinicalStatusCodes
import com.projectronin.interop.fhir.r4.valueset.ConditionVerificationStatus
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 Condition](http://hl7.org/fhir/R4/condition.html)
 */
object R4ConditionValidator : R4ElementContainingValidator<Condition>() {
    private val acceptedAbatementClinicalCodes = listOf(
        ConditionClinicalStatusCodes.INACTIVE,
        ConditionClinicalStatusCodes.REMISSION,
        ConditionClinicalStatusCodes.RESOLVED
    )

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
            val clinicalStatusCodes = element.clinicalStatus?.let { clinicalStatus ->
                clinicalStatus.coding.mapNotNull { coding ->
                    coding.code?.let { code ->
                        runCatching {
                            code.value?.let {
                                CodedEnum.byCode<ConditionClinicalStatusCodes>(it)
                            }
                        }.getOrNull()
                    }
                }
            } ?: emptyList()

            element.clinicalStatus?.let { clinicalStatus ->
                checkTrue(
                    clinicalStatusCodes.size == 1,
                    InvalidValueSetError(
                        Condition::clinicalStatus,
                        clinicalStatus.coding.joinToString { it.code?.value!! }
                    ),
                    parentContext
                )
            }

            element.abatement?.let { data ->
                checkTrue(
                    acceptedAbatementClinicalCodes.contains(clinicalStatusCodes.firstOrNull()),
                    invalidClinicalStatusForAbatementError,
                    parentContext
                )
            }

            val verificationStatusCodes = element.verificationStatus?.let { verificationStatus ->
                verificationStatus.coding.mapNotNull { coding ->
                    coding.code?.let { code ->
                        runCatching {
                            code.value?.let {
                                CodedEnum.byCode<ConditionVerificationStatus>(it)
                            }
                        }.getOrNull()
                    }
                }
            } ?: emptyList()

            element.verificationStatus?.let { verificationStatus ->
                checkTrue(
                    verificationStatusCodes.size == 1,
                    InvalidValueSetError(
                        Condition::verificationStatus,
                        verificationStatus.coding.joinToString { it.code?.value!! }
                    ),
                    parentContext
                )
            }

            element.clinicalStatus?.let {
                checkTrue(
                    verificationStatusCodes.firstOrNull() != ConditionVerificationStatus.ENTERED_IN_ERROR,
                    clinicalStatusAndEnteredInErrorError,
                    parentContext
                )
            }
        }
    }
}

/**
 * Validator for the [R4 ConditionEvidence](http://hl7.org/fhir/R4/condition-definitions.html#Condition.evidence)
 */
object R4ConditionEvidenceValidator : R4ElementContainingValidator<ConditionEvidence>() {
    private val codeOrDetailError = FHIRError(
        code = "R4_CNDEV_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "evidence SHALL have code or details",
        location = LocationContext(ConditionEvidence::class)
    )

    override fun validateElement(element: ConditionEvidence, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkTrue((element.code.isNotEmpty() || element.detail.isNotEmpty()), codeOrDetailError, parentContext)
        }
    }
}

/**
 * Validator for the [R4 ConditionStage](http://hl7.org/fhir/R4/condition-definitions.html#Condition.stage)
 */
object R4ConditionStageValidator : R4ElementContainingValidator<ConditionStage>() {
    private val summaryOrAssessmentError = FHIRError(
        code = "R4_CNDSTG_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "stage SHALL have summary or assessment",
        location = LocationContext(ConditionStage::class)
    )

    override fun validateElement(element: ConditionStage, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkTrue(
                (element.summary != null || element.assessment.isNotEmpty()),
                summaryOrAssessmentError,
                parentContext
            )
        }
    }
}
