package com.projectronin.interop.fhir.r4.datatype

/**
 * Contact for the organization for a certain purpose.
 *
 * See [FHIR Spec] (https://hl7.org/fhir/R4/organization-definitions.html#Organization.contact)
 */
data class OrganizationContact(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val purpose: CodeableConcept? = null,
    val name: HumanName? = null,
    val telecom: List<ContactPoint> = listOf(),
    val address: Address? = null
) : BackboneElement<OrganizationContact>
