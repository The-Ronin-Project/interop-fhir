package com.projectronin.interop.fhir.r4.datatype.primitive

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.element.Element

/**
 * Defines additional data associated to a primitive.
 */
@JsonDeserialize(using = PrimitiveDataDeserializer::class)
@JsonSerialize(using = PrimitiveDataSerializer::class)
data class PrimitiveData(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf()
) : Element<PrimitiveData>

class PrimitiveDataSerializer : BaseFHIRSerializer<PrimitiveData>(PrimitiveData::class.java)
class PrimitiveDataDeserializer : BaseFHIRDeserializer<PrimitiveData>(PrimitiveData::class.java)
