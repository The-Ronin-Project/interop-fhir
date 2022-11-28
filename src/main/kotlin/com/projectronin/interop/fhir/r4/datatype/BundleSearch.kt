package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

/**
 * Search related information
 */
@JsonSerialize(using = BundleSearchSerializer::class)
@JsonDeserialize(using = BundleSearchDeserializer::class)
data class BundleSearch(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val mode: Code? = null,
    val score: Decimal? = null
) : BackboneElement<BundleSearch>

class BundleSearchSerializer : BaseFHIRSerializer<BundleSearch>(BundleSearch::class.java)
class BundleSearchDeserializer : BaseFHIRDeserializer<BundleSearch>(BundleSearch::class.java)
