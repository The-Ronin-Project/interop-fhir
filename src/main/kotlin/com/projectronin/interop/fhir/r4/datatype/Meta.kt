package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

/**
 * Set of metadata that provides technical and workflow context to the resource.
 */
@JsonSerialize(using = MetaSerializer::class)
@JsonDeserialize(using = MetaDeserializer::class)
data class Meta(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val versionId: Id? = null,
    val lastUpdated: Instant? = null,
    val source: Uri? = null,
    val profile: List<Canonical> = listOf(),
    val security: List<Coding> = listOf(),
    val tag: List<Coding> = listOf()
) : Element<Meta>

class MetaSerializer : BaseFHIRSerializer<Meta>(Meta::class.java)
class MetaDeserializer : BaseFHIRDeserializer<Meta>(Meta::class.java)
