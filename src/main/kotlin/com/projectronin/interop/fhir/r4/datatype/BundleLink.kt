package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

/**
 * A link that provides context to a bundle.
 */
@JsonSerialize(using = BundleLinkSerializer::class)
@JsonDeserialize(using = BundleLinkDeserializer::class)
data class BundleLink(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val relation: FHIRString?,
    val url: Uri?
) : BackboneElement<BundleLink>

class BundleLinkSerializer : BaseFHIRSerializer<BundleLink>(BundleLink::class.java)
class BundleLinkDeserializer : BaseFHIRDeserializer<BundleLink>(BundleLink::class.java)
