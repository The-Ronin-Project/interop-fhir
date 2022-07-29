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
import com.projectronin.interop.fhir.r4.resource.base.BaseObservation
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
    override val identifier: List<Identifier> = listOf(),
    override val basedOn: List<Reference> = listOf(),
    override val partOf: List<Reference> = listOf(),
    override val status: ObservationStatus,
    override val category: List<CodeableConcept> = listOf(),
    override val code: CodeableConcept,
    override val subject: Reference? = null,
    override val focus: List<Reference> = listOf(),
    override val encounter: Reference? = null,
    override val effective: DynamicValue<Any>? = null,
    override val issued: Instant? = null,
    override val performer: List<Reference> = listOf(),
    override val value: DynamicValue<Any>? = null,
    override val dataAbsentReason: CodeableConcept? = null,
    override val interpretation: List<CodeableConcept> = listOf(),
    override val bodySite: CodeableConcept? = null,
    override val method: CodeableConcept? = null,
    override val specimen: Reference? = null,
    override val device: Reference? = null,
    override val referenceRange: List<ObservationReferenceRange> = listOf(),
    override val hasMember: List<Reference> = listOf(),
    override val derivedFrom: List<Reference> = listOf(),
    override val component: List<ObservationComponent> = listOf(),
    override val note: List<Annotation> = listOf(),
) : DomainResource, BaseObservation() {
    init {
        validate().alertIfErrors()
    }
}
