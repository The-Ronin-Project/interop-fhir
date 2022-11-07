package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.annotation.JsonProperty
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.PrimitiveData
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

/**
 * A numeric or alphanumeric string that is associated with a single object or entity within a given system. Typically,
 * identifiers are used to connect content in resources to external content available in other frameworks or protocols.
 * Identifiers are associated with objects and may be changed or retired due to human or system process and errors.
 */
data class Identifier(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    val use: Code? = null,
    val type: CodeableConcept? = null,
    val system: Uri? = null,
    val value: String? = null,
    val period: Period? = null,
    val assigner: Reference? = null,
    @JsonProperty(value = "_value")
    val valueData: PrimitiveData? = null
) : Element<Identifier>
