package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.FHIRStringDataGenerator
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.EmptyListDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

class ExtensionGenerator : DataGenerator<Extension>() {
    val id: FHIRStringDataGenerator = FHIRStringDataGenerator()

    // This has to be like this to prevent StackOverflowErrors.
    val extension: ListDataGenerator<Extension?> = EmptyListDataGenerator()
    val url: DataGenerator<Uri?> = NullDataGenerator()
    val value: DataGenerator<DynamicValue<Any>?> = NullDataGenerator()

    override fun generateInternal() = Extension(
        id = id.generate(),
        extension = extension.generate().filterNotNull(),
        url = url.generate(),
        value = value.generate()
    )
}

fun extension(block: ExtensionGenerator.() -> Unit): Extension {
    val extension = ExtensionGenerator()
    extension.apply(block)
    return extension.generate()
}
