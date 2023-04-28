package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.FHIRStringDataGenerator
import com.projectronin.interop.fhir.generators.primitives.MarkdownGenerator
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

class AnnotationGenerator : DataGenerator<Annotation>() {
    val id: FHIRStringDataGenerator = FHIRStringDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val author: DataGenerator<DynamicValue<Any>?> = NullDataGenerator()
    val time: DataGenerator<DateTime?> = NullDataGenerator()
    val text: DataGenerator<Markdown> = MarkdownGenerator()

    override fun generateInternal() = Annotation(
        id = id.generate(),
        extension = extension.generate(),
        author = author.generate(),
        time = time.generate(),
        text = text.generate()
    )
}

fun annotation(block: AnnotationGenerator.() -> Unit): Annotation {
    val annotation = AnnotationGenerator()
    annotation.apply(block)
    return annotation.generate()
}
