package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.event.interop.internal.v1.ResourceType
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.element.Element
import com.projectronin.interop.fhir.validate.annotation.SupportedReferenceTypes

/**
 * A text note which also contains information about who made the statement and when.
 */
@JsonDeserialize(using = AnnotationDeserializer::class)
@JsonSerialize(using = AnnotationSerializer::class)
data class Annotation(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    @SupportedReferenceTypes(
        ResourceType.Practitioner,
        ResourceType.Patient,
        ResourceType.RelatedPerson,
        ResourceType.Organization
    )
    val author: DynamicValue<Any>? = null,
    val time: DateTime? = null,
    val text: Markdown?
) : Element<Annotation>

class AnnotationDeserializer : BaseFHIRDeserializer<Annotation>(Annotation::class.java)
class AnnotationSerializer : BaseFHIRSerializer<Annotation>(Annotation::class.java)
