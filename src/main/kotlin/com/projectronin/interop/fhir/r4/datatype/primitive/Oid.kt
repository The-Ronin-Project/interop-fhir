package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator

/**
 * An OID represented as a URI (RFC 3001); e.g. urn:oid:1.2.3.4.5
 */
data class Oid @JsonCreator constructor(override val value: String) : Primitive<String, Oid>
