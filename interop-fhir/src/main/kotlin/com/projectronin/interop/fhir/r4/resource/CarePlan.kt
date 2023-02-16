package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.BackboneElement

@JsonDeserialize(using = CarePlanDeserializer::class)
@JsonSerialize(using = CarePlanSerializer::class)
@JsonTypeName("CarePlan")
data class CarePlan(
    override val id: Id? = null,
    override val meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<ContainedResource> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: List<Identifier> = listOf(),
    val instantiatesCanonical: List<Canonical> = listOf(),
    val instantiatesUri: List<Uri> = listOf(),
    val basedOn: List<Reference> = listOf(),
    val replaces: List<Reference> = listOf(),
    val partOf: List<Reference> = listOf(),
    val status: Code? = null,
    val intent: Code? = null,
    val category: List<CodeableConcept> = listOf(),
    val title: FHIRString? = null,
    val description: FHIRString? = null,
    val subject: Reference? = null,
    val encounter: Reference? = null,
    val period: Period? = null,
    val created: DateTime? = null,
    val author: Reference? = null,
    val contributor: List<Reference> = listOf(),
    val careTeam: List<Reference> = listOf(),
    val addresses: List<Reference> = listOf(),
    val supportingInfo: List<Reference> = listOf(),
    val goal: List<Reference> = listOf(),
    val activity: List<CarePlanActivity> = listOf(),
    val note: List<Annotation> = listOf()
) : DomainResource<CarePlan> {
    override val resourceType: String = "CarePlan"
}

class CarePlanSerializer : BaseFHIRSerializer<CarePlan>(CarePlan::class.java)
class CarePlanDeserializer : BaseFHIRDeserializer<CarePlan>(CarePlan::class.java)

@JsonSerialize(using = CarePlanActivitySerializer::class)
@JsonDeserialize(using = CarePlanActivityDeserializer::class)
data class CarePlanActivity(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val outcomeCodeableConcept: List<CodeableConcept> = listOf(),
    val outcomeReference: List<Reference> = listOf(),
    val progress: List<Annotation> = listOf(),
    val reference: Reference? = null,
    val detail: CarePlanDetail? = null
) : BackboneElement<CarePlanActivity>

class CarePlanActivitySerializer : BaseFHIRSerializer<CarePlanActivity>(CarePlanActivity::class.java)
class CarePlanActivityDeserializer : BaseFHIRDeserializer<CarePlanActivity>(CarePlanActivity::class.java)

@JsonDeserialize(using = CarePlanDetailDeserializer::class)
@JsonSerialize(using = CarePlanDetailSerializer::class)
data class CarePlanDetail(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val kind: Code? = null,
    val instantiatesCanonical: List<Canonical> = listOf(),
    val instantiatesUri: Uri? = null,
    val code: CodeableConcept? = null,
    val reasonCode: List<CodeableConcept> = listOf(),
    val reasonReference: List<Reference> = listOf(),
    val goal: List<Reference> = listOf(),
    val status: Code? = null,
    val statusReason: CodeableConcept? = null,
    val doNotPerform: FHIRBoolean? = null,
    val scheduled: DynamicValue<Any>? = null,
    val location: Reference? = null,
    val performer: List<Reference> = listOf(),
    val product: DynamicValue<Any>? = null,
    val dailyAmount: SimpleQuantity? = null,
    val quantity: SimpleQuantity? = null,
    val description: FHIRString? = null
) : BackboneElement<CarePlanDetail>

class CarePlanDetailDeserializer : BaseFHIRDeserializer<CarePlanDetail>(CarePlanDetail::class.java)
class CarePlanDetailSerializer : BaseFHIRSerializer<CarePlanDetail>(CarePlanDetail::class.java)
