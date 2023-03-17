package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.validate.Validatable

/**
 * A resource is an entity that:
 * - has a known identity (a URL) by which it can be addressed
 * - identifies itself as one of the types of resource defined in this specification
 * - contains a set of structured data items as described by the definition of the resource type
 * - has an identified version that changes if the contents of the resource change
 *
 * See [FHIR Spec](https://www.hl7.org/fhir/resource.html)
 */
@JsonPropertyOrder("resourceType")
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "resourceType",
    defaultImpl = UnknownResource::class,
    visible = true
)
/**
 * This is the list of FHIR resources that Ronin product code can use.
 */
@JsonSubTypes(
    JsonSubTypes.Type(Appointment::class),
    JsonSubTypes.Type(Bundle::class),
    JsonSubTypes.Type(CarePlan::class),
    JsonSubTypes.Type(ConceptMap::class),
    JsonSubTypes.Type(Condition::class),
    JsonSubTypes.Type(Encounter::class),
    JsonSubTypes.Type(Location::class),
    JsonSubTypes.Type(Medication::class),
    JsonSubTypes.Type(MedicationRequest::class),
    JsonSubTypes.Type(MedicationStatement::class),
    JsonSubTypes.Type(Observation::class),
    JsonSubTypes.Type(Organization::class),
    JsonSubTypes.Type(Patient::class),
    JsonSubTypes.Type(Practitioner::class),
    JsonSubTypes.Type(PractitionerRole::class),
    JsonSubTypes.Type(CareTeam::class),
    JsonSubTypes.Type(DiagnosticReport::class),
    JsonSubTypes.Type(Communication::class),
    JsonSubTypes.Type(DocumentReference::class)
)
interface Resource<T : Resource<T>> : Validatable<T> {
    val resourceType: String
    val id: Id?
    val meta: Meta?
    val implicitRules: Uri?
    val language: Code?
}