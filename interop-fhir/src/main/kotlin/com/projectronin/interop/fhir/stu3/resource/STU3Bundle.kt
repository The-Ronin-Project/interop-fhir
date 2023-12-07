package com.projectronin.interop.fhir.stu3.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Signature
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.BundleEntry
import com.projectronin.interop.fhir.r4.resource.BundleLink
import com.projectronin.interop.fhir.stu3.element.STU3BundleEntry
import com.projectronin.interop.fhir.r4.resource.Bundle as R4Bundle

/**
 * A container for a collection of resources.
 *
 * See [FHIR Spec](http://www.hl7.org/fhir/bundle.html)
 */
@JsonSerialize(using = STU3BundleSerializer::class)
@JsonDeserialize(using = STU3BundleDeserializer::class)
@JsonTypeName("Bundle")
data class STU3Bundle(
    override val id: Id? = null,
    override var meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    val identifier: Identifier? = null,
    val type: Code?,
    val timestamp: Instant? = null,
    val total: UnsignedInt? = null,
    val link: List<BundleLink> = listOf(),
    val entry: List<STU3BundleEntry> = listOf(),
    val signature: Signature? = null,
) : STU3Resource<STU3Bundle> {
    override val resourceType: String = "Bundle"

    override fun transformToR4(): R4Bundle {
        return R4Bundle(
            id = id,
            meta = meta,
            implicitRules = implicitRules,
            language = language,
            identifier = identifier,
            type = type,
            timestamp = timestamp,
            total = total,
            link = link,
            entry =
                entry.map {
                    BundleEntry(
                        id = it.id,
                        extension = it.extension,
                        modifierExtension = it.modifierExtension,
                        link = it.link,
                        fullUrl = it.fullUrl,
                        resource = it.resource?.transformToR4(),
                        request = it.request,
                        response = it.response,
                        search = it.search,
                    )
                },
            signature = signature,
        )
    }
}

class STU3BundleSerializer : BaseFHIRSerializer<STU3Bundle>(STU3Bundle::class.java)

class STU3BundleDeserializer : BaseFHIRDeserializer<STU3Bundle>(STU3Bundle::class.java)
