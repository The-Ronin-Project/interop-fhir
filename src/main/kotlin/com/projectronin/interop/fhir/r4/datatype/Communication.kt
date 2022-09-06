package com.projectronin.interop.fhir.r4.datatype

/**
 * A language which may be used to communicate with the patient about his or her health.
 */
data class Communication(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val language: CodeableConcept?,
    val preferred: Boolean? = null
) : BackboneElement<Communication>
