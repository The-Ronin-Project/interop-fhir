package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Base64Binary
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.validate.annotation.RequiredField

/**
 * A resource that represents the data of a single raw artifact as digital content accessible in its native format.
 * A Binary resource can contain any content, whether text, image, pdf, zip archive, etc.
 */
@JsonSerialize(using = BinarySerializer::class)
@JsonDeserialize(using = BinaryDeserializer::class)
@JsonTypeName("Binary")
data class Binary(
    override val id: Id? = null,
    override var meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    @RequiredField
    val contentType: Code?,
    val securityContent: Reference? = null,
    val data: Base64Binary? = null
) : Resource<Binary> {
    override val resourceType: String = "Binary"
}

class BinarySerializer : BaseFHIRSerializer<Binary>(Binary::class.java)
class BinaryDeserializer : BaseFHIRDeserializer<Binary>(Binary::class.java)
