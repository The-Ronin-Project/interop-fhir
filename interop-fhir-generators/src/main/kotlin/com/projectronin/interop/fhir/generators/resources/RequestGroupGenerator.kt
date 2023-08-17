package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.AnnotationGenerator
import com.projectronin.interop.fhir.generators.datatypes.CodeableConceptGenerator
import com.projectronin.interop.fhir.generators.datatypes.ExtensionGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.datatypes.ReferenceGenerator
import com.projectronin.interop.fhir.generators.datatypes.RelatedArtifactGenerator
import com.projectronin.interop.fhir.generators.primitives.CanonicalGenerator
import com.projectronin.interop.fhir.generators.primitives.CodeGenerator
import com.projectronin.interop.fhir.generators.primitives.IdGenerator
import com.projectronin.interop.fhir.generators.primitives.UriGenerator
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
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
import com.projectronin.interop.fhir.r4.resource.RequestGroup
import com.projectronin.interop.fhir.r4.resource.RequestGroupAction
import com.projectronin.interop.fhir.r4.resource.RequestGroupCondition
import com.projectronin.interop.fhir.r4.resource.RequestGroupRelatedAction
import com.projectronin.interop.fhir.r4.resource.Resource
import com.projectronin.interop.fhir.r4.valueset.RequestGroupConditionKind
import com.projectronin.interop.fhir.r4.valueset.RequestGroupIntent
import com.projectronin.interop.fhir.r4.valueset.RequestGroupPriority
import com.projectronin.interop.fhir.r4.valueset.RequestGroupRelatedActionRelationship
import com.projectronin.interop.fhir.r4.valueset.RequestGroupStatus
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class RequestGroupGenerator(
    override val id: DataGenerator<Id?> = NullDataGenerator(),
    override val meta: DataGenerator<Meta?> = NullDataGenerator(),
    override val implicitRules: DataGenerator<Uri?> = NullDataGenerator(),
    override val language: DataGenerator<Code?> = NullDataGenerator(),
    override val text: DataGenerator<Narrative?> = NullDataGenerator(),
    override val contained: ListDataGenerator<Resource<*>?> = ListDataGenerator(0, NullDataGenerator()),
    override val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    override val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val instantiatesCanonical: ListDataGenerator<Canonical> = ListDataGenerator(0, CanonicalGenerator()),
    val instantiatesUri: ListDataGenerator<Uri> = ListDataGenerator(0, UriGenerator()),
    val basedOn: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val replaces: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val groupIdentifier: DataGenerator<Identifier?> = NullDataGenerator(),
    val status: DataGenerator<Code?> = CodeGenerator(RequestGroupStatus::class),
    val intent: DataGenerator<Code?> = CodeGenerator(RequestGroupIntent::class),
    val priority: DataGenerator<Code?> = CodeGenerator(RequestGroupPriority::class),
    val code: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val subject: DataGenerator<Reference> = ReferenceGenerator(),
    val encounter: DataGenerator<Reference?> = NullDataGenerator(),
    val authoredOn: DataGenerator<DateTime?> = NullDataGenerator(),
    val author: DataGenerator<Reference?> = NullDataGenerator(),
    val reasonCode: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val reasonReference: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val note: ListDataGenerator<Annotation> = ListDataGenerator(0, AnnotationGenerator()),
    val action: ListDataGenerator<RequestGroupAction> = ListDataGenerator(0, RequestGroupActionGenerator())
) : DomainResource<RequestGroup> {
    override fun toFhir() =
        RequestGroup(
            id = id.generate(),
            meta = meta.generate(),
            implicitRules = implicitRules.generate(),
            language = language.generate(),
            text = text.generate(),
            contained = contained.generate().filterNotNull(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            identifier = identifier.generate(),
            instantiatesCanonical = instantiatesCanonical.generate(),
            instantiatesUri = instantiatesUri.generate(),
            basedOn = basedOn.generate(),
            replaces = replaces.generate(),
            groupIdentifier = groupIdentifier.generate(),
            status = status.generate(),
            intent = intent.generate(),
            priority = priority.generate(),
            code = code.generate(),
            subject = subject.generate(),
            encounter = encounter.generate(),
            authoredOn = authoredOn.generate(),
            author = author.generate(),
            reasonCode = reasonCode.generate(),
            reasonReference = reasonReference.generate(),
            note = note.generate(),
            action = action.generate()
        )
}

data class RequestGroupActionGenerator(
    val id: DataGenerator<FHIRString?> = NullDataGenerator(),
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val prefix: DataGenerator<FHIRString?> = NullDataGenerator(),
    val title: DataGenerator<FHIRString?> = NullDataGenerator(),
    val description: DataGenerator<FHIRString?> = NullDataGenerator(),
    val textEquivalent: DataGenerator<FHIRString?> = NullDataGenerator(),
    val priority: DataGenerator<Code?> = NullDataGenerator(),
    val documentation: ListDataGenerator<RelatedArtifact> = ListDataGenerator(0, RelatedArtifactGenerator()),
    val condition: ListDataGenerator<RequestGroupCondition> = ListDataGenerator(0, RequestGroupConditionGenerator()),
    val relatedAction: ListDataGenerator<RequestGroupRelatedAction> = ListDataGenerator(
        0,
        RequestGroupRelatedActionGenerator()
    ),
    val timing: DataGenerator<DynamicValue<Any>?> = NullDataGenerator(),
    val participant: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val type: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val groupingBehavior: DataGenerator<Code?> = NullDataGenerator(),
    val selectionBehavior: DataGenerator<Code?> = NullDataGenerator(),
    val requiredBehavior: DataGenerator<Code?> = NullDataGenerator(),
    val precheckBehavior: DataGenerator<Code?> = NullDataGenerator(),
    val cardinalityBehavior: DataGenerator<Code?> = NullDataGenerator(),
    val resource: DataGenerator<Reference?> = NullDataGenerator(),
    // This can not reference this class or we will StackOverflow.
    val action: ListDataGenerator<RequestGroupAction?> = ListDataGenerator(0, NullDataGenerator())
) : DataGenerator<RequestGroupAction>() {
    override fun generateInternal() =
        RequestGroupAction(
            id = id.generate(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            prefix = prefix.generate(),
            title = title.generate(),
            description = description.generate(),
            textEquivalent = textEquivalent.generate(),
            priority = priority.generate(),
            documentation = documentation.generate(),
            condition = condition.generate(),
            relatedAction = relatedAction.generate(),
            timing = timing.generate(),
            participant = participant.generate(),
            type = type.generate(),
            groupingBehavior = groupingBehavior.generate(),
            selectionBehavior = selectionBehavior.generate(),
            requiredBehavior = requiredBehavior.generate(),
            precheckBehavior = precheckBehavior.generate(),
            cardinalityBehavior = cardinalityBehavior.generate(),
            resource = resource.generate(),
            action = action.generate().filterNotNull()
        )
}

data class RequestGroupConditionGenerator(
    val id: DataGenerator<FHIRString?> = NullDataGenerator(),
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val kind: DataGenerator<Code?> = CodeGenerator(RequestGroupConditionKind::class),
    val expression: DataGenerator<Expression?> = NullDataGenerator()
) : DataGenerator<RequestGroupCondition>() {
    override fun generateInternal() =
        RequestGroupCondition(
            id = id.generate(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            kind = kind.generate(),
            expression = expression.generate()
        )
}

data class RequestGroupRelatedActionGenerator(
    val id: DataGenerator<FHIRString?> = NullDataGenerator(),
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val actionId: DataGenerator<Id> = IdGenerator(),
    val relationship: DataGenerator<Code?> = CodeGenerator(RequestGroupRelatedActionRelationship::class),
    val offset: DataGenerator<DynamicValue<Any>?> = NullDataGenerator()
) : DataGenerator<RequestGroupRelatedAction>() {
    override fun generateInternal() =
        RequestGroupRelatedAction(
            id = id.generate(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            actionId = actionId.generate(),
            relationship = relationship.generate(),
            offset = offset.generate()
        )
}

fun requestGroup(block: RequestGroupGenerator.() -> Unit): RequestGroup {
    val requestGroup = RequestGroupGenerator()
    requestGroup.apply(block)
    return requestGroup.toFhir()
}

fun requestGroupAction(block: RequestGroupActionGenerator.() -> Unit): RequestGroupAction {
    val requestGroupAction = RequestGroupActionGenerator()
    requestGroupAction.apply(block)
    return requestGroupAction.generate()
}

fun requestGroupCondition(block: RequestGroupConditionGenerator.() -> Unit): RequestGroupCondition {
    val requestGroupCondition = RequestGroupConditionGenerator()
    requestGroupCondition.apply(block)
    return requestGroupCondition.generate()
}

fun requestGroupRelatedAction(block: RequestGroupRelatedActionGenerator.() -> Unit): RequestGroupRelatedAction {
    val requestGroupRelatedAction = RequestGroupRelatedActionGenerator()
    requestGroupRelatedAction.apply(block)
    return requestGroupRelatedAction.generate()
}
