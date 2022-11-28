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
import com.projectronin.interop.fhir.r4.datatype.LocationHoursOfOperation
import com.projectronin.interop.fhir.r4.datatype.LocationPosition
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

/**
 * Details and position information for a physical place where services are provided and
 * resources and participants may be stored, found, contained, or accommodated.
 */
@JsonSerialize(using = LocationSerializer::class)
@JsonDeserialize(using = LocationDeserializer::class)
@JsonTypeName("Location")
data class Location(
    override val id: Id? = null,
    override val meta: Meta? = null,
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
    val endpoint: List<Reference> = listOf(),
) : DomainResource<Location> {
    override val resourceType: String = "Location"
}

class LocationSerializer : BaseFHIRSerializer<Location>(Location::class.java)
class LocationDeserializer : BaseFHIRDeserializer<Location>(Location::class.java)
