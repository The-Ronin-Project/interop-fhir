package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

/**
 * Stage/grade, usually assessed formally
 */
@JsonSerialize(using = ConditionStageSerializer::class)
@JsonDeserialize(using = ConditionStageDeserializer::class)
data class ConditionStage(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val summary: CodeableConcept? = null,
    val assessment: List<Reference> = listOf(),
    val type: CodeableConcept? = null
) : BackboneElement<ConditionStage>

class ConditionStageSerializer : BaseFHIRSerializer<ConditionStage>(ConditionStage::class.java)
class ConditionStageDeserializer : BaseFHIRDeserializer<ConditionStage>(ConditionStage::class.java)
