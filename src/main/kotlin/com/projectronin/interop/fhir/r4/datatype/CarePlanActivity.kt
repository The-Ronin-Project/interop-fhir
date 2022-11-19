package com.projectronin.interop.fhir.r4.datatype

data class CarePlanActivity(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val outcomeCodeableConcept: List<CodeableConcept> = listOf(),
    val outcomeReference: List<Reference> = listOf(),
    val progress: List<Annotation> = listOf(),
    val reference: Reference? = null,
    val detail: CarePlanDetail? = null
) : BackboneElement<CarePlanActivity>
