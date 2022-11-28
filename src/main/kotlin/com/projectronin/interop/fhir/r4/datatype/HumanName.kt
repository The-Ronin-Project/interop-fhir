package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

/**
 * A name of a human with text, parts and usage information. Names may be changed or repudiated. People may have different
 * names in different contexts. Names may be divided into parts of different type that have variable significance depending
 * on context, though the division into parts is not always significant. With personal names, the different parts might
 * or might not be imbued with some implicit meaning; various cultures associate different importance with the name parts
 * and the degree to which systems SHALL care about name parts around the world varies widely.
 */
@JsonSerialize(using = HumanNameSerializer::class)
@JsonDeserialize(using = HumanNameDeserializer::class)
data class HumanName(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val use: Code? = null,
    val text: FHIRString? = null,
    val family: FHIRString? = null,
    val given: List<FHIRString> = listOf(),
    val prefix: List<FHIRString> = listOf(),
    val suffix: List<FHIRString> = listOf(),
    val period: Period? = null
) : Element<HumanName>

class HumanNameSerializer : BaseFHIRSerializer<HumanName>(HumanName::class.java)
class HumanNameDeserializer : BaseFHIRDeserializer<HumanName>(HumanName::class.java)
