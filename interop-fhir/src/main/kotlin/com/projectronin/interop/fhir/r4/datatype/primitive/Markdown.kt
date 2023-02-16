package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator
import com.projectronin.interop.fhir.r4.datatype.Extension

/**
 * A FHIR string that may contain markdown syntax for optional processing by a markdown presentation engine, in the GFM extension of CommonMark format
 *
 * See [FHIR documentation](http://hl7.org/fhir/R4/datatypes.html#markdown)
 */
data class Markdown(
    override val value: String?,
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf()
) :
    Primitive<String, Markdown> {
    @JsonCreator
    constructor(value: String) : this(value, null, emptyList())
}
