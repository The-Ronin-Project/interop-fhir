package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown

/**
 * A text note which also contains information about who made the statement and when.
 */
@JsonDeserialize(using = AnnotationDeserializer::class)
@JsonSerialize(using = AnnotationSerializer::class)
data class Annotation(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    val author: DynamicValue<Any>? = null,
    val time: DateTime? = null,
    val text: Markdown?
) : Element<Annotation>

class AnnotationDeserializer : BaseFHIRDeserializer<Annotation>(Annotation::class.java)
class AnnotationSerializer : BaseFHIRSerializer<Annotation>(Annotation::class.java)
