package com.projectronin.interop.fhir.r4.ronin.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.ronin.OncologyObservationDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.ronin.OncologyObservationSerializer
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
import com.projectronin.interop.fhir.r4.resource.ContainedResource
import com.projectronin.interop.fhir.r4.resource.base.BaseObservation
import com.projectronin.interop.fhir.r4.valueset.ObservationStatus

/**
 * Project Ronin definition of an Oncology Observation.
 *
 * See [Project Ronin Profile Spec](https://crispy-carnival-61996e6e.pages.github.io/StructureDefinition-oncology-observation.html)
 */

@JsonDeserialize(using = OncologyObservationDeserializer::class)
@JsonSerialize(using = OncologyObservationSerializer::class)
@JsonTypeName("Observation")
data class OncologyObservation(
    override val id: Id? = null,
    override val meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<ContainedResource> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    override val identifier: List<Identifier>,
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
) : RoninDomainResource, BaseObservation() {
    init {
        validate()

        requireTenantIdentifier(identifier)
    }
}
