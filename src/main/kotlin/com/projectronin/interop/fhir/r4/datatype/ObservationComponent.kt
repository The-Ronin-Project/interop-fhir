package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.ObservationComponentDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.ObservationComponentSerializer

/**
 * An Observation.component provides component results as type of component observation (code / type) and actual component result.
 */
@JsonDeserialize(using = ObservationComponentDeserializer::class)
@JsonSerialize(using = ObservationComponentSerializer::class)
data class ObservationComponent(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val code: CodeableConcept?,
    val value: DynamicValue<Any>? = null,
    val dataAbsentReason: CodeableConcept? = null,
    val interpretation: List<CodeableConcept> = listOf(),
    val referenceRange: List<ObservationReferenceRange> = listOf(),
) : BackboneElement<ObservationComponent>
