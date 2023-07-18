package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.Date
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.temporal.DateGenerator as BaseDateGenerator

class DateGenerator : DataGenerator<Date>() {
    val year: DataGenerator<Int> = BaseDateGenerator().year
    val month: DataGenerator<Int> = BaseDateGenerator().month
    val day: DataGenerator<Int> = BaseDateGenerator().day

    override fun generateInternal(): Date =
        Date("%d-%02d-%02d".format(year.generate(), month.generate(), day.generate()))
}

fun date(block: DateGenerator.() -> Unit): Date {
    val date = DateGenerator()
    date.apply(block)
    return date.generate()
}
