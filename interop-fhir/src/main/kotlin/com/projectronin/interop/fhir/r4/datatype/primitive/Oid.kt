package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator
import com.projectronin.interop.fhir.r4.datatype.Extension

/**
 * An OID represented as a URI (RFC 3001); e.g. urn:oid:1.2.3.4.5
 */
data class Oid(
    override val value: String?,
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf()
) : Primitive<String, Oid> {
    @JsonCreator
    constructor(value: String) : this(value, null, emptyList())
}
