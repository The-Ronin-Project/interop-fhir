package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.Address
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.ContactPoint
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.HumanName
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.BackboneElement

/**
 * A formally or informally recognized grouping of people or organizations formed for the purpose of achieving some
 * form of collective action. Includes companies, institutions, corporations, departments, community groups,
 * healthcare practice groups, payer/insurer, etc.
 *
 * See [FHIR Spec](https://hl7.org/fhir/R4/organization.html)
 */
@JsonDeserialize(using = OrganizationDeserializer::class)
@JsonSerialize(using = OrganizationSerializer::class)
@JsonTypeName("Organization")
data class Organization(
    override val id: Id? = null,
    override val meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<ContainedResource> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: List<Identifier> = listOf(),
    val active: FHIRBoolean? = null,
    val type: List<CodeableConcept> = listOf(),
    val name: FHIRString? = null,
    val alias: List<FHIRString> = listOf(),
    val telecom: List<ContactPoint> = listOf(),
    val address: List<Address> = listOf(),
    val partOf: Reference? = null,
    val contact: List<OrganizationContact> = listOf(),
    val endpoint: List<Reference> = listOf()
) : DomainResource<Organization> {
    override val resourceType: String = "Organization"
}

class OrganizationDeserializer : BaseFHIRDeserializer<Organization>(Organization::class.java)
class OrganizationSerializer : BaseFHIRSerializer<Organization>(Organization::class.java)

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
