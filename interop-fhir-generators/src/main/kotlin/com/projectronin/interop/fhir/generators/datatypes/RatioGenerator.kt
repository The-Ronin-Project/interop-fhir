package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.Ratio
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

class RatioGenerator : DataGenerator<Ratio>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val numerator: DataGenerator<Quantity?> = NullDataGenerator()
    val denominator: DataGenerator<Quantity?> = NullDataGenerator()

    override fun generateInternal() = Ratio(
        numerator = numerator.generate(),
        denominator = denominator.generate()
    )
}

fun ratio(block: RatioGenerator.() -> Unit): Ratio {
    val ratio = RatioGenerator()
    ratio.apply(block)
    return ratio.generate()
}
