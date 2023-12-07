package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.Element

/**
 * A Coding is a representation of a defined concept using a symbol from a defined "code system"
 */
@JsonSerialize(using = CodingSerializer::class)
@JsonDeserialize(using = CodingDeserializer::class)
data class Coding(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val system: Uri? = null,
    val version: FHIRString? = null,
    val code: Code? = null,
    val display: FHIRString? = null,
    val userSelected: FHIRBoolean? = null,
) : Element<Coding>

class CodingSerializer : BaseFHIRSerializer<Coding>(Coding::class.java)

class CodingDeserializer : BaseFHIRDeserializer<Coding>(Coding::class.java)
