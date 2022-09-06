package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt

/**
 * Details for all kinds of technology-mediated contact points for a person or organization, including telephone, email, etc.
 */
data class ContactPoint(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    val system: Code? = null,
    val value: String? = null,
    val use: Code? = null,
    val rank: PositiveInt? = null,
    val period: Period? = null
) : Element<ContactPoint>
