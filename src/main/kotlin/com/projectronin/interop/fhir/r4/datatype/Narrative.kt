package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.Code

/**
 * Any resource that is a DomainResource (all resources except Bundle, Parameters and Binary) may include a human-readable
 * narrative that contains a summary of the resource and may be used to represent the content of the resource to a human.
 */
data class Narrative(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    val status: Code?,
    val div: String?
) : Element<Narrative>
