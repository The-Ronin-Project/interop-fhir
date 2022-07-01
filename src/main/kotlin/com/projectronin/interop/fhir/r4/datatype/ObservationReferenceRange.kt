package com.projectronin.interop.fhir.r4.datatype

/**
 * An Observation.referenceRange provides guide for interpretation.
 * Rule: Must have at least a low or a high or text.
 *
 * See [FHIR Documentation](http://hl7.org/fhir/R4/observation.html)
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
) : BackboneElement {
    init {
        require((low != null) || (high != null) || !text.isNullOrEmpty()) {
            "referenceRange must have at least a low or a high or text"
        }
    }
}
