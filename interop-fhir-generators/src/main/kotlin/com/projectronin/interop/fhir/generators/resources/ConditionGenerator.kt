package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.AnnotationGenerator
import com.projectronin.interop.fhir.generators.datatypes.CodeableConceptGenerator
import com.projectronin.interop.fhir.generators.datatypes.ExtensionGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.datatypes.ReferenceGenerator
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.Condition
import com.projectronin.interop.fhir.r4.resource.ConditionEvidence
import com.projectronin.interop.fhir.r4.resource.ConditionStage
import com.projectronin.interop.fhir.r4.resource.ContainedResource
import com.projectronin.interop.fhir.r4.valueset.ConditionClinicalStatusCodes
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class ConditionGenerator(
    override val id: DataGenerator<Id?> = NullDataGenerator(),
    override val meta: DataGenerator<Meta?> = NullDataGenerator(),
    override val implicitRules: DataGenerator<Uri?> = NullDataGenerator(),
    override val language: DataGenerator<Code?> = NullDataGenerator(),
    override val text: DataGenerator<Narrative?> = NullDataGenerator(),
    override val contained: ListDataGenerator<ContainedResource?> = ListDataGenerator(0, NullDataGenerator()),
    override val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    override val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val clinicalStatus: DataGenerator<CodeableConcept> = CodeableConceptGenerator(ConditionClinicalStatusCodes::class),
    val verificationStatus: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val category: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val severity: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val code: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val bodySite: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val subject: DataGenerator<Reference> = ReferenceGenerator(),
    val encounter: DataGenerator<Reference?> = NullDataGenerator(),
    val onset: DataGenerator<DynamicValue<Any>?> = NullDataGenerator(),
    val abatement: DataGenerator<DynamicValue<Any>?> = NullDataGenerator(),
    val recordedDate: DataGenerator<DateTime?> = NullDataGenerator(),
    val recorder: DataGenerator<Reference?> = NullDataGenerator(),
    val asserter: DataGenerator<Reference?> = NullDataGenerator(),
    val stage: ListDataGenerator<ConditionStage> = ListDataGenerator(0, ConditionStageGenerator()),
    val evidence: ListDataGenerator<ConditionEvidence> = ListDataGenerator(0, ConditionEvidenceGenerator()),
    val note: ListDataGenerator<Annotation> = ListDataGenerator(0, AnnotationGenerator())
) : DomainResource<Condition> {
    override fun toFhir(): Condition =
        Condition(
            id = id.generate(),
            meta = meta.generate(),
            implicitRules = implicitRules.generate(),
            language = language.generate(),
            text = text.generate(),
            contained = contained.generate().filterNotNull(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            identifier = identifier.generate(),
            // By default, we will fill in a clinical status to CON-3 (clinical status must be populated when validation isn't)
            clinicalStatus = clinicalStatus.generate(),
            verificationStatus = verificationStatus.generate(),
            category = category.generate(),
            severity = severity.generate(),
            code = code.generate(),
            bodySite = bodySite.generate(),
            subject = subject.generate(),
            encounter = encounter.generate(),
            onset = onset.generate(),
            abatement = abatement.generate(),
            recordedDate = recordedDate.generate(),
            recorder = recorder.generate(),
            asserter = asserter.generate(),
            stage = stage.generate(),
            evidence = evidence.generate(),
            note = note.generate()
        )
}

class ConditionEvidenceGenerator : DataGenerator<ConditionEvidence>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val code: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator())
    val detail: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator())

    override fun generateInternal() = ConditionEvidence(
        id = id.generate(),
        extension = extension.generate(),
        modifierExtension = modifierExtension.generate(),
        code = code.generate(),
        detail = detail.generate()
    )
}

class ConditionStageGenerator : DataGenerator<ConditionStage>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val summary: DataGenerator<CodeableConcept?> = NullDataGenerator()
    val assessment: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator())
    val type: DataGenerator<CodeableConcept?> = NullDataGenerator()

    override fun generateInternal() = ConditionStage(
        id = id.generate(),
        extension = extension.generate(),
        modifierExtension = modifierExtension.generate(),
        summary = summary.generate(),
        assessment = assessment.generate(),
        type = type.generate()
    )
}

fun condition(block: ConditionGenerator.() -> Unit): Condition {
    val condition = ConditionGenerator()
    condition.apply(block)
    return condition.toFhir()
}

fun conditionEvidence(block: ConditionEvidenceGenerator.() -> Unit): ConditionEvidence {
    val conditionEvidence = ConditionEvidenceGenerator()
    conditionEvidence.apply(block)
    return conditionEvidence.generate()
}

fun conditionStage(block: ConditionStageGenerator.() -> Unit): ConditionStage {
    val conditionStage = ConditionStageGenerator()
    conditionStage.apply(block)
    return conditionStage.generate()
}
