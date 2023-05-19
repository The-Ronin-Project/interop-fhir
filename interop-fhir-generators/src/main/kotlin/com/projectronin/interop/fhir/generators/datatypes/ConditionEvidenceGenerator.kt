package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.FHIRStringDataGenerator
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.resource.ConditionEvidence
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

class ConditionEvidenceGenerator : DataGenerator<ConditionEvidence>() {
    val id: FHIRStringDataGenerator = FHIRStringDataGenerator()
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

fun conditionEvidence(block: ConditionEvidenceGenerator.() -> Unit): ConditionEvidence {
    val conditionEvidence = ConditionEvidenceGenerator()
    conditionEvidence.apply(block)
    return conditionEvidence.generate()
}
