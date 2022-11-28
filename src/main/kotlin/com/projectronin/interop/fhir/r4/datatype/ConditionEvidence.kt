package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

/**
 * Supporting evidence
 */
@JsonSerialize(using = ConditionEvidenceSerializer::class)
@JsonDeserialize(using = ConditionEvidenceDeserializer::class)
data class ConditionEvidence(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val code: List<CodeableConcept> = listOf(),
    val detail: List<Reference> = listOf()
) : BackboneElement<ConditionEvidence>

class ConditionEvidenceSerializer : BaseFHIRSerializer<ConditionEvidence>(ConditionEvidence::class.java)
class ConditionEvidenceDeserializer : BaseFHIRDeserializer<ConditionEvidence>(ConditionEvidence::class.java)
