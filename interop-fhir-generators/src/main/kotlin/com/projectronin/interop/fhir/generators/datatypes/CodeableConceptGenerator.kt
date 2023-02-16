package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.primitive.asFHIR
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

class CodeableConceptGenerator : DataGenerator<CodeableConcept>() {
    val coding: ListDataGenerator<Coding> = ListDataGenerator(0, CodingGenerator())
    val text: NullDataGenerator<String> = NullDataGenerator()

    override fun generateInternal(): CodeableConcept =
        CodeableConcept(
            coding = coding.generate(),
            text = text.generate()?.asFHIR()
        )
}

fun codeableConcept(block: CodeableConceptGenerator.() -> Unit): CodeableConcept {
    val codeableConcept = CodeableConceptGenerator()
    codeableConcept.apply(block)
    return codeableConcept.generate()
}
