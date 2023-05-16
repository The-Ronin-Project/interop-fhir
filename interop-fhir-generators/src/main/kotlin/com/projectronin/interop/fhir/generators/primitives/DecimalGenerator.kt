package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.test.data.generator.DataGenerator

class DecimalGenerator : DataGenerator<Decimal?>() {
    override fun generateInternal(): Decimal? = null
}

infix fun DataGenerator<Decimal?>.of(value: Double?) {
    of(Decimal(value))
}
