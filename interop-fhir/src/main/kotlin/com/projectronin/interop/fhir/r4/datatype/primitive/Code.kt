package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.annotation.JsonCreator
import com.projectronin.interop.common.enums.CodedEnum
import com.projectronin.interop.fhir.r4.datatype.Extension

/**
 * Indicates that the value is taken from a set of controlled strings defined elsewhere. Technically, a code is restricted to a string
 * which has at least one character and no leading or trailing whitespace, and where there is no whitespace other than single spaces in the contents
 */
data class Code @JsonCreator constructor(
    override val value: String?,
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf()
) : Primitive<String, Code> {
    @JsonCreator
    constructor(value: String) : this(value, null, emptyList())
}

inline fun <reified T> Code?.asEnum() where T : Enum<T>, T : CodedEnum<T> = this?.value?.let { CodedEnum.byCode<T>(it) }
