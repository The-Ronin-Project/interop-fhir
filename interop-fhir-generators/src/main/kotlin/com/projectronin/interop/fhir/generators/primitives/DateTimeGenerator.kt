package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.temporal.DateGenerator as BaseDateGenerator

class DateTimeGenerator : DataGenerator<DateTime>() {
    val year: DataGenerator<Int> = BaseDateGenerator().year
    val month: DataGenerator<Int> = BaseDateGenerator().month
    val day: DataGenerator<Int> = BaseDateGenerator().day

    override fun generateInternal(): DateTime =
        DateTime("%d-%02d-%02d".format(year.generate(), month.generate(), day.generate()))
}

fun dateTime(block: DateTimeGenerator.() -> Unit): DateTime {
    val date = DateTimeGenerator()
    date.apply(block)
    return date.generate()
}
