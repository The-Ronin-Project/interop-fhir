package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator

/**
 * A UUID (aka GUID) represented as a URI (RFC 4122); e.g. urn:uuid:c757873d-ec9a-4326-a141-556f43239520
 */
data class Uuid @JsonCreator constructor(override val value: String) : Primitive<String, Uuid>
