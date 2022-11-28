package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

/**
 * A numeric or alphanumeric string that is associated with a single object or entity within a given system. Typically,
 * identifiers are used to connect content in resources to external content available in other frameworks or protocols.
 * Identifiers are associated with objects and may be changed or retired due to human or system process and errors.
 */
@JsonSerialize(using = IdentifierSerializer::class)
@JsonDeserialize(using = IdentifierDeserializer::class)
data class Identifier(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val use: Code? = null,
    val type: CodeableConcept? = null,
    val system: Uri? = null,
    val value: FHIRString? = null,
    val period: Period? = null,
    val assigner: Reference? = null,
) : Element<Identifier>

class IdentifierSerializer : BaseFHIRSerializer<Identifier>(Identifier::class.java)
class IdentifierDeserializer : BaseFHIRDeserializer<Identifier>(Identifier::class.java)
