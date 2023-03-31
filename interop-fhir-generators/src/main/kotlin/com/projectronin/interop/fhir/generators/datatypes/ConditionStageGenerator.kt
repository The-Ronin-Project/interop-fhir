package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.resource.ConditionStage
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

class ConditionStageGenerator : DataGenerator<ConditionStage>() {
    val assessment: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator())

    override fun generateInternal(): ConditionStage =
        ConditionStage(
            assessment = assessment.generate()
        )
}

fun conditionStage(block: ConditionStageGenerator.() -> Unit): ConditionStage {
    val conditionStage = ConditionStageGenerator()
    conditionStage.apply(block)
    return conditionStage.generate()
}
