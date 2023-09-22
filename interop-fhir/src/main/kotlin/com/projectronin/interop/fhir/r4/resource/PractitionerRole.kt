package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.event.interop.internal.v1.ResourceType
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.ContactPoint
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Time
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.BackboneElement
import com.projectronin.interop.fhir.r4.valueset.DayOfWeek
import com.projectronin.interop.fhir.validate.annotation.RequiredField
import com.projectronin.interop.fhir.validate.annotation.RequiredValueSet
import com.projectronin.interop.fhir.validate.annotation.SupportedReferenceTypes

/**
 * A specific set of Roles/Locations/specialties/services that a practitioner may perform at an organization for a period of time.
 */
@JsonSerialize(using = PractitionerRoleSerializer::class)
@JsonDeserialize(using = PractitionerRoleDeserializer::class)
@JsonTypeName("PractitionerRole")
data class PractitionerRole(
    override val id: Id? = null,
    override var meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<Resource<*>> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: List<Identifier> = listOf(),
    val active: FHIRBoolean? = null,
    val period: Period? = null,
    @SupportedReferenceTypes(ResourceType.Practitioner)
    val practitioner: Reference? = null,
    @SupportedReferenceTypes(ResourceType.Organization)
    val organization: Reference? = null,
    val code: List<CodeableConcept> = listOf(),
    val specialty: List<CodeableConcept> = listOf(),
    @SupportedReferenceTypes(ResourceType.Location)
    val location: List<Reference> = listOf(),
    @SupportedReferenceTypes(ResourceType.HealthcareService)
    val healthcareService: List<Reference> = listOf(),
    val telecom: List<ContactPoint> = listOf(),
    val availableTime: List<AvailableTime> = listOf(),
    val notAvailable: List<NotAvailable> = listOf(),
    val availabilityExceptions: FHIRString? = null,
    @SupportedReferenceTypes(ResourceType.Endpoint)
    val endpoint: List<Reference> = listOf()
) : DomainResource<PractitionerRole> {
    override val resourceType: String = "PractitionerRole"
}

class PractitionerRoleSerializer : BaseFHIRSerializer<PractitionerRole>(PractitionerRole::class.java)
class PractitionerRoleDeserializer : BaseFHIRDeserializer<PractitionerRole>(PractitionerRole::class.java)

/**
 * Times the practitioner is available or performing this role at the location and/or healthcareservice.
 */
@JsonSerialize(using = AvailableTimeSerializer::class)
@JsonDeserialize(using = AvailableTimeDeserializer::class)
data class AvailableTime(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    @RequiredValueSet(DayOfWeek::class)
    val daysOfWeek: List<Code> = listOf(),
    val allDay: FHIRBoolean? = null,
    val availableStartTime: Time? = null,
    val availableEndTime: Time? = null
) : BackboneElement<AvailableTime>

class AvailableTimeSerializer : BaseFHIRSerializer<AvailableTime>(AvailableTime::class.java)
class AvailableTimeDeserializer : BaseFHIRDeserializer<AvailableTime>(AvailableTime::class.java)

/**
 * The practitioner is not available or performing this role during this period of time due to the provided reason.
 */
@JsonSerialize(using = NotAvailableSerializer::class)
@JsonDeserialize(using = NotAvailableDeserializer::class)
data class NotAvailable(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    @RequiredField
    val description: FHIRString?,
    val during: Period? = null
) : BackboneElement<NotAvailable>

class NotAvailableSerializer : BaseFHIRSerializer<NotAvailable>(NotAvailable::class.java)
class NotAvailableDeserializer : BaseFHIRDeserializer<NotAvailable>(NotAvailable::class.java)
