package com.projectronin.interop.fhir.r4.resource.base

import com.projectronin.interop.common.enums.CodedEnum
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.ConditionEvidence
import com.projectronin.interop.fhir.r4.datatype.ConditionStage
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.ContainedResource
import com.projectronin.interop.fhir.r4.valueset.ConditionClinicalStatusCodes
import com.projectronin.interop.fhir.r4.valueset.ConditionVerificationStatus

/**
 * Base class representing a FHIR R4 Condition.
 *
 * See [FHIR Spec](https://www.hl7.org/fhir/R4/condition.html)
 */
abstract class BaseCondition {
    val resourceType: String = "Condition"

    abstract val id: Id?
    abstract val meta: Meta?
    abstract val implicitRules: Uri?
    abstract val language: Code?
    abstract val text: Narrative?
    abstract val contained: List<ContainedResource>
    abstract val extension: List<Extension>
    abstract val modifierExtension: List<Extension>
    abstract val identifier: List<Identifier>
    abstract val clinicalStatus: CodeableConcept?
    abstract val verificationStatus: CodeableConcept?
    abstract val category: List<CodeableConcept>
    abstract val severity: CodeableConcept?
    abstract val code: CodeableConcept?
    abstract val bodySite: List<CodeableConcept>
    abstract val subject: Reference
    abstract val encounter: Reference?
    abstract val onset: DynamicValue<Any>?
    abstract val abatement: DynamicValue<Any>?
    abstract val recordedDate: DateTime?
    abstract val recorder: Reference?
    abstract val asserter: Reference?
    abstract val stage: List<ConditionStage>
    abstract val evidence: List<ConditionEvidence>
    abstract val note: List<Annotation>

    companion object {
        val acceptedOnsetTypes = listOf(
            DynamicValueType.DATE_TIME,
            DynamicValueType.AGE,
            DynamicValueType.PERIOD,
            DynamicValueType.RANGE,
            DynamicValueType.STRING
        )
        val acceptedAbatementTypes = listOf(
            DynamicValueType.DATE_TIME,
            DynamicValueType.AGE,
            DynamicValueType.PERIOD,
            DynamicValueType.RANGE,
            DynamicValueType.STRING
        )
        val acceptedClinicalStatusCodes = listOf(
            ConditionClinicalStatusCodes.ACTIVE,
            ConditionClinicalStatusCodes.RECURRENCE,
            ConditionClinicalStatusCodes.RELAPSE,
            ConditionClinicalStatusCodes.INACTIVE,
            ConditionClinicalStatusCodes.REMISSION,
            ConditionClinicalStatusCodes.RESOLVED
        )
        val acceptedAbatementClinicalCodes = listOf(
            ConditionClinicalStatusCodes.INACTIVE,
            ConditionClinicalStatusCodes.REMISSION,
            ConditionClinicalStatusCodes.RESOLVED
        )
        val acceptedVerificationStatusCodes = listOf(
            ConditionVerificationStatus.CONFIRMED,
            ConditionVerificationStatus.DIFFERENTIAL,
            ConditionVerificationStatus.ENTERED_IN_ERROR,
            ConditionVerificationStatus.PROVISIONAL,
            ConditionVerificationStatus.REFUTED,
            ConditionVerificationStatus.UNCONFIRMED
        )
    }

    protected fun validate() {
        onset?.let { data ->
            require(acceptedOnsetTypes.contains(data.type)) {
                "onset can only be one of the following data types: ${acceptedOnsetTypes.joinToString { it.code }}"
            }
        }

        val clinicalStatusCodes = clinicalStatus?.let { clinicalStatus ->
            clinicalStatus.coding.mapNotNull { coding ->
                coding.code?.let { code -> CodedEnum.byCode<ConditionClinicalStatusCodes>(code.value) }
            }
        } ?: emptyList()
        require(clinicalStatus == null || clinicalStatusCodes.size == 1) {
            "clinicalStatus can only be one of the following codes: ${acceptedClinicalStatusCodes.joinToString { it.code }}"
        }

        abatement?.let { data ->
            require(acceptedAbatementTypes.contains(data.type)) {
                "abatement can only be one of the following data types: ${acceptedOnsetTypes.joinToString { it.code }}"
            }
            require(acceptedAbatementClinicalCodes.contains(clinicalStatusCodes.firstOrNull())) {
                "If condition is abated, then clinicalStatus must be either inactive, resolved, or remission"
            }
        }

        val verificationStatusCodes = verificationStatus?.let { status ->
            status.coding.mapNotNull { coding ->
                coding.code?.let { code -> CodedEnum.byCode<ConditionVerificationStatus>(code.value) }
            }
        } ?: emptyList()
        require(verificationStatus == null || verificationStatusCodes.size == 1) {
            "verificationStatus can only be one of the following codes: ${acceptedVerificationStatusCodes.joinToString { it.code }}"
        }

        require(clinicalStatus == null || (verificationStatusCodes.firstOrNull() != ConditionVerificationStatus.ENTERED_IN_ERROR)) {
            "clinicalStatus SHALL NOT be present if verification Status is entered-in-error"
        }
    }
}
