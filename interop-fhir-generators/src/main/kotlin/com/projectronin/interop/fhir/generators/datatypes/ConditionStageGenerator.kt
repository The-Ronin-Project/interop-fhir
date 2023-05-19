package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.FHIRStringDataGenerator
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.resource.ConditionStage
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

class ConditionStageGenerator : DataGenerator<ConditionStage>() {
    val id: FHIRStringDataGenerator = FHIRStringDataGenerator()
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

fun conditionStage(block: ConditionStageGenerator.() -> Unit): ConditionStage {
    val conditionStage = ConditionStageGenerator()
    conditionStage.apply(block)
    return conditionStage.generate()
}
