package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.Code

/**
 * Contributor information
 */
data class Contributor(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    val type: Code?,
    val name: String?,
    val contact: List<ContactDetail> = listOf()
) : Element<Contributor>
