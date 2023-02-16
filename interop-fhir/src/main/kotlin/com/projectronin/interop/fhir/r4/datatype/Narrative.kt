package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.element.Element

/**
 * Any resource that is a DomainResource (all resources except Bundle, Parameters and Binary) may include a human-readable
 * narrative that contains a summary of the resource and may be used to represent the content of the resource to a human.
 */
@JsonSerialize(using = NarrativeSerializer::class)
@JsonDeserialize(using = NarrativeDeserializer::class)
data class Narrative(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val status: Code?,
    val div: FHIRString?
) : Element<Narrative>

class NarrativeSerializer : BaseFHIRSerializer<Narrative>(Narrative::class.java)
class NarrativeDeserializer : BaseFHIRDeserializer<Narrative>(Narrative::class.java)
