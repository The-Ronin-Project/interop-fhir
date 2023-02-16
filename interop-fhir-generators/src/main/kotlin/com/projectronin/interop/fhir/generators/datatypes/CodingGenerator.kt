package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.primitive.asFHIR
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator

class CodingGenerator : DataGenerator<Coding>() {
    val system: UriGenerator = UriGenerator()
    val version: NullDataGenerator<String> = NullDataGenerator()
    val code: CodeGenerator = CodeGenerator()
    val display: NullDataGenerator<String> = NullDataGenerator()
    val userSelected: DataGenerator<Boolean?> = NullDataGenerator()

    override fun generateInternal(): Coding =
        Coding(
            system = system.generate(),
            version = version.generate()?.asFHIR(),
            code = code.generate(),
            display = display.generate()?.asFHIR(),
            userSelected = userSelected.generate()?.asFHIR()
        )
}

fun coding(block: CodingGenerator.() -> Unit): Coding {
    val coding = CodingGenerator()
    coding.apply(block)
    return coding.generate()
}
