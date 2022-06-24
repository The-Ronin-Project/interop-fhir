package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.ObservationComponentDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.ObservationComponentSerializer

/**
 * An Observation.component provides component results as
 * type of component observation (code / type) and actual component result.
 * Note: code is required.
 *
 * See [FHIR Documentation](http://hl7.org/fhir/R4/observation.html)
 */

@JsonDeserialize(using = ObservationComponentDeserializer::class)
@JsonSerialize(using = ObservationComponentSerializer::class)
data class ObservationComponent(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val code: CodeableConcept,
    val value: DynamicValue<Any>?,
    val dataAbsentReason: CodeableConcept? = null,
    val interpretation: List<CodeableConcept> = listOf(),
    val referenceRange: List<ObservationReferenceRange> = listOf(),
) : BackboneElement
