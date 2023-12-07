package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.CodeGenerator
import com.projectronin.interop.fhir.generators.primitives.UriGenerator
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator

class CodingGenerator : DataGenerator<Coding>() {
    val system: UriGenerator = UriGenerator()
    val version: DataGenerator<FHIRString?> = NullDataGenerator()
    val code: CodeGenerator = CodeGenerator()
    val display: DataGenerator<FHIRString?> = NullDataGenerator()
    val userSelected: DataGenerator<FHIRBoolean?> = NullDataGenerator()

    override fun generateInternal(): Coding =
        Coding(
            system = system.generate(),
            version = version.generate(),
            code = code.generate(),
            display = display.generate(),
            userSelected = userSelected.generate(),
        )
}

fun coding(block: CodingGenerator.() -> Unit): Coding {
    val coding = CodingGenerator()
    coding.apply(block)
    return coding.generate()
}
