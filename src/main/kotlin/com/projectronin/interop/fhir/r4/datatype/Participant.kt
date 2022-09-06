package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.Code

/**
 * List of participants involved in the appointment.
 */
data class Participant(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val type: List<CodeableConcept> = listOf(),
    val actor: Reference? = null,
    val required: Code? = null,
    val status: Code?,
    val period: Period? = null
) : BackboneElement<Participant>
