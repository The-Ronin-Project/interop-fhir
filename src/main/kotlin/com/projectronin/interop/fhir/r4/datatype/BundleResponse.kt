package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.Resource

/**
 * Results of execution (transaction/batch/history)
 */
@JsonSerialize(using = BundleResponseSerializer::class)
@JsonDeserialize(using = BundleResponseDeserializer::class)
data class BundleResponse(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val status: FHIRString?,
    val location: Uri? = null,
    val etag: FHIRString? = null,
    val lastModified: Instant? = null,
    val outcome: Resource<*>? = null
) : BackboneElement<BundleResponse>

class BundleResponseSerializer : BaseFHIRSerializer<BundleResponse>(BundleResponse::class.java)
class BundleResponseDeserializer : BaseFHIRDeserializer<BundleResponse>(BundleResponse::class.java)
