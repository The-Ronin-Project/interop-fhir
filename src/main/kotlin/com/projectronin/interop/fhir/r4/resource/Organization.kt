package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.projectronin.interop.fhir.r4.datatype.Address
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.ContactPoint
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.OrganizationContact
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

/**
 * A formally or informally recognized grouping of people or organizations formed for the purpose of achieving some
 * form of collective action. Includes companies, institutions, corporations, departments, community groups,
 * healthcare practice groups, payer/insurer, etc.
 *
 * See [FHIR Spec](https://hl7.org/fhir/R4/organization.html)
 * */

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
    val active: Boolean? = null,
    val type: List<CodeableConcept> = listOf(),
    val name: String? = null,
    val alias: List<String> = listOf(),
    val telecom: List<ContactPoint> = listOf(),
    val address: List<Address> = listOf(),
    val partOf: Reference? = null,
    val contact: List<OrganizationContact> = listOf(),
    val endpoint: List<Reference> = listOf()
) : DomainResource<Organization> {
    override val resourceType: String = "Organization"
}
