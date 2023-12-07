package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Signature
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.BackboneElement
import com.projectronin.interop.fhir.r4.valueset.BundleType
import com.projectronin.interop.fhir.r4.valueset.HttpVerb
import com.projectronin.interop.fhir.r4.valueset.SearchEntryMode
import com.projectronin.interop.fhir.validate.annotation.RequiredField
import com.projectronin.interop.fhir.validate.annotation.RequiredValueSet

/**
 * A container for a collection of resources.
 */
@JsonSerialize(using = BundleSerializer::class)
@JsonDeserialize(using = BundleDeserializer::class)
data class Bundle(
    override val id: Id? = null,
    override var meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    val identifier: Identifier? = null,
    @RequiredField
    @RequiredValueSet(BundleType::class)
    val type: Code?,
    val timestamp: Instant? = null,
    val total: UnsignedInt? = null,
    val link: List<BundleLink> = listOf(),
    val entry: List<BundleEntry> = listOf(),
    val signature: Signature? = null,
) : Resource<Bundle> {
    override val resourceType: String = "Bundle"
}

class BundleSerializer : BaseFHIRSerializer<Bundle>(Bundle::class.java)

class BundleDeserializer : BaseFHIRDeserializer<Bundle>(Bundle::class.java)

/**
 * Entry in the bundle - will have a resource or information
 */
@JsonSerialize(using = BundleEntrySerializer::class)
@JsonDeserialize(using = BundleEntryDeserializer::class)
data class BundleEntry(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val link: List<BundleLink> = listOf(),
    val fullUrl: Uri? = null,
    val resource: Resource<*>? = null,
    val search: BundleSearch? = null,
    val request: BundleRequest? = null,
    val response: BundleResponse? = null,
) : BackboneElement<BundleEntry>

class BundleEntrySerializer : BaseFHIRSerializer<BundleEntry>(BundleEntry::class.java)

class BundleEntryDeserializer : BaseFHIRDeserializer<BundleEntry>(BundleEntry::class.java)

/**
 * A link that provides context to a bundle.
 */
@JsonSerialize(using = BundleLinkSerializer::class)
@JsonDeserialize(using = BundleLinkDeserializer::class)
data class BundleLink(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    @RequiredField
    val relation: FHIRString?,
    @RequiredField
    val url: Uri?,
) : BackboneElement<BundleLink>

class BundleLinkSerializer : BaseFHIRSerializer<BundleLink>(BundleLink::class.java)

class BundleLinkDeserializer : BaseFHIRDeserializer<BundleLink>(BundleLink::class.java)

/**
 * Additional execution information (transaction/batch/history)
 */
@JsonSerialize(using = BundleRequestSerializer::class)
@JsonDeserialize(using = BundleRequestDeserializer::class)
data class BundleRequest(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    @RequiredField
    @RequiredValueSet(HttpVerb::class)
    val method: Code?,
    @RequiredField
    val url: Uri?,
    val ifNoneMatch: FHIRString? = null,
    val ifModifiedSince: Instant? = null,
    val ifMatch: FHIRString? = null,
    val ifNoneExist: FHIRString? = null,
) : BackboneElement<BundleRequest>

class BundleRequestSerializer : BaseFHIRSerializer<BundleRequest>(BundleRequest::class.java)

class BundleRequestDeserializer : BaseFHIRDeserializer<BundleRequest>(BundleRequest::class.java)

/**
 * Results of execution (transaction/batch/history)
 */
@JsonSerialize(using = BundleResponseSerializer::class)
@JsonDeserialize(using = BundleResponseDeserializer::class)
data class BundleResponse(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    @RequiredField
    val status: FHIRString?,
    val location: Uri? = null,
    val etag: FHIRString? = null,
    val lastModified: Instant? = null,
    val outcome: Resource<*>? = null,
) : BackboneElement<BundleResponse>

class BundleResponseSerializer : BaseFHIRSerializer<BundleResponse>(BundleResponse::class.java)

class BundleResponseDeserializer : BaseFHIRDeserializer<BundleResponse>(BundleResponse::class.java)

/**
 * Search related information
 */
@JsonSerialize(using = BundleSearchSerializer::class)
@JsonDeserialize(using = BundleSearchDeserializer::class)
data class BundleSearch(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    @RequiredValueSet(SearchEntryMode::class)
    val mode: Code? = null,
    val score: Decimal? = null,
) : BackboneElement<BundleSearch>

class BundleSearchSerializer : BaseFHIRSerializer<BundleSearch>(BundleSearch::class.java)

class BundleSearchDeserializer : BaseFHIRDeserializer<BundleSearch>(BundleSearch::class.java)
