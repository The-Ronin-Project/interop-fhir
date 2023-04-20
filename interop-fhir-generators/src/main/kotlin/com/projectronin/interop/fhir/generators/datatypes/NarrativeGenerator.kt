package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.CodeGenerator
import com.projectronin.interop.fhir.generators.primitives.FHIRStringDataGenerator
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

class NarrativeGenerator : DataGenerator<Narrative>() {
    val id: FHIRStringDataGenerator = FHIRStringDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val status: CodeGenerator = CodeGenerator()
    val div: FHIRStringDataGenerator = FHIRStringDataGenerator()

    override fun generateInternal() = Narrative(
        id = id.generate(),
        extension = extension.generate(),
        status = status.generate(),
        div = div.generate()
    )
}

fun narrative(block: NarrativeGenerator.() -> Unit): Narrative {
    val narrative = NarrativeGenerator()
    narrative.apply(block)
    return narrative.generate()
}
