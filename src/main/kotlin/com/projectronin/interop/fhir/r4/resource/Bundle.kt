package com.projectronin.interop.fhir.r4.resource

import com.projectronin.interop.fhir.r4.datatype.BundleEntry
import com.projectronin.interop.fhir.r4.datatype.BundleLink
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Signature
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

/**
 * A container for a collection of resources.
 */
data class Bundle(
    override val id: Id? = null,
    override val meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    val identifier: Identifier? = null,
    val type: Code?,
    val timestamp: Instant? = null,
    val total: UnsignedInt? = null,
    val link: List<BundleLink> = listOf(),
    val entry: List<BundleEntry> = listOf(),
    val signature: Signature? = null
) : Resource<Bundle> {
    override val resourceType: String = "Bundle"
}
