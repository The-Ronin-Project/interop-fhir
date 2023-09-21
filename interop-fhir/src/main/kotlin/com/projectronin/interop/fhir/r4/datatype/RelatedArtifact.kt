package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Url
import com.projectronin.interop.fhir.r4.element.Element
import com.projectronin.interop.fhir.validate.annotation.RequiredField

/**
 * The RelatedArtifact structure defines resources related to a module such as previous and next versions of documents,
 * documentation, citations, etc. Note that the name resource here is being used in a more general sense than the
 * FHIR-specific Resource. The related resource may be a FHIR resource, or it may be another type of resource,
 * represented using the Attachment data type.
 */
@JsonSerialize(using = RelatedArtifactSerializer::class)
@JsonDeserialize(using = RelatedArtifactDeserializer::class)
data class RelatedArtifact(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    @RequiredField
    val type: Code?,
    val label: FHIRString? = null,
    val display: FHIRString? = null,
    val citation: Markdown? = null,
    val url: Url? = null,
    val document: Attachment? = null,
    val resource: Canonical? = null
) : Element<RelatedArtifact>

class RelatedArtifactSerializer : BaseFHIRSerializer<RelatedArtifact>(RelatedArtifact::class.java)
class RelatedArtifactDeserializer : BaseFHIRDeserializer<RelatedArtifact>(RelatedArtifact::class.java)
