package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.test.data.generator.DataGenerator

infix fun DataGenerator<PositiveInt?>.of(value: Int) {
    if (value <= 0) throw IllegalArgumentException("$value is not positive")

    of(PositiveInt(value))
}
