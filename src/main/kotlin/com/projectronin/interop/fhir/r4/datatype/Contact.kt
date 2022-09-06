package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.Code

/**
 * Contact covers all kinds of contact parties: family members, business contacts, guardians, caregivers. Not applicable
 * to register pedigree and family ties beyond use of having contact.
 */
data class Contact(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val relationship: List<CodeableConcept> = listOf(),
    val name: HumanName? = null,
    val telecom: List<ContactPoint> = listOf(),
    val address: Address? = null,
    val gender: Code? = null,
    val organization: Reference? = null,
    val period: Period? = null
) : BackboneElement<Contact>
