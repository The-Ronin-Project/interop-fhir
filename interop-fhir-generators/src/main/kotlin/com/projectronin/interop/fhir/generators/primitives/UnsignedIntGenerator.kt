package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.test.data.generator.DataGenerator

class UnsignedIntGenerator : DataGenerator<UnsignedInt?>() {
    override fun generateInternal(): UnsignedInt? = null

    infix fun of(value: Int) {
        if (value < 0) throw IllegalArgumentException("value cannot be negative")

        of(UnsignedInt(value))
    }
}
