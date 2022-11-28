package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.Resource

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
    val response: BundleResponse? = null
) : BackboneElement<BundleEntry>

class BundleEntrySerializer : BaseFHIRSerializer<BundleEntry>(BundleEntry::class.java)
class BundleEntryDeserializer : BaseFHIRDeserializer<BundleEntry>(BundleEntry::class.java)
