package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.test.data.generator.DataGenerator

@Deprecated("Use NullDataGenerator instead")
class PositiveIntGenerator : DataGenerator<PositiveInt?>() {
    override fun generateInternal(): PositiveInt? = null

    infix fun of(value: Int) {
        if (value <= 0) throw IllegalArgumentException("$value is not positive")

        of(PositiveInt(value))
    }
}

infix fun DataGenerator<PositiveInt?>.of(value: Int) {
    if (value <= 0) throw IllegalArgumentException("$value is not positive")

    of(PositiveInt(value))
}
