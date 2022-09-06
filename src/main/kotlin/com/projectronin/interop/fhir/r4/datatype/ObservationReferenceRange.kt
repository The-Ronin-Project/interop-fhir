package com.projectronin.interop.fhir.r4.datatype

/**
 * An Observation.referenceRange provides guide for interpretation.
 * Rule: Must have at least a low or a high or text.
 */
data class ObservationReferenceRange(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val low: SimpleQuantity? = null,
    val high: SimpleQuantity? = null,
    val type: CodeableConcept? = null,
    val appliesTo: List<CodeableConcept> = listOf(),
    val age: Range? = null,
    val text: String? = null,
) : BackboneElement<ObservationReferenceRange>
