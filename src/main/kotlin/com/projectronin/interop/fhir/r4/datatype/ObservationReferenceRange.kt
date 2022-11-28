package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

/**
 * An Observation.referenceRange provides guide for interpretation.
 * Rule: Must have at least a low or a high or text.
 */
@JsonSerialize(using = ObservationReferenceRangeSerializer::class)
@JsonDeserialize(using = ObservationReferenceRangeDeserializer::class)
data class ObservationReferenceRange(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val low: SimpleQuantity? = null,
    val high: SimpleQuantity? = null,
    val type: CodeableConcept? = null,
    val appliesTo: List<CodeableConcept> = listOf(),
    val age: Range? = null,
    val text: FHIRString? = null,
) : BackboneElement<ObservationReferenceRange>

class ObservationReferenceRangeSerializer :
    BaseFHIRSerializer<ObservationReferenceRange>(ObservationReferenceRange::class.java)

class ObservationReferenceRangeDeserializer :
    BaseFHIRDeserializer<ObservationReferenceRange>(ObservationReferenceRange::class.java)
