package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator

class PeriodGenerator : DataGenerator<Period>() {
    val start: NullDataGenerator<DateTime> = NullDataGenerator()
    val end: NullDataGenerator<DateTime> = NullDataGenerator()

    override fun generateInternal(): Period =
        Period(
            start = start.generate(),
            end = end.generate(),
        )
}

fun period(block: PeriodGenerator.() -> Unit): Period {
    val period = PeriodGenerator()
    period.apply(block)
    return period.generate()
}
