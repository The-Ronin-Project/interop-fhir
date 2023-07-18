package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.generators.datatypes.ExtensionGenerator
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator
import com.projectronin.test.data.generator.util.IdentifierStringGenerator

class IdGenerator : DataGenerator<Id>() {
    val value: DataGenerator<String> = IdentifierStringGenerator()
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())

    override fun generateInternal() = Id(
        value = value.generate(),
        id = id.generate(),
        extension = extension.generate()
    )
}

fun id(block: IdGenerator.() -> Unit): Id {
    val id = IdGenerator()
    id.apply(block)
    return id.generate()
}

infix fun DataGenerator<Id?>.of(value: String) = of(Id(value))
