package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

/**
 * Contact for the organization for a certain purpose.
 *
 * See [FHIR Spec] (https://hl7.org/fhir/R4/organization-definitions.html#Organization.contact)
 */
@JsonSerialize(using = OrganizationContactSerializer::class)
@JsonDeserialize(using = OrganizationContactDeserializer::class)
data class OrganizationContact(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val purpose: CodeableConcept? = null,
    val name: HumanName? = null,
    val telecom: List<ContactPoint> = listOf(),
    val address: Address? = null
) : BackboneElement<OrganizationContact>

class OrganizationContactSerializer : BaseFHIRSerializer<OrganizationContact>(OrganizationContact::class.java)
class OrganizationContactDeserializer : BaseFHIRDeserializer<OrganizationContact>(OrganizationContact::class.java)
