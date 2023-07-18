package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.generators.datatypes.ExtensionGenerator
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator
import com.projectronin.test.data.generator.faker.SentenceGenerator

class MarkdownGenerator : DataGenerator<Markdown>() {
    val value: DataGenerator<String> = SentenceGenerator()
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())

    override fun generateInternal() = Markdown(
        value = value.generate(),
        id = id.generate(),
        extension = extension.generate()
    )
}

fun markdown(block: MarkdownGenerator.() -> Unit): Markdown {
    val markdown = MarkdownGenerator()
    markdown.apply(block)
    return markdown.generate()
}

infix fun DataGenerator<Markdown?>.of(value: String) = of(Markdown(value))
