package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.ObservationDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.ObservationSerializer
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
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
    val status: Code?,
    val category: List<CodeableConcept> = listOf(),
    val code: CodeableConcept?,
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
) : DomainResource<Observation> {
    override val resourceType: String = "Observation"
}
