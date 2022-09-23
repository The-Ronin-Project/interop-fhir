package com.projectronin.interop.fhir.stu3.datatype

import com.projectronin.interop.fhir.r4.datatype.BundleEntry
import com.projectronin.interop.fhir.r4.datatype.BundleLink
import com.projectronin.interop.fhir.r4.datatype.BundleRequest
import com.projectronin.interop.fhir.r4.datatype.BundleResponse
import com.projectronin.interop.fhir.r4.datatype.BundleSearch
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.stu3.resource.STU3Resource

/**
 * Entry in the bundle - will have a resource or information
 *
 * See [FHIR Spec](http://www.hl7.org/fhir/bundle-definitions.html#Bundle.entry)
 */
data class STU3BundleEntry(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val link: List<BundleLink> = listOf(),
    val fullUrl: Uri? = null,
    val resource: STU3Resource<*>? = null,
    val search: BundleSearch? = null,
    val request: BundleRequest? = null,
    val response: BundleResponse? = null
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
            response
        )
    }
}
