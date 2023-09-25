package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.event.interop.internal.v1.ResourceType
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Expression
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.RelatedArtifact
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.BackboneElement
import com.projectronin.interop.fhir.r4.valueset.RequestGroupActionCardinalityBehavior
import com.projectronin.interop.fhir.r4.valueset.RequestGroupActionGroupingBehavior
import com.projectronin.interop.fhir.r4.valueset.RequestGroupActionPrecheckBehavior
import com.projectronin.interop.fhir.r4.valueset.RequestGroupActionRequiredBehavior
import com.projectronin.interop.fhir.r4.valueset.RequestGroupActionSelectionBehavior
import com.projectronin.interop.fhir.r4.valueset.RequestGroupConditionKind
import com.projectronin.interop.fhir.r4.valueset.RequestGroupRelatedActionRelationship
import com.projectronin.interop.fhir.r4.valueset.RequestIntent
import com.projectronin.interop.fhir.r4.valueset.RequestPriority
import com.projectronin.interop.fhir.r4.valueset.RequestStatus
import com.projectronin.interop.fhir.validate.annotation.RequiredField
import com.projectronin.interop.fhir.validate.annotation.RequiredValueSet
import com.projectronin.interop.fhir.validate.annotation.SupportedDynamicValueTypes
import com.projectronin.interop.fhir.validate.annotation.SupportedReferenceTypes

@JsonSerialize(using = RequestGroupSerializer::class)
@JsonDeserialize(using = RequestGroupDeserializer::class)
@JsonTypeName("RequestGroup")
data class RequestGroup(
    override val id: Id? = null,
    override var meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<Resource<*>> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: List<Identifier> = listOf(),
    val instantiatesCanonical: List<Canonical> = listOf(),
    val instantiatesUri: List<Uri> = listOf(),
    val basedOn: List<Reference> = listOf(),
    val replaces: List<Reference> = listOf(),
    val groupIdentifier: Identifier? = null,
    @RequiredField
    @RequiredValueSet(RequestStatus::class)
    val status: Code?,
    @RequiredField
    @RequiredValueSet(RequestIntent::class)
    val intent: Code?,
    @RequiredValueSet(RequestPriority::class)
    val priority: Code? = null,
    val code: CodeableConcept? = null,
    @SupportedReferenceTypes(ResourceType.Patient, ResourceType.Group)
    val subject: Reference? = null,
    @SupportedReferenceTypes(ResourceType.Encounter)
    val encounter: Reference? = null,
    val authoredOn: DateTime? = null,
    @SupportedReferenceTypes(ResourceType.Device, ResourceType.Practitioner, ResourceType.PractitionerRole)
    val author: Reference? = null,
    val reasonCode: List<CodeableConcept> = listOf(),
    @SupportedReferenceTypes(
        ResourceType.Condition,
        ResourceType.Observation,
        ResourceType.DiagnosticReport,
        ResourceType.DocumentReference
    )
    val reasonReference: List<Reference> = listOf(),
    val note: List<Annotation> = listOf(),
    val action: List<RequestGroupAction> = listOf()
) : DomainResource<RequestGroup> {
    override val resourceType: String = "RequestGroup"
}

class RequestGroupSerializer : BaseFHIRSerializer<RequestGroup>(RequestGroup::class.java)
class RequestGroupDeserializer : BaseFHIRDeserializer<RequestGroup>(RequestGroup::class.java)

@JsonSerialize(using = RequestGroupActionSerializer::class)
@JsonDeserialize(using = RequestGroupActionDeserializer::class)
data class RequestGroupAction(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val prefix: FHIRString? = null,
    val title: FHIRString? = null,
    val description: FHIRString? = null,
    val textEquivalent: FHIRString? = null,
    @RequiredValueSet(RequestPriority::class)
    val priority: Code? = null,
    val code: List<CodeableConcept> = listOf(),
    val documentation: List<RelatedArtifact> = listOf(),
    val condition: List<RequestGroupCondition> = listOf(),
    val relatedAction: List<RequestGroupRelatedAction> = listOf(),
    @SupportedDynamicValueTypes(
        DynamicValueType.DATE_TIME,
        DynamicValueType.AGE,
        DynamicValueType.PERIOD,
        DynamicValueType.DURATION,
        DynamicValueType.RANGE,
        DynamicValueType.TIMING
    )
    val timing: DynamicValue<Any>? = null,
    @SupportedReferenceTypes(
        ResourceType.Patient,
        ResourceType.Practitioner,
        ResourceType.PractitionerRole,
        ResourceType.RelatedPerson,
        ResourceType.Device
    )
    val participant: List<Reference> = listOf(),
    val type: CodeableConcept? = null,
    @RequiredValueSet(RequestGroupActionGroupingBehavior::class)
    val groupingBehavior: Code? = null,
    @RequiredValueSet(RequestGroupActionSelectionBehavior::class)
    val selectionBehavior: Code? = null,
    @RequiredValueSet(RequestGroupActionRequiredBehavior::class)
    val requiredBehavior: Code? = null,
    @RequiredValueSet(RequestGroupActionPrecheckBehavior::class)
    val precheckBehavior: Code? = null,
    @RequiredValueSet(RequestGroupActionCardinalityBehavior::class)
    val cardinalityBehavior: Code? = null,
    val resource: Reference? = null,
    val action: List<RequestGroupAction> = emptyList()
) : BackboneElement<RequestGroupAction>

class RequestGroupActionSerializer : BaseFHIRSerializer<RequestGroupAction>(RequestGroupAction::class.java)
class RequestGroupActionDeserializer : BaseFHIRDeserializer<RequestGroupAction>(RequestGroupAction::class.java)

@JsonSerialize(using = RequestGroupConditionSerializer::class)
@JsonDeserialize(using = RequestGroupConditionDeserializer::class)
data class RequestGroupCondition(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    @RequiredField
    @RequiredValueSet(RequestGroupConditionKind::class)
    val kind: Code?,
    val expression: Expression? = null
) : BackboneElement<RequestGroupCondition>

class RequestGroupConditionSerializer : BaseFHIRSerializer<RequestGroupCondition>(RequestGroupCondition::class.java)
class RequestGroupConditionDeserializer : BaseFHIRDeserializer<RequestGroupCondition>(RequestGroupCondition::class.java)

@JsonSerialize(using = RequestGroupRelatedActionSerializer::class)
@JsonDeserialize(using = RequestGroupRelatedActionDeserializer::class)
data class RequestGroupRelatedAction(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    @RequiredField
    val actionId: Id?,
    @RequiredField
    @RequiredValueSet(RequestGroupRelatedActionRelationship::class)
    val relationship: Code?,
    @SupportedDynamicValueTypes(DynamicValueType.DURATION, DynamicValueType.RANGE)
    val offset: DynamicValue<Any>? = null
) : BackboneElement<RequestGroupRelatedAction>

class RequestGroupRelatedActionSerializer :
    BaseFHIRSerializer<RequestGroupRelatedAction>(RequestGroupRelatedAction::class.java)

class RequestGroupRelatedActionDeserializer :
    BaseFHIRDeserializer<RequestGroupRelatedAction>(RequestGroupRelatedAction::class.java)
