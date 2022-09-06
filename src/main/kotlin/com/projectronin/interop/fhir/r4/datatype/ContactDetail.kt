package com.projectronin.interop.fhir.r4.datatype

/**
 * The ContactDetail structure defines general contact details.
 */
data class ContactDetail(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    val name: String? = null,
    val telecom: List<ContactPoint> = listOf()
) : Element<ContactDetail>
