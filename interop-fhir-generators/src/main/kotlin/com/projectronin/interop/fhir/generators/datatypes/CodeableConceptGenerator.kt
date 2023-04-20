package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.FHIRStringDataGenerator
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

class CodeableConceptGenerator : DataGenerator<CodeableConcept>() {
    val coding: ListDataGenerator<Coding> = ListDataGenerator(0, CodingGenerator())
    val text: FHIRStringDataGenerator = FHIRStringDataGenerator()

    override fun generateInternal(): CodeableConcept =
        CodeableConcept(
            coding = coding.generate(),
            text = text.generate()
        )
}

fun codeableConcept(block: CodeableConceptGenerator.() -> Unit): CodeableConcept {
    val codeableConcept = CodeableConceptGenerator()
    codeableConcept.apply(block)
    return codeableConcept.generate()
}
