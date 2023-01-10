package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.element.Element

/**
 * The UsageContext structure defines the context of use for a module.
 */
@JsonDeserialize(using = UsageContextDeserializer::class)
@JsonSerialize(using = UsageContextSerializer::class)
data class UsageContext(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val code: Coding?,
    val value: DynamicValue<Any>?
) : Element<UsageContext>

class UsageContextDeserializer : BaseFHIRDeserializer<UsageContext>(UsageContext::class.java)
class UsageContextSerializer : BaseFHIRSerializer<UsageContext>(UsageContext::class.java)
