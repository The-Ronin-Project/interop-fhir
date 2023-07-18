package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.faker.FakerDataGenerator

abstract class FHIRStringFakerDataGenerator : FakerDataGenerator<FHIRString>() {
    abstract fun generateString(): String

    override fun generateInternal() = FHIRString(generateString())

    infix fun of(value: String) {
        of(FHIRString(value))
    }
}

infix fun DataGenerator<FHIRString?>.of(value: String) = of(FHIRString(value))
