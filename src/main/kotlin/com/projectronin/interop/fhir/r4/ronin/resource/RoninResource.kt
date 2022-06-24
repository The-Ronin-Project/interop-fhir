package com.projectronin.interop.fhir.r4.ronin.resource

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.projectronin.interop.fhir.FHIRResource
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

/**
 * A FHIR resource is an entity that:
 * - has a known identity (a URL) by which it can be addressed
 * - identifies itself as one of the types of resource defined in this specification
 * - contains a set of structured data items as described by the definition of the resource type
 * - has an identified version that changes if the contents of the resource change
 *
 * See the [FHIR Spec](https://www.hl7.org/fhir/resource.html)
 *
 * A Ronin FHIR resource is a resource that has differences from the standard FHIR specification.
 * The [Ronin FHIR Profile](https://crispy-carnival-61996e6e.pages.github.io/toc.html)
 * document lists the differences between each Ronin resource
 * and the standard FHIR specification.
 */
@JsonPropertyOrder("resourceType")
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "resourceType",
    defaultImpl = UnknownRoninResource::class,
    visible = true
)
@JsonSubTypes(
    JsonSubTypes.Type(OncologyAppointment::class),
    JsonSubTypes.Type(OncologyCondition::class),
    JsonSubTypes.Type(OncologyObservation::class),
    JsonSubTypes.Type(OncologyPatient::class),
    JsonSubTypes.Type(OncologyPractitioner::class),
    JsonSubTypes.Type(OncologyPractitionerRole::class),
    JsonSubTypes.Type(RoninBundle::class)
)
interface RoninResource : FHIRResource {
    val meta: Meta?
    val implicitRules: Uri?
    val language: Code?
}
