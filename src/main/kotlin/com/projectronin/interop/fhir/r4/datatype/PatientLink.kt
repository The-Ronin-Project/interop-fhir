package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.Code

/**
 * Link to another patient resource that concerns the same actual patient.
 */
data class PatientLink(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val other: Reference?,
    val type: Code?
) : BackboneElement<PatientLink>
