package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.Element
import com.projectronin.interop.fhir.validate.annotation.RequiredField

/**
 * Every element in a resource or data type includes an optional "extension" child element that may be present any number of times.
 */
@JsonDeserialize(using = ExtensionDeserializer::class)
@JsonSerialize(using = ExtensionSerializer::class)
data class Extension(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    @RequiredField
    val url: Uri? = null,
    val value: DynamicValue<Any>? = null,
) : Element<Extension>

class ExtensionDeserializer : BaseFHIRDeserializer<Extension>(Extension::class.java)

class ExtensionSerializer : BaseFHIRSerializer<Extension>(Extension::class.java)
