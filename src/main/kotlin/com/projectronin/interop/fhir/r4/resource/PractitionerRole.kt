package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.AvailableTime
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.ContactPoint
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.NotAvailable
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

/**
 * A specific set of Roles/Locations/specialties/services that a practitioner may perform at an organization for a period of time.
 */
@JsonSerialize(using = PractitionerRoleSerializer::class)
@JsonDeserialize(using = PractitionerRoleDeserializer::class)
@JsonTypeName("PractitionerRole")
data class PractitionerRole(
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
    val period: Period? = null,
    val practitioner: Reference? = null,
    val organization: Reference? = null,
    val code: List<CodeableConcept> = listOf(),
    val specialty: List<CodeableConcept> = listOf(),
    val location: List<Reference> = listOf(),
    val healthcareService: List<Reference> = listOf(),
    val telecom: List<ContactPoint> = listOf(),
    val availableTime: List<AvailableTime> = listOf(),
    val notAvailable: List<NotAvailable> = listOf(),
    val availabilityExceptions: FHIRString? = null,
    val endpoint: List<Reference> = listOf()
) : DomainResource<PractitionerRole> {
    override val resourceType: String = "PractitionerRole"
}

class PractitionerRoleSerializer : BaseFHIRSerializer<PractitionerRole>(PractitionerRole::class.java)
class PractitionerRoleDeserializer : BaseFHIRDeserializer<PractitionerRole>(PractitionerRole::class.java)
