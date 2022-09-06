package com.projectronin.interop.fhir.r4.datatype

/**
 * Stage/grade, usually assessed formally
 */
data class ConditionStage(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val summary: CodeableConcept? = null,
    val assessment: List<Reference> = listOf(),
    val type: CodeableConcept? = null
) : BackboneElement<ConditionStage>
