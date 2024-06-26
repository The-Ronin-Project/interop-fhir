package com.projectronin.interop.fhir.stu3.resource

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.validate.Validatable
import com.projectronin.interop.fhir.r4.resource.Resource as R4Resource

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
    defaultImpl = STU3UnknownResource::class,
    visible = true,
)
@JsonSubTypes(
    JsonSubTypes.Type(STU3Appointment::class),
    JsonSubTypes.Type(STU3Bundle::class),
    JsonSubTypes.Type(STU3MedicationStatement::class),
    JsonSubTypes.Type(STU3Medication::class),
)
interface STU3Resource<T : STU3Resource<T>> : Validatable<T> {
    val resourceType: String
    val id: Id?
    var meta: Meta?
    val implicitRules: Uri?
    val language: Code?

    fun transformToR4(): R4Resource<*>
}
