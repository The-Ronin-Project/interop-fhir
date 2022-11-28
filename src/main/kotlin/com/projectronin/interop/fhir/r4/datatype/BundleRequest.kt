package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

/**
 * Additional execution information (transaction/batch/history)
 */
@JsonSerialize(using = BundleRequestSerializer::class)
@JsonDeserialize(using = BundleRequestDeserializer::class)
data class BundleRequest(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val method: Code?,
    val url: Uri?,
    val ifNoneMatch: FHIRString? = null,
    val ifModifiedSince: Instant? = null,
    val ifMatch: FHIRString? = null,
    val ifNoneExist: FHIRString? = null
) : BackboneElement<BundleRequest>

class BundleRequestSerializer : BaseFHIRSerializer<BundleRequest>(BundleRequest::class.java)
class BundleRequestDeserializer : BaseFHIRDeserializer<BundleRequest>(BundleRequest::class.java)
