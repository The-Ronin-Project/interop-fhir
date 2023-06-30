package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

class QuantityGenerator : DataGenerator<Quantity>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val value: DataGenerator<Decimal?> = NullDataGenerator()
    val comparator: DataGenerator<Code?> = NullDataGenerator()
    val unit: DataGenerator<FHIRString?> = NullDataGenerator()
    val system: DataGenerator<Uri?> = NullDataGenerator()
    val code: DataGenerator<Code?> = NullDataGenerator()

    override fun generateInternal() = Quantity(
        value = value.generate(),
        comparator = comparator.generate(),
        unit = unit.generate(),
        system = system.generate(),
        code = code.generate()
    )
}

fun quantity(block: QuantityGenerator.() -> Unit): Quantity {
    val quantity = QuantityGenerator()
    quantity.apply(block)
    return quantity.generate()
}
