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
import com.projectronin.interop.fhir.r4.resource.base.BaseLocation
import com.projectronin.interop.fhir.r4.valueset.LocationMode
import com.projectronin.interop.fhir.r4.valueset.LocationStatus

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
    override val identifier: List<Identifier> = listOf(),
    override val status: LocationStatus? = null,
    override val operationalStatus: Coding? = null,
    override val name: String? = null,
    override val alias: List<String> = listOf(),
    override val description: String? = null,
    override val mode: LocationMode? = null,
    override val type: List<CodeableConcept> = listOf(),
    override val telecom: List<ContactPoint> = listOf(),
    override val address: Address? = null,
    override val physicalType: CodeableConcept? = null,
    override val position: LocationPosition? = null,
    override val managingOrganization: Reference? = null,
    override val partOf: Reference? = null,
    override val hoursOfOperation: List<LocationHoursOfOperation> = listOf(),
    override val availabilityExceptions: String? = null,
    override val endpoint: List<Reference> = listOf(),
) : DomainResource, BaseLocation() {
    init {
        validate()
    }
}
