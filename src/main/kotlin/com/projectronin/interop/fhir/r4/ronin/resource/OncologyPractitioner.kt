package com.projectronin.interop.fhir.r4.ronin.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.CodeableConcepts
import com.projectronin.interop.fhir.r4.datatype.Address
import com.projectronin.interop.fhir.r4.datatype.Attachment
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.ContactPoint
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.HumanName
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Qualification
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Date
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.ContainedResource
import com.projectronin.interop.fhir.r4.resource.base.BasePractitioner
import com.projectronin.interop.fhir.r4.valueset.AdministrativeGender
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.validation

/**
 * Project Ronin definition of an Oncology Practitioner.
 *
 * See [Project Ronin Profile Spec](https://crispy-carnival-61996e6e.pages.github.io/StructureDefinition-oncology-practitioner.html)
 */
@JsonTypeName("Practitioner")
data class OncologyPractitioner(
    override val id: Id? = null,
    override val meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<ContainedResource> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    override val identifier: List<Identifier>,
    override val active: Boolean? = null,
    override val name: List<HumanName>,
    override val telecom: List<ContactPoint> = listOf(),
    override val address: List<Address> = listOf(),
    override val gender: AdministrativeGender? = null,
    override val birthDate: Date? = null,
    override val photo: List<Attachment> = listOf(),
    override val qualification: List<Qualification> = listOf(),
    override val communication: List<CodeableConcept> = listOf()
) : RoninDomainResource, BasePractitioner() {
    override fun validate(): Validation = validation {
        merge(super.validate())

        requireTenantIdentifier(this, identifier)

        identifier.find { it.system == CodeSystem.SER.uri }?.let {
            check(it.type == CodeableConcepts.SER) { "SER provided without proper CodeableConcept defined" }
        }

        check(name.isNotEmpty()) { "At least one name must be provided" }
        check(name.all { it.family != null }) { "All names must have a family name provided" }
    }
}
