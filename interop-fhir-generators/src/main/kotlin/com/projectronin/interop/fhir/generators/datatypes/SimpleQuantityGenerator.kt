package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

class SimpleQuantityGenerator : DataGenerator<SimpleQuantity>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val value: DataGenerator<Decimal?> = NullDataGenerator()
    val unit: DataGenerator<FHIRString?> = NullDataGenerator()
    val system: DataGenerator<Uri?> = NullDataGenerator()
    val code: DataGenerator<Code?> = NullDataGenerator()

    override fun generateInternal() =
        SimpleQuantity(
            value = value.generate(),
            unit = unit.generate(),
            system = system.generate(),
            code = code.generate(),
        )
}

fun simpleQuantity(block: SimpleQuantityGenerator.() -> Unit): SimpleQuantity {
    val quantity = SimpleQuantityGenerator()
    quantity.apply(block)
    return quantity.generate()
}
