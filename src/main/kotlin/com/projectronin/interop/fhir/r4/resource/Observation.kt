package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.ObservationDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.ObservationSerializer
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.ObservationComponent
import com.projectronin.interop.fhir.r4.datatype.ObservationReferenceRange
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.ObservationStatus

/**
 * Measurements and simple assertions.
 *
 * See [FHIR Spec](https://www.hl7.org/fhir/observation.html)
 */
@JsonDeserialize(using = ObservationDeserializer::class)
@JsonSerialize(using = ObservationSerializer::class)
@JsonTypeName("Observation")
data class Observation(
    override val id: Id? = null,
    override val meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<ContainedResource> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: List<Identifier> = listOf(),
    val basedOn: List<Reference> = listOf(),
    val partOf: List<Reference> = listOf(),
    val status: ObservationStatus,
    val category: List<CodeableConcept> = listOf(),
    val code: CodeableConcept,
    val subject: Reference? = null,
    val focus: List<Reference> = listOf(),
    val encounter: Reference? = null,
    val effective: DynamicValue<Any>? = null,
    val issued: Instant? = null,
    val performer: List<Reference> = listOf(),
    val value: DynamicValue<Any>? = null,
    val dataAbsentReason: CodeableConcept? = null,
    val interpretation: List<CodeableConcept> = listOf(),
    val bodySite: CodeableConcept? = null,
    val method: CodeableConcept? = null,
    val specimen: Reference? = null,
    val device: Reference? = null,
    val referenceRange: List<ObservationReferenceRange> = listOf(),
    val hasMember: List<Reference> = listOf(),
    val derivedFrom: List<Reference> = listOf(),
    val component: List<ObservationComponent> = listOf(),
    val note: List<Annotation> = listOf(),
) : DomainResource {
    override val resourceType: String = "Observation"

    companion object {
        val acceptedEffectives = listOf(
            DynamicValueType.DATE_TIME,
            DynamicValueType.PERIOD,
            DynamicValueType.TIMING,
            DynamicValueType.INSTANT
        )
    }

    init {
        // R4 Observation.value[x] data types are constrained by the ObservationStatus enum type, so no validation needed.
        effective?.let { data ->
            require(acceptedEffectives.contains(data.type)) {
                "Observation effective can only be one of the following data types: ${acceptedEffectives.joinToString { it.code }}"
            }
        }
        require(value == null || dataAbsentReason == null) {
            "dataAbsentReason SHALL only be present if value[x] is not present"
        }
        if (value != null) {
            require(component.all { child -> (child.code != code) }) {
                "If Observation.code is the same as an Observation.component.code then the Observation.value SHALL NOT be present"
            }
        }
    }
}
