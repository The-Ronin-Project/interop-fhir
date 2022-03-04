package com.projectronin.interop.fhir.r4.resource.base

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
import com.projectronin.interop.fhir.r4.resource.ContainedResource
import com.projectronin.interop.fhir.r4.valueset.LocationMode
import com.projectronin.interop.fhir.r4.valueset.LocationStatus

/**
 * Base class representing a FHIR R4 Location.
 *
 * See [FHIR Spec](https://www.hl7.org/fhir/location.html)
 */
abstract class BaseLocation {
    val resourceType: String = "Location"

    abstract val id: Id?
    abstract val meta: Meta?
    abstract val implicitRules: Uri?
    abstract val language: Code?
    abstract val text: Narrative?
    abstract val contained: List<ContainedResource>
    abstract val extension: List<Extension>
    abstract val modifierExtension: List<Extension>
    abstract val identifier: List<Identifier>
    abstract val status: LocationStatus?
    abstract val operationalStatus: Coding?
    abstract val name: String?
    abstract val alias: List<String>
    abstract val description: String?
    abstract val mode: LocationMode?
    abstract val type: List<CodeableConcept>
    abstract val telecom: List<ContactPoint>
    abstract val address: Address?
    abstract val physicalType: CodeableConcept?
    abstract val position: LocationPosition?
    abstract val managingOrganization: Reference?
    abstract val partOf: Reference?
    abstract val hoursOfOperation: List<LocationHoursOfOperation>
    abstract val availabilityExceptions: String?
    abstract val endpoint: List<Reference>

    protected fun validate() {}
}
