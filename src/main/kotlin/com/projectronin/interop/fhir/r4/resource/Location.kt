package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
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
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.LocationMode
import com.projectronin.interop.fhir.r4.valueset.LocationStatus
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.validation

/**
 * Details and position information for a physical place where services are provided and
 * resources and participants may be stored, found, contained, or accommodated.
 *
 * See [FHIR Spec](https://hl7.org/fhir/R4/location.html)
 */
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
    val status: LocationStatus? = null,
    val operationalStatus: Coding? = null,
    val name: String? = null,
    val alias: List<String> = listOf(),
    val description: String? = null,
    val mode: LocationMode? = null,
    val type: List<CodeableConcept> = listOf(),
    val telecom: List<ContactPoint> = listOf(),
    val address: Address? = null,
    val physicalType: CodeableConcept? = null,
    val position: LocationPosition? = null,
    val managingOrganization: Reference? = null,
    val partOf: Reference? = null,
    val hoursOfOperation: List<LocationHoursOfOperation> = listOf(),
    val availabilityExceptions: String? = null,
    val endpoint: List<Reference> = listOf(),
) : DomainResource<Location> {
    override val resourceType: String = "Location"

    override fun validate(): Validation = validation {}
}
