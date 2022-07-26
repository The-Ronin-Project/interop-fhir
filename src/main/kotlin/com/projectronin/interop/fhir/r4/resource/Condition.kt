package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.common.enums.CodedEnum
import com.projectronin.interop.fhir.jackson.inbound.r4.ConditionDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.ConditionSerializer
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
import com.projectronin.interop.fhir.r4.valueset.ConditionClinicalStatusCodes
import com.projectronin.interop.fhir.r4.valueset.ConditionVerificationStatus

/**
 * A clinical condition, problem, diagnosis, or other event, situation, issue, or clinical concept that has risen to a level of concern.
 *
 * See [FHIR Spec](https://www.hl7.org/fhir/condition.html)
 */
@JsonDeserialize(using = ConditionDeserializer::class)
@JsonSerialize(using = ConditionSerializer::class)
@JsonTypeName("Condition")
data class Condition(
    override val id: Id? = null,
    override val meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<ContainedResource> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: List<Identifier> = listOf(),
    val clinicalStatus: CodeableConcept? = null,
    val verificationStatus: CodeableConcept? = null,
    val category: List<CodeableConcept> = listOf(),
    val severity: CodeableConcept? = null,
    val code: CodeableConcept? = null,
    val bodySite: List<CodeableConcept> = listOf(),
    val subject: Reference,
    val encounter: Reference? = null,
    val onset: DynamicValue<Any>? = null,
    val abatement: DynamicValue<Any>? = null,
    val recordedDate: DateTime? = null,
    val recorder: Reference? = null,
    val asserter: Reference? = null,
    val stage: List<ConditionStage> = listOf(),
    val evidence: List<ConditionEvidence> = listOf(),
    val note: List<Annotation> = listOf()
) : DomainResource<Condition> {
    override val resourceType: String = "Condition"

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

    init {
        onset?.let { data ->
            require(acceptedOnsetTypes.contains(data.type)) {
                "onset can only be one of the following data types: " +
                    acceptedOnsetTypes.joinToString { it.code }
            }
        }

        val clinicalStatusCodes = clinicalStatus?.let { clinicalStatus ->
            clinicalStatus.coding.mapNotNull { coding ->
                coding.code?.let { code -> CodedEnum.byCode<ConditionClinicalStatusCodes>(code.value) }
            }
        } ?: emptyList()
        require(clinicalStatus == null || clinicalStatusCodes.size == 1) {
            "clinicalStatus can only be one of the following codes: " +
                acceptedClinicalStatusCodes.joinToString { it.code }
        }

        abatement?.let { data ->
            require(acceptedAbatementTypes.contains(data.type)) {
                "abatement can only be one of the following data types: " +
                    acceptedAbatementTypes.joinToString { it.code }
            }
            require(acceptedAbatementClinicalCodes.contains(clinicalStatusCodes.firstOrNull())) {
                "If condition is abated, then clinicalStatus must be one of the following: " +
                    acceptedAbatementClinicalCodes.joinToString { it.code }
            }
        }

        val verificationStatusCodes = verificationStatus?.let { status ->
            status.coding.mapNotNull { coding ->
                coding.code?.let { code -> CodedEnum.byCode<ConditionVerificationStatus>(code.value) }
            }
        } ?: emptyList()
        require(verificationStatus == null || verificationStatusCodes.size == 1) {
            "verificationStatus can only be one of the following codes: " +
                acceptedVerificationStatusCodes.joinToString { it.code }
        }

        require(clinicalStatus == null || (verificationStatusCodes.firstOrNull() != ConditionVerificationStatus.ENTERED_IN_ERROR)) {
            "clinicalStatus SHALL NOT be present if verification Status is entered-in-error"
        }
    }
}
