package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator
import com.projectronin.interop.fhir.r4.datatype.Extension

/**
 * A UUID (aka GUID) represented as a URI (RFC 4122); e.g. urn:uuid:c757873d-ec9a-4326-a141-556f43239520
 */
data class Uuid(
    override val value: String?,
    override val id: String? = null,
    override val extension: List<Extension> = listOf()
) : Primitive<String, Uuid> {
    @JsonCreator
    constructor(value: String) : this(value, null, emptyList())
}
