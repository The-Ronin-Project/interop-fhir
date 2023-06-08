package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.test.data.generator.DataGenerator
import java.math.BigDecimal

class DecimalGenerator : DataGenerator<Decimal?>() {
    override fun generateInternal(): Decimal? = null
}

infix fun DataGenerator<Decimal?>.of(value: BigDecimal?) {
    of(Decimal(value))
}

infix fun DataGenerator<Decimal?>.of(value: Double?) {
    of(Decimal(value))
}
