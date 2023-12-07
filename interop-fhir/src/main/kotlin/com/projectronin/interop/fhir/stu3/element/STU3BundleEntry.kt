package com.projectronin.interop.fhir.stu3.element

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.BundleEntry
import com.projectronin.interop.fhir.r4.resource.BundleLink
import com.projectronin.interop.fhir.r4.resource.BundleRequest
import com.projectronin.interop.fhir.r4.resource.BundleResponse
import com.projectronin.interop.fhir.r4.resource.BundleSearch
import com.projectronin.interop.fhir.stu3.resource.STU3Resource

/**
 * Entry in the bundle - will have a resource or information
 *
 * See [FHIR Spec](http://www.hl7.org/fhir/bundle-definitions.html#Bundle.entry)
 */
@JsonSerialize(using = STU3BundleEntrySerializer::class)
@JsonDeserialize(using = STU3BundleEntryDeserializer::class)
data class STU3BundleEntry(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val link: List<BundleLink> = listOf(),
    val fullUrl: Uri? = null,
    val resource: STU3Resource<*>? = null,
    val search: BundleSearch? = null,
    val request: BundleRequest? = null,
    val response: BundleResponse? = null,
) : STU3BackboneElement<STU3BundleEntry> {
    override fun transformToR4(): BundleEntry {
        return BundleEntry(
            id,
            extension,
            modifierExtension,
            link,
            fullUrl,
            resource?.transformToR4(),
            search,
            request,
            response,
        )
    }
}

class STU3BundleEntrySerializer : BaseFHIRSerializer<STU3BundleEntry>(STU3BundleEntry::class.java)

class STU3BundleEntryDeserializer : BaseFHIRDeserializer<STU3BundleEntry>(STU3BundleEntry::class.java)
