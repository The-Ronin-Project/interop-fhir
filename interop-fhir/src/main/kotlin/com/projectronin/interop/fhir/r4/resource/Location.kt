package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.Address
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.ContactPoint
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Time
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.BackboneElement

/**
 * Details and position information for a physical place where services are provided and
 * resources and participants may be stored, found, contained, or accommodated.
 */
@JsonSerialize(using = LocationSerializer::class)
@JsonDeserialize(using = LocationDeserializer::class)
@JsonTypeName("Location")
data class Location(
    override val id: Id? = null,
    override var meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<ContainedResource> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: List<Identifier> = listOf(),
    val status: Code? = null,
    val operationalStatus: Coding? = null,
    val name: FHIRString? = null,
    val alias: List<FHIRString> = listOf(),
    val description: FHIRString? = null,
    val mode: Code? = null,
    val type: List<CodeableConcept> = listOf(),
    val telecom: List<ContactPoint> = listOf(),
    val address: Address? = null,
    val physicalType: CodeableConcept? = null,
    val position: LocationPosition? = null,
    val managingOrganization: Reference? = null,
    val partOf: Reference? = null,
    val hoursOfOperation: List<LocationHoursOfOperation> = listOf(),
    val availabilityExceptions: FHIRString? = null,
    val endpoint: List<Reference> = listOf()
) : DomainResource<Location> {
    override val resourceType: String = "Location"
}

class LocationSerializer : BaseFHIRSerializer<Location>(Location::class.java)
class LocationDeserializer : BaseFHIRDeserializer<Location>(Location::class.java)

/**
 * What days/times during a week is this location usually open.
 */
@JsonSerialize(using = LocationHoursOfOperationSerializer::class)
@JsonDeserialize(using = LocationHoursOfOperationDeserializer::class)
data class LocationHoursOfOperation(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val daysOfWeek: List<Code> = listOf(),
    val allDay: FHIRBoolean? = null,
    val openingTime: Time? = null,
    val closingTime: Time? = null
) : BackboneElement<LocationHoursOfOperation>

class LocationHoursOfOperationSerializer :
    BaseFHIRSerializer<LocationHoursOfOperation>(LocationHoursOfOperation::class.java)

class LocationHoursOfOperationDeserializer :
    BaseFHIRDeserializer<LocationHoursOfOperation>(LocationHoursOfOperation::class.java)

/**
 * The absolute geographic location.
 */
@JsonSerialize(using = LocationPositionSerializer::class)
@JsonDeserialize(using = LocationPositionDeserializer::class)
data class LocationPosition(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val longitude: Decimal?,
    val latitude: Decimal?,
    val altitude: Decimal? = null
) : BackboneElement<LocationPosition>

class LocationPositionSerializer : BaseFHIRSerializer<LocationPosition>(LocationPosition::class.java)
class LocationPositionDeserializer : BaseFHIRDeserializer<LocationPosition>(LocationPosition::class.java)
