package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.element.Element

/**
 * The DoseAndRate structure supports Dosage, which is typically represented in medication requests,
 * medication dispenses and medication statements.
 */

@JsonDeserialize(using = DoseAndRateDeserializer::class)
@JsonSerialize(using = DoseAndRateSerializer::class)
data class DoseAndRate(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val type: CodeableConcept? = null,
    val dose: DynamicValue<Any>? = null,
    val rate: DynamicValue<Any>? = null
) : Element<DoseAndRate>

class DoseAndRateDeserializer : BaseFHIRDeserializer<DoseAndRate>(DoseAndRate::class.java)
class DoseAndRateSerializer : BaseFHIRSerializer<DoseAndRate>(DoseAndRate::class.java)
